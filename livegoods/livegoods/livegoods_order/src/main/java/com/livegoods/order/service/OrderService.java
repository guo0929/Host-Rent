package com.livegoods.order.service;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Order;

import java.util.List;

public interface OrderService {
    Result<Order> addOrder(Order order);
    List<Order> getOrdersByUser(String user);

    Order getOrderById(String id);

    Result<Object> modifyOrderCommentState(String orderId);
}
