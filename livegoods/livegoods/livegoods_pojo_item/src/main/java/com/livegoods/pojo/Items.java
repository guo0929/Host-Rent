package com.livegoods.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Items implements Serializable {
    private String id;
    private String title;
    private String img;
    private Long sales; // 销售数量
    private Boolean recommendation; // 是否推荐。
    private int recommendationSort; // 推荐排序
    private String city; // 城市
    // link 连接地址。 link是推导属性，不需要数据库记录，可以通过主键推导得出。
    public String getLink(){
        return "/items/"+id;
    }
    // 空处理，占位使用，避免Jackson做反向序列化抛出异常。
    public void setLink(String link){}
}
