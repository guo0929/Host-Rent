package com.livegoods.buytime.controller;

import com.livegoods.buytime.service.BuyTimeService;
import com.livegoods.commons.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BuyTimeController {
    @Autowired
    private BuyTimeService buyTimeService;

    /**
     * 根据房屋主键，查询访问可订购时间。
     * @param id
     * @return
     *  { "time":时间戳 }
     */
    @GetMapping("/buytime")
    public Result<Object> getHouseBuyTime(String id){
        return buyTimeService.getHouseBuyTime(id);
    }
}
