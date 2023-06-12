package com.livegoods.search.controller;

import com.livegoods.commons.pojo.Result;
import com.livegoods.search.pojo.House4ES;
import com.livegoods.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 搜索房屋。每页固定查询5条数据
     * @param city 所在城市
     * @param content 所属关键字
     * @param page 页码，从0开始
     * @return
     */
    @GetMapping("/search")
    public Result<House4ES> search(String city, String content, int page){
        return searchService.search(city, content, page);
    }

    /**
     * 初始化ES数据。从MongoDB中查询所有的房屋数据，并初始化到ES中。
     * 不是任何人都可以访问的。必须经过登录认证，通过后，才能访问。
     * @return
     */
    @GetMapping("/init")
    @CrossOrigin
    public String init(){
        searchService.init();
        return "初始化ES完毕";
    }
}
