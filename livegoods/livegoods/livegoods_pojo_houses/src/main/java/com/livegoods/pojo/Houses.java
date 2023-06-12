package com.livegoods.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Houses implements Serializable {
    private String id;
    private String title;
    private String houseType;
    private String rentType;
    private String price;
    private String city;
    private String[] imgs;
    /**
     * years: "建造年份 2002年",
     * type: "庭室  4室1厅",
     * level: "楼层  17/19层",
     * style: "装修  精装性",
     * orientation: "朝向  朝南"
     */
    private Map<String, String> info;
    private Date buytime;
    private int nums;

}
