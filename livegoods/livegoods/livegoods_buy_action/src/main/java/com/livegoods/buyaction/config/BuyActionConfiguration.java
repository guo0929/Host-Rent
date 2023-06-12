package com.livegoods.buyaction.config;

import com.livegoods.MessagePublisher;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuyActionConfiguration {
    @Bean
    public MessagePublisher messagePublisher(AmqpTemplate template){
        MessagePublisher messagePublisher = new MessagePublisher();
        messagePublisher.setAmqpTemplate(template);
        return messagePublisher;
    }
}
