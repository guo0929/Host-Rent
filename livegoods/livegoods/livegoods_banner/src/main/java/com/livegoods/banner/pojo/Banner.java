package com.livegoods.banner.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

// 实体类型。轮播图
@Data
public class Banner implements Serializable {
    private String id;
    private String path; // 图片路径地址。
    // 只有查询的系统当前时刻，在开始和结束之间的数据为有效数据。
    private Date beginTime; // 有效开始时间
    private Date endTime; // 有效结束时间
}
