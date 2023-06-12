package com.livegoods.feedback.service;

import com.livegoods.commons.pojo.Result;

public interface FeedbackService {
    /**
     * 评论
     * @param orderId
     * @param feelback
     * @param rate
     * @return
     */
    Result<Object> feedback(String orderId, String feelback, int rate);
}
