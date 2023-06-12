package com.livegoods.search.service;

import com.livegoods.commons.pojo.Result;
import com.livegoods.search.pojo.House4ES;

public interface SearchService {
    // 初始化数据到ES。 返回结果代表初始化是否成功。
    boolean init();

    // 搜索房屋。每页搜索5条数据
    Result<House4ES> search(String city, String content, int page);
}
