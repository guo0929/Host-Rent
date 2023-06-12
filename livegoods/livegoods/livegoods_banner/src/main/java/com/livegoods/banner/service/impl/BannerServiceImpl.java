package com.livegoods.banner.service.impl;

import com.livegoods.banner.dao.BannerDao;
import com.livegoods.banner.pojo.Banner;
import com.livegoods.banner.service.BannerService;
import com.livegoods.commons.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    @Value("${livegoods.banner.nginx.server}")
    private String bannerNginxServer;
    @Autowired
    private BannerDao bannerDao;

    /**
     * 调用DAO(Data Access Object)，远程访问MongoDB数据库，查询数据。
     * 返回结果，做随机排序。抽取其中5条数据。不足5条的，返回全部结果。
     * @return
     */
    @Override
    public Result<String> getBanners() {
        // 查询Banner
        List<Banner> banners = bannerDao.getBanners();
        // 随机排序
        Collections.shuffle(banners);
        // 定义返回结果集合
        List<String> results = null;
        // 判断查询的Banner集合长度。根据具体返回结果数据长度，初始化集合。
        if(banners.size() > 5){
            results = new ArrayList<>(5);
        }else{
            results = new ArrayList<>(banners.size());
        }
        int end = banners.size() > 5 ? 5 : banners.size();
        // 循环处理结果。把Banner中的图片地址获取，并保存到结果集合中。
        for(int i = 0; i < end; i++){
            Banner banner = banners.get(i);
            results.add(bannerNginxServer + banner.getPath());
        }

        // 返回结果
        return new Result<>(200, results);
    }
}
