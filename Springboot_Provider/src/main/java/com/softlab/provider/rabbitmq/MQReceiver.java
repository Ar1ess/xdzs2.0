package com.softlab.provider.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlab.common.exception.AppException;
import com.softlab.common.model.User;
import com.softlab.common.model.vo.DecryptVo;
import com.softlab.common.service.UserService;
import com.softlab.common.util.JsonUtil;
import com.softlab.provider.mapper.RedisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LiXiwen
 * @date 2019/11/8 19:03
 */
@Service
public class MQReceiver implements ChannelAwareMessageListener {
    private final Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Autowired
    public MQReceiver(ObjectMapper objectMapper, UserService userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            byte[] body = message.getBody();
            DecryptVo decryptVo = objectMapper.readValue(body, DecryptVo.class);
            logger.info("tag : " + tag);
            logger.info("异步接收decryptVo ： ");
            User user = new User();
            BeanUtils.copyProperties(decryptVo, user);

            userService.addUser(user);
        } catch (Exception e) {
            logger.error("异步接收decrytpVo信息发生异常 : " + e.getMessage());
            channel.basicReject(tag, false);
        } finally {
            channel.basicAck(tag, false);
        }
    }




}
