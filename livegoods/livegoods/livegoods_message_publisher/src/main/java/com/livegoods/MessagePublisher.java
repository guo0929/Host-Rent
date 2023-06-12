package com.livegoods;

import org.springframework.amqp.core.AmqpTemplate;

/**
 * 发送消息的工具类型。
 */
public class MessagePublisher {
    private AmqpTemplate amqpTemplate;

    /**
     * 发送同步消息
     * @return
     */
    public Object sendMessageSync(String exchange, String routingKey, Object messagePayload){
        return amqpTemplate.convertSendAndReceive(exchange, routingKey, messagePayload);
    }

    /**
     * 发送异步消息
     */
    public void sendMessageAsync(String exchange, String routingKey, Object messagePayload){
        amqpTemplate.convertAndSend(exchange, routingKey, messagePayload);
    }

    public AmqpTemplate getAmqpTemplate() {
        return amqpTemplate;
    }

    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }
}
