package com.livegoods.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LivegoodsSearchApp {
    public static void main(String[] args) {
        SpringApplication.run(LivegoodsSearchApp.class, args);
    }
}
