package com.livegoods.feedback.service.impl;

import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.livegoods.commons.pojo.Result;
import com.livegoods.feedback.dao.FeedbackDao;
import com.livegoods.feedback.openfeign.OrderServiceInterface;
import com.livegoods.feedback.service.FeedbackService;
import com.livegoods.pojo.Comments;
import com.livegoods.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;
    @Autowired
    private OrderServiceInterface orderServiceInterface;

    /**
     * 评论， 新增评论信息
     *  1、 远程调用订单服务，主键查询订单
     *  2、 维护评论对象
     *  3、 保存数据并返回结果
     * @param orderId
     * @param feelback
     * @param rate
     * @return
     */
    @Override
    @TccTransaction
    public Result<Object> feedback(String orderId, String feelback, int rate) {
        Result<Object> result = new Result<>();
        // 远程调用服务，查询订单
        Order order = orderServiceInterface.getOrderById(orderId);
        if(order == null){
            result.setStatus(500);
            result.setMsg("评论失败");
            return result;
        }
        // 维护评论对象
        Comments comments = new Comments();
        comments.setUsername(order.getUser());
        comments.setComment(feelback);
        comments.setHouseId(order.getHouseId());
        comments.setStar(rate);
        // 保存评论
        feedbackDao.saveFeedback(comments);
        result.setStatus(200);
        result.setMsg("评论成功");

        // 更新订单状态
        orderServiceInterface.modifyOrderCommentState(order.getId());

        return result;
    }
    // 取消
    public Result<Object> cancelFeedback(String orderId, String feelback, int rate){
        // 查询订单。
        Order order = orderServiceInterface.getOrderById(orderId);
        // 确认评论对象，房屋、评论内容、评论星级分数、评论用户一致，是要删除的评论对象
        Comments comments = new Comments();
        comments.setStar(rate);
        comments.setHouseId(order.getHouseId());
        comments.setComment(feelback);
        comments.setUsername(order.getUser());

        // 删除评论数据
        feedbackDao.removeByComments(comments);

        Result<Object> result = new Result<>();
        result.setStatus(500);
        result.setMsg("评论失败");
        return result;
    }
    // 确认
    public Result<Object> confirmFeedback(String orderId, String feelback, int rate){
        Result<Object> result = new Result<>();
        result.setStatus(200);
        result.setMsg("评论成功");

        return result;
    }
}
