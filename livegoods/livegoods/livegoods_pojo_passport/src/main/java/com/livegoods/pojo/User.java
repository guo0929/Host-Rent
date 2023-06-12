package com.livegoods.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

// 用户
@Data
public class User implements Serializable {
    private String id;
    private String phone; // 手机号
    private Date registerTime; // 注册时间
    private Date lastLoginTime; // 最近登录时间
}
