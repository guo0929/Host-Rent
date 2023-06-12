package com.livegoods.lcn.tm;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTransactionManagerServer
public class LivegoodsTransactionManagerApp {
    public static void main(String[] args) {
        SpringApplication.run(LivegoodsTransactionManagerApp.class, args);
    }
}
