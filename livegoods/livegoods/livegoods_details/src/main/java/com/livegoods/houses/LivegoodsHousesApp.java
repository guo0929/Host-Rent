package com.livegoods.houses;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableDistributedTransaction
public class LivegoodsHousesApp {
    public static void main(String[] args) {
        SpringApplication.run(LivegoodsHousesApp.class, args);
    }
}
