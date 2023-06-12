package com.livegoods.comments.dao;

import com.livegoods.pojo.Comments;

import java.util.List;

public interface CommentsDao {
    // 分页查询房屋评价集合
    List<Comments> findCommentsByHouseId(String id, int page, int rows);
    // 根据房屋主键，查询评论总数
    long findCommentsRowsByHouseId(String id);
}
