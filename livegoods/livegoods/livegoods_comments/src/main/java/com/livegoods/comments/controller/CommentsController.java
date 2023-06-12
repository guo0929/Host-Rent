package com.livegoods.comments.controller;

import com.livegoods.comments.service.CommentsService;
import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    /**
     * 根据房屋主键，查询评价数据。
     * @param id 房屋主键
     * @param page 页码
     * @return
     */
    @GetMapping("/comment")
    public Result<Comments> getCommentsByHouseId(String id, int page){
        return commentsService.getCommentsByHouseId(id, page);
    }
}
