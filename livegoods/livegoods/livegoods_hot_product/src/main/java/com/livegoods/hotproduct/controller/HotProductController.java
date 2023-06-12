package com.livegoods.hotproduct.controller;

import com.livegoods.commons.pojo.Result;
import com.livegoods.hotproduct.service.HotProductService;
import com.livegoods.pojo.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HotProductController {
    @Autowired
    private HotProductService hotProductService;

    /**
     * 查询热销商品。
     * 数据规则：
     *  1、 条件所在城市，商品数量>=4，返回查询结果
     *  2、 条件所在城市，商品数量<4，查询其他城市商品，补足4条并返回。
     *  3、 所有城市商品数量不足4条，返回所有结果。
     * @param city 查询条件，城市
     * @return
     */
    @GetMapping("/hotProduct")
    public Result<Items> getHotProducts(String city){
        return hotProductService.getHotProducts(city);
    }
}
