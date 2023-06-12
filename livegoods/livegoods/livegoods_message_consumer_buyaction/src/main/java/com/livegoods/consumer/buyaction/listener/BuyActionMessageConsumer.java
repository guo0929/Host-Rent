package com.livegoods.consumer.buyaction.listener;

import com.livegoods.commons.message.BuyActionMessage;
import com.livegoods.pojo.Houses;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 处理秒杀消息的消费者
 */
@Component
public class BuyActionMessageConsumer {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private final String lockValue = "1";
    @Value("${livegoods.lock.buyAction}")
    private String lockKey;
    // 自旋超时阈值
    private final long times = 1000;
    @Value("${livegoods.house.redisPrefix}")
    private String housePrefix;
    /**
     * 处理秒杀消息
     *  1、 访问redis，获取分布式锁。锁设定一个有效期。
     *  2、 判断锁是否获取成功。
     *  3、 获取失败，自旋等待，获取锁后，进入后续流程。
     *  4、 获取成功，访问redis，查询房屋数据
     *  5、 修改房屋数据库存
     *  6、 更新redis中的房屋缓存
     *  7、 释放分布式锁标记
     *  8、 返回秒杀结果
     * @param message
     * @return
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "${livegoods.amqp.buyaction.queueName}", autoDelete = "false"),
                    exchange = @Exchange(name = "${livegoods.amqp.buyaction.exchange}", type = "${livegoods.amqp.buyaction.exchangeType}"),
                    key = "${livegoods.amqp.buyaction.routingKey}"
            )
    })
    public boolean onMessage(BuyActionMessage message){
        long localTimes = 0;
        while(localTimes < times) {
            // 分布式锁
            if (redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, 2, TimeUnit.SECONDS)) {
                try {
                    // 获取到锁标记
                    // 查询redis中的商品数据
                    String houseKey = housePrefix + "(" + message.getHouseId() + ")";
                    Houses houses = (Houses) redisTemplate.opsForValue().get(houseKey);

                    // 判断库存是否充足
                    if (houses.getNums() > 0) {
                        // 商品库存 - 1
                        houses.setNums(houses.getNums() - 1);
                        // 更新商品数据到redis，保证后续其他逻辑可以得到秒杀后的结果
                        redisTemplate.opsForValue().set(houseKey, houses);
                        // 秒杀成功
                        return true;
                    } else {
                        // 库存不足，秒杀失败
                        return false;
                    }
                }finally { // 一定删除锁标记
                    redisTemplate.delete(lockKey);
                }
            }
            try {
                localTimes += 100L;
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 自旋超时，秒杀失败。
        return false;
    }
}
