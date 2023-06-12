package com.livegoods.order.dao;

import com.livegoods.pojo.Order;

import java.util.List;

public interface OrderDao {
    void saveOrder(Order order);
    void deleteOrder(Order order);
    List<Order> findOrdersByUser(String user);

    Order findById(String id);

    void modifyOrderCommentState(String orderId, int i);
}
