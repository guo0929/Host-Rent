package com.livegoods.order.dao.impl;

import com.livegoods.order.dao.OrderDao;
import com.livegoods.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 修改订单的评论状态
     * @param orderId 订单主键
     * @param i 状态
     */
    @Override
    public void modifyOrderCommentState(String orderId, int i) {
        Query query = Query.query(
                Criteria.where("_id").is(orderId)
        );
        Update update = Update.update("commentState", i);
        mongoTemplate.updateFirst(query, update, Order.class);
    }

    @Override
    public Order findById(String id) {
        return mongoTemplate.findById(id, Order.class);
    }

    @Override
    public List<Order> findOrdersByUser(String user) {
        Query query = Query.query(
                Criteria.where("user").is(user)
        );
        return mongoTemplate.find(query, Order.class);
    }

    @Override
    public void deleteOrder(Order order) {
        mongoTemplate.remove(order);
    }

    @Override
    public void saveOrder(Order order) {
        mongoTemplate.save(order);
    }
}
