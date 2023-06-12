package com.livegoods.commons.message;

import lombok.Data;

import java.io.Serializable;

/**
 * 秒杀流程中的消息类型
 */
@Data
public class BuyActionMessage implements Serializable {
    // 秒杀房屋主键
    private String houseId;
}
