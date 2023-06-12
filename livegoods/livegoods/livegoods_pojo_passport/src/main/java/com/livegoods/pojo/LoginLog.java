package com.livegoods.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

// 登录日志
@Data
public class LoginLog implements Serializable {
    private String id;
    private String username; // 手机号|用户名
    private Date currentTime; // 当前时间
    private String type; // 登录方式  ：  验证码|密码
    private String message; // 消息
    private String status; // 登录状态 ： 成功|失败

    public static final String VALIDATE_CODE="验证码登录";
    public static final String PASSWORD="密码登录";
    public static final String SUCCESS="成功";
    public static final String ERROR="失败";
}
