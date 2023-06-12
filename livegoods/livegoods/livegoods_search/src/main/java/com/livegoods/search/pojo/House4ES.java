package com.livegoods.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * 映射ES中所有的实体类型
 */
@Data
@Document(indexName = "livegoods_houses", type = "houses")
public class House4ES implements Serializable {
    @Id
    private String id;
    private String title;
    private String houseType;
    private String rentType;
    private String img;
    private String city;
    private String price;
}
