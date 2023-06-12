package com.livegoods.buyaction.controller;

import com.livegoods.buyaction.service.BuyActionService;
import com.livegoods.commons.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BuyActionController {
    @Autowired
    private BuyActionService buyActionService;

    /**
     * 秒杀
     * @param id 订购商品的主键
     * @param user 用户名， 用户登录手机号
     * @return
     */
    @GetMapping("/buyaction")
    public Result<Object> buyAction(String id, String user){
        return buyActionService.buyAction(id, user);
    }
}
