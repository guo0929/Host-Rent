package com.livegoods.banner.service;

import com.livegoods.commons.pojo.Result;

// Banner微服务中的服务接口
public interface BannerService {
    /**
     * 查询Banner轮播图数据。
     * 查询条件是，beginTime < 系统当前时间 < endTime
     * @return
     */
    Result<String> getBanners();
}
