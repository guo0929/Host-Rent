package com.livegoods.buytime.service.impl;

import com.livegoods.buytime.dao.BuyTimeDao;
import com.livegoods.buytime.service.BuyTimeService;
import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Houses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyTimeServiceImpl implements BuyTimeService {
    @Autowired
    private BuyTimeDao buyTimeDao;

    @Override
    public Result<Object> getHouseBuyTime(String id) {
        Houses houses = buyTimeDao.findHouseById(id);
        if(houses == null){
            return new Result<>();
        }
        System.out.println(houses.getBuytime());
        long buytime = houses.getBuytime().getTime();
        Result<Object> result = new Result<>();
        result.setStatus(200);
        result.setTime(buytime);

        return result;
    }
}
