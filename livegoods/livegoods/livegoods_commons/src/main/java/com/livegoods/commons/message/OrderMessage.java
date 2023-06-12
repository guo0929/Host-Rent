package com.livegoods.commons.message;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建订单的消息类型
 */
@Data
public class OrderMessage implements Serializable {
    // 房屋主键
    private String houseId;
    // 购买人手机号
    private String user;
}
