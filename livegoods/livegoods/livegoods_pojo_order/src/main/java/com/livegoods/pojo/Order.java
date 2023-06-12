package com.livegoods.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable {
    private String id;
    private String title; // 房屋标题
    private String houseType; // 房屋类型， 厅室 - 面积
    private String price; // 价格
    private String img; // 图片
    private String rentType; // 租赁方式
    private int commentState; // 评论标记。 0 - 未评论。 2 - 已评论
    private String houseId; // 房屋主键
    private String user; // 用户手机号
}
