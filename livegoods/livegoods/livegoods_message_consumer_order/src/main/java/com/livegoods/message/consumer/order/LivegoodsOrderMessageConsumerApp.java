package com.livegoods.message.consumer.order;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDistributedTransaction
@EnableFeignClients
public class LivegoodsOrderMessageConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(LivegoodsOrderMessageConsumerApp.class, args);
    }
}
