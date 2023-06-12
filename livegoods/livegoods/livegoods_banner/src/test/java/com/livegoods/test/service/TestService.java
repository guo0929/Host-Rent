package com.livegoods.test.service;

import com.livegoods.banner.LivegoodsBannerApp;
import com.livegoods.banner.service.BannerService;
import com.livegoods.commons.pojo.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {LivegoodsBannerApp.class})
@RunWith(SpringRunner.class)
public class TestService {
    @Autowired
    private BannerService bannerService;

    /**
     * 测试BannerService中的查询逻辑。
     */
    @Test
    public void testGetBanners(){
        Result<String> result = bannerService.getBanners();
        System.out.println(result.getStatus());

        for(String path : result.getResults()){
            System.out.println(path);
        }
    }
}
