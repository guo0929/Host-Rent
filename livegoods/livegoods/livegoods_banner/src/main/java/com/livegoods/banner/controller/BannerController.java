package com.livegoods.banner.controller;

import com.livegoods.banner.service.BannerService;
import com.livegoods.commons.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BannerController {
    @Autowired
    private BannerService bannerService;

    /**
     * 前后端分离项目，所有的请求都由前端系统发起，都是跨域请求。
     * 控制器必须可以处理跨域请求。
     * 在商业环境中，轮播有有效时间。或数据量级固定。不会无限膨胀。
     * 处理轮播图查询逻辑
     * 每次访问返回5个轮播图。不足返回查询的所有结果
     * 顺序随机
     * @return 可以使用Map作为返回结果类型。或自定义类型处理返回结果。
     *  {
     *      "status":200
     *      "results":["","",""]
     *  }
     */
    @GetMapping("/banner")
    public Result<String> getBanners(){
        return bannerService.getBanners();
    }
}
