package com.livegoods.message.consumer.order.openfeign;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("livegoods-order")
public interface OrderServiceInterface {
    @PostMapping("/addOrder")
    Result<Order> addOrder(@RequestBody Order order);
}
