package com.softlab.provider.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.vo.DecryptVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


/**
 * @author LiXiwen
 * @date 2019/11/8 19:03
 */
@Service
public class MQSender {
    private final Logger logger = LoggerFactory.getLogger(MQSender.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public MQSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(DecryptVo decryptVo) {
        try {
            rabbitTemplate.setExchange("192.168.1.100.decrypt.exchange");
            rabbitTemplate.setRoutingKey("192.168.1.100.decrypt.routing.key");
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(decryptVo))
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();

            rabbitTemplate.convertAndSend(
                    message,
                    message1 -> {
                        MessageProperties properties=message1.getMessageProperties();
                        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,DecryptVo.class);
                        return message1;
                    }
            );
        } catch (Exception e) {
            logger.error("发送消息错误!" + e.getMessage());
            throw new AppException(1, "发送消息错误!" + e.getMessage());
        }

    }

}
