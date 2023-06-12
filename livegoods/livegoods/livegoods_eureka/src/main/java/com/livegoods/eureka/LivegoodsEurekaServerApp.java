package com.livegoods.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LivegoodsEurekaServerApp {
    public static void main(String[] args) {
        SpringApplication.run(LivegoodsEurekaServerApp.class, args);
    }
}
