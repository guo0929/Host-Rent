package com.livegoods.feedback.openfeign;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("livegoods-order")
public interface OrderServiceInterface {
    @GetMapping("/getOrderById")
    Order getOrderById(@RequestParam("id") String id);
    @PostMapping("/modifyOrderCommentState")
    Result<Object> modifyOrderCommentState(@RequestParam("orderId") String orderId);
}
