package com.livegoods.commons.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Livegoods项目中的返回结果类型。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private Integer status;
    private List<T> results;
    private boolean hasMore;
    private long time;
    private String msg;
    public Result(Integer status, List<T> results){
        this.status = status;
        this.results = results;
    }
    public List<T> getData(){
        return results;
    }
    public void setData(List<T> data){
        this.results = data;
    }
}
