package com.livegoods.feedback.controller;

import com.livegoods.commons.pojo.Result;
import com.livegoods.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    /**
     * 评论功能
     * @param orderId 订单主键
     * @param feelback 评论内容
     * @param rate 评论星级分数
     * @return
     */
    @PostMapping("/feelback")
    public Result<Object> feedback(String orderId, String feelback, int rate){
        return feedbackService.feedback(orderId, feelback, rate);
    }
}
