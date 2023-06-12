package com.livegoods.feedback;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDistributedTransaction
public class LivegoodsFeedbackApp {
    public static void main(String[] args) {
        SpringApplication.run(LivegoodsFeedbackApp.class, args);
    }
}
