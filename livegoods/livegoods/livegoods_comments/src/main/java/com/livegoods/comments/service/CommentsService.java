package com.livegoods.comments.service;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Comments;

public interface CommentsService {
    // 根据房屋主键，查询评价集合。
    Result<Comments> getCommentsByHouseId(String id, int page);
}
