package com.livegoods.buyaction.service;

import com.livegoods.commons.pojo.Result;

public interface BuyActionService {
    /**
     * 秒杀
     *  1、 发送同步消息，等待处理结果
     *  2、 秒杀成功，发送异步消息，创建订单，修改库存。返回成功结果
     *  3、 秒杀失败，返回失败结果。
     *
     * 设计消息内容：
     *  秒杀消息：
     *   传递商品数据即可。
     *  订单消息：
     *   传递商品数据和用户数据。
     * @param id 商品主键
     * @param user 登录用户手机号
     * @return
     */
    Result<Object> buyAction(String id, String user);
}
