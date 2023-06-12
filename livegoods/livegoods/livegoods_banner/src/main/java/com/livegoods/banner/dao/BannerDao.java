package com.livegoods.banner.dao;

import com.livegoods.banner.pojo.Banner;

import java.util.List;

// banner系统中的数据访问接口
public interface BannerDao {
    List<Banner> getBanners();
}
