package com.livegoods.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments implements Serializable {
    private String id; // 主键
    private String username; // 用户名，即用户手机号
    private String comment; // 评价内容
    private int star; // 评价星级
    private String houseId; // 房屋主键
}
