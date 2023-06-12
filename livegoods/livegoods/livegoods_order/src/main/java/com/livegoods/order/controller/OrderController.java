package com.livegoods.order.controller;

import com.livegoods.commons.pojo.Result;
import com.livegoods.order.service.OrderService;
import com.livegoods.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 评论成功后，修改订单状态的方法。
     * 把订单的 commentState 0 -> 2
     * @param orderId
     * @return
     */
    @PostMapping("/modifyOrderCommentState")
    public Result<Object> modifyOrderCommentState(String orderId){
        return orderService.modifyOrderCommentState(orderId);
    }

    @GetMapping("/getOrderById")
    public Order getOrderById(String id){
        return orderService.getOrderById(id);
    }

    /**
     * 根据登录用户手机号，查询订单集合
     * @param user 登录用户手机号
     * @return
     */
    @GetMapping("/order")
    public List<Order> getOrdersByUser(String user){
        return orderService.getOrdersByUser(user);
    }

    /**
     * 新增订单
     * @param order
     * @return
     */
    @PostMapping("/addOrder")
    public Result<Order> addOrder(@RequestBody Order order){
        return orderService.addOrder(order);
    }
}
