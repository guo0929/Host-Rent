package com.livegoods.message.consumer.order.consumer;

import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.livegoods.commons.message.OrderMessage;
import com.livegoods.commons.pojo.Result;
import com.livegoods.message.consumer.order.openfeign.DetailsServiceInterface;
import com.livegoods.message.consumer.order.openfeign.OrderServiceInterface;
import com.livegoods.pojo.Houses;
import com.livegoods.pojo.Order;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageConsumer {
    @Autowired
    private DetailsServiceInterface detailsServiceInterface;
    @Autowired
    private OrderServiceInterface orderServiceInterface;

    /**
     * 缺少订单实体类型。确定订单内容，决定后续逻辑中，如何维护数据
     * 订单消息消费者方法。
     * 1、 新增订单，订单数据需要维护。
     * 2、 更新商品库存，房屋数据需要维护。
     * 3、 分布式事务管理
     * @param message
     */
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(name = "${livegoods.amqp.order.queueName}", autoDelete = "false"),
            exchange = @Exchange(name = "${livegoods.amqp.order.exchange}", type = "${livegoods.amqp.order.exchangeType}"),
            key = "${livegoods.amqp.order.routingKey}"
    )})
    @TccTransaction
    public void onMessage(OrderMessage message){
        // 调用details服务，根据房屋主键，查询房屋对象。
        Houses houses = detailsServiceInterface.showHouseDetailsById(message.getHouseId());

        // 维护订单数据
        Order order = new Order();
        order.setUser(message.getUser());
        order.setTitle(houses.getTitle());
        order.setRentType(houses.getRentType());
        order.setPrice(houses.getPrice());
        order.setImg((houses.getImgs() != null && houses.getHouseType().length() > 0) ? houses.getImgs()[0] : "");
        order.setCommentState(0);
        order.setHouseId(message.getHouseId());
        order.setHouseType(houses.getInfo().get("type") + " - " + houses.getHouseType());

        // 新增订单， 调用订单服务实现
        Result<Order> addOrderResult = orderServiceInterface.addOrder(order);
        if(addOrderResult.getStatus() != 200){
            // 新增订单错误。通过异常，实现事务回滚
            throw new RuntimeException("新增订单异常");
        }

        // 更新房屋库存， 调用Details房屋服务实现
        // 注意：应该校验库存是否正确，考虑高并发极限情况。
        // 注意：可以考虑加分布式锁，来更新库存。
        Result<Houses> modifyResult = detailsServiceInterface.modifyHousesNum(houses);
        if(modifyResult.getStatus() != 200){
            // 新增订单错误。通过异常，实现事务回滚
            throw new RuntimeException("更新房屋库存异常");
        }

        // 事务管理
    }
}
