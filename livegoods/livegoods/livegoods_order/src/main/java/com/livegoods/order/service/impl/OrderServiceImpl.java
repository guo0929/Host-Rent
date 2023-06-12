package com.livegoods.order.service.impl;

import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.livegoods.commons.pojo.Result;
import com.livegoods.order.dao.OrderDao;
import com.livegoods.order.service.OrderService;
import com.livegoods.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    /**
     * 修改订单状态， commentState 0 -> 2
     * @param orderId
     * @return
     */
    @Override
    @TccTransaction
    public Result<Object> modifyOrderCommentState(String orderId) {
        // 修改订单的评论状态。 已评论
        orderDao.modifyOrderCommentState(orderId, 2);
        Result<Object> result = new Result<>();
        result.setStatus(200);
        return result;
    }

    // 取消
    public Result<Object> cancelModifyOrderCommentState(String orderId) {
        // 恢复订单的评论状态。 未评论
        orderDao.modifyOrderCommentState(orderId, 0);
        Result<Object> result = new Result<>();
        result.setStatus(500);
        return result;
    }

    // 确认
    public Result<Object> confirmModifyOrderCommentState(String orderId) {
        Result<Object> result = new Result<>();
        result.setStatus(200);
        return result;
    }

    @Override
    public Order getOrderById(String id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> getOrdersByUser(String user) {

        return orderDao.findOrdersByUser(user);
    }

    /**
     * 新增订单。需要做分布式事务管理
     * @param order
     * @return
     */
    @Override
    @TccTransaction
    public Result<Order> addOrder(Order order) {
        orderDao.saveOrder(order);
        Result<Order> result = new Result<>();
        result.setStatus(200);
        result.setResults(Arrays.asList(order));
        return result;
    }
    // 确认，不需要特殊操作
    public Result<Order> confirmAddOrder(Order order){
        Result<Order> result = new Result<>();
        result.setStatus(200);
        result.setResults(Arrays.asList(order));
        return result;
    }
    // 取消，需要回滚事务，删除刚新增的数据
    public Result<Order> cancelAddOrder(Order order){
        Result<Order> result = new Result<>();
        result.setStatus(500);
        orderDao.deleteOrder(order);
        return result;
    }
}
