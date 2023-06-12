package com.livegoods.buytime.service;


import com.livegoods.commons.pojo.Result;

public interface BuyTimeService {
    /**
     * 根据房屋主键，查询可订购时间。
     * @param id
     * @return
     */
    Result<Object> getHouseBuyTime(String id);
}
