package com.softlab.provider.config;

import com.softlab.provider.rabbitmq.MQReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.amqp.core.*;

/**
 * @author LiXiwen
 * @date 2019/11/8 17:08
 */

@Configuration
public class RabbitmqConfig {
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqConfig.class);

    @Value("${queue.user.exchange.name}")
    private String exchange;
    @Value("${queue.user.queue.name}")
    private String queue;
    @Value("${queue.user.routing.key.name}")
    private String routingKey;

    private final CachingConnectionFactory connectionFactory;
    private final MQReceiver mqReceiver;

    @Autowired
    public RabbitmqConfig(CachingConnectionFactory connectionFactory, MQReceiver mqReceiver) {
        this.connectionFactory = connectionFactory;
        this.mqReceiver = mqReceiver;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /**
     * exchange -（direct） 队列 一个队列一个消费者
     */

    @Bean
    public DirectExchange decryptExchange() {
        return new DirectExchange(exchange, true, false);
    }

    @Bean
    public Queue decryptQueue() {
        return new Queue(queue, true);
    }

    @Bean
    public Binding decryptBinding() {
        return BindingBuilder.bind(decryptQueue()).to(decryptExchange()).with(routingKey);
    }



    @Bean
    public RabbitTemplate rabbitTemplate(){
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        /*
        若使用confirm-callback必须要配置publisherConfirms为true
        若使用return-callback必须要配置publisherReturns为true
        每个rabbitTemplate只能有一个confirm-callback和return-callback，如果这里配置了，
        那么写生产者的时候不能再写confirm-callback和return-callback
        使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause)
                -> logger.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause));

        /*
         * todo : setConfirmCallback
         * 如果消息没有到exchange,则confirm回调,ack=false
         * 如果消息到达exchange,则confirm回调,ack=true
         * todo : setConfirmCallback
         * exchange到queue成功,则不回调return
         * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
         */
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey)
                -> logger.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message));

        return rabbitTemplate;
    }


    @Bean
    public SimpleMessageListenerContainer listenerContainerUserOrder() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();

        container.setConnectionFactory(connectionFactory);
        // TODO : 并发配置
        container.setConcurrentConsumers(5);
        container.setMaxConcurrentConsumers(20);
        container.setPrefetchCount(1);
        // TODO : 消息确认机制
        container.setQueues(decryptQueue());
        container.setMessageListener(mqReceiver);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }



    /**
     * 单一消费者
     * @return
     */
/*
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        // TODO : 并发配置
        factory.setConcurrentConsumers(5);
        factory.setMaxConcurrentConsumers(20);
        factory.setPrefetchCount(1);
        // TODO : 消息确认机制
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        return factory;
    }
*/

}

