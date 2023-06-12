package com.livegoods.feedback.dao;

import com.livegoods.pojo.Comments;

public interface FeedbackDao {
    /**
     * 保存评论
     * @param comments
     */
    void saveFeedback(Comments comments);

    void removeByComments(Comments comments);
}
