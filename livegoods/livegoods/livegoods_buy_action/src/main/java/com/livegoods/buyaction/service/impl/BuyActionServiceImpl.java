package com.livegoods.buyaction.service.impl;

import com.livegoods.MessagePublisher;
import com.livegoods.buyaction.service.BuyActionService;
import com.livegoods.commons.message.BuyActionMessage;
import com.livegoods.commons.message.OrderMessage;
import com.livegoods.commons.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BuyActionServiceImpl implements BuyActionService {
    @Autowired
    private MessagePublisher messagePublisher;
    @Value("${livegoods.amqp.buyaction.exchange}")
    private String exchange;
    @Value("${livegoods.amqp.buyaction.routingKey}")
    private String routingKey;
    @Value("${livegoods.amqp.order.exchange}")
    private String orderExchange;
    @Value("${livegoods.amqp.order.routingKey}")
    private String orderRoutingKey;

    /**
     * 秒杀
     *  1、 发同步消息
     *  2、 判断处理结果
     *  3、 发送异步消息
     *  4、 返回
     * @param id 商品主键
     * @param user 登录用户手机号
     * @return
     */
    @Override
    public Result<Object> buyAction(String id, String user) {
        BuyActionMessage message = new BuyActionMessage();
        message.setHouseId(id);
        boolean buyResult =
                (boolean) messagePublisher.sendMessageSync(exchange, routingKey, message);
        Result<Object> result = new Result<>();
        if(buyResult){
            // 秒杀成功，发送异步消息，创建订单，更新库存。
            OrderMessage orderMessage = new OrderMessage();
            orderMessage.setHouseId(id);
            orderMessage.setUser(user);
            messagePublisher.sendMessageAsync(orderExchange, orderRoutingKey, orderMessage);

            result.setStatus(200);
            result.setMsg("预定成功，请等待客服联系，请务必保持手机畅通！");
        }else{
            // 秒杀失败，直接返回处理结果
            result.setStatus(500);
            result.setMsg("手慢了，房屋已订出，请下次努力！");
        }
        return result;
    }
}
