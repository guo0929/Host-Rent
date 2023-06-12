package com.livegoods.recommendation.controller;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Items;
import com.livegoods.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class RecommendationController {
    @Autowired
    private RecommendationService recommendationService;

    /**
     * 获取热门推荐商品数据。
     * 视图只能显示4个热门推荐商品。为保证视图美化。每次返回的数据一定是4条。
     * 除非，数据库中所有的数据累加不足4条，返回所有数据。
     * 数据规则：
     *  1、 查询条件匹配，如果所在城市的推荐商品>=4条，可以处理后返回。
     *  2、 如果查询条件匹配数据不足4条，从其他城市查询热门推荐商品补足。
     *  3、 如果所有城市的热门推荐商品不足4条，从当前城市的非热门推荐商品中补充。
     *  4、 如果所有城市热门推荐+当前城市非热门推荐商品总数不足4条，从其他城市非热门推荐商品中补充。
     *  5、 所有特例情况累加不足4条数据，返回所有数据。
     *
     * @param city 商品所在城市。 查询条件
     * @return
     *  {
     *      "status":200,
     *      "datas":[{id,title,img,link},{},{}]
     *  }
     */
    @RequestMapping("/recommendation")
    public Result<Items> getRecommendations(String city){
        return recommendationService.getRecommendations(city);
    }
}
