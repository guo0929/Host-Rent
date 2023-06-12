package com.livegoods.recommendation.service.impl;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Items;
import com.livegoods.recommendation.dao.RecommendationDao;
import com.livegoods.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {
    @Autowired
    private RecommendationDao recommendationDao;
    @Value("${livegoods.nginx.server}")
    private String nginxServer;

    /**
     * 1、 查询条件匹配，如果所在城市的推荐商品>=4条，可以处理后返回。
     * 2、 如果查询条件匹配数据不足4条，从其他城市查询热门推荐商品补足。
     * 3、 如果所有城市的热门推荐商品不足4条，从当前城市的非热门推荐商品中补充。
     * 4、 如果所有城市热门推荐+当前城市非热门推荐商品总数不足4条，从其他城市非热门推荐商品中补充。
     * 5、 所有特例情况累加不足4条数据，返回所有数据。
     * @param city 查询条件
     * @return
     */
    @Override
    public Result<Items> getRecommendations(String city) {
        // 查询条件匹配的数据，只要4条。 城市所在地是city，且是热门推荐商品
        Query query = Query.query(
                Criteria.where("city").is(city).and("recommendation").is(true)
        );
        // 定义排序规则和分页规则
        query.with(
                PageRequest.of(0, 4,
                        Sort.by(Sort.Direction.DESC, "recommendationSort"))
        );
        // 查询商品数据
        List<Items> result = recommendationDao.findItems4Recommendation(query);

        // 判断数据是否足够
        if(result.size() < 4){
            // 不足4条数据，查询其他城市的热门推荐商品
            query = Query.query(
                    Criteria.where("city").ne(city).and("recommendation").is(true)
            );
            // 定义排序规则和分页规则
            query.with(
                    PageRequest.of(0, 4-result.size(),
                            Sort.by(Sort.Direction.DESC, "recommendationSort"))
            );
            // 查询其他城市的热门推荐
            List<Items> otherRecommendations = recommendationDao.findItems4Recommendation(query);
            // 把其他城市的人买推荐数据保存到结果集合中。
            result.addAll(otherRecommendations);
            // 所有城市的热门推荐商品不足4条
            if(result.size() < 4){
                // 查询当前城市的非热门推荐数据。
                query = Query.query(
                        Criteria.where("city").is(city).and("recommendation").is(false)
                );
                // 定义排序规则和分页规则
                query.with(
                        PageRequest.of(0, 4-result.size())
                );
                // 查询当前城市的非热门推荐数据
                List<Items> currentCityNoneRecommendation =
                        recommendationDao.findItems4Recommendation(query);
                // 保存到结果集合中
                result.addAll(currentCityNoneRecommendation);
                // 结果集合不足4条，查询其他城市非热门推荐商品
                if(result.size() < 4){
                    query = Query.query(
                            Criteria.where("city").ne(city).and("recommendation").is(false)
                    );
                    // 定义排序规则和分页规则
                    query.with(
                            PageRequest.of(0, 4-result.size())
                    );
                    List<Items> otherCityNoneRecommendations =
                            recommendationDao.findItems4Recommendation(query);
                    // 保存到结果集合中
                    result.addAll(otherCityNoneRecommendations);
                    // 所有逻辑结束。即使结果集合不足4条，也没有其他数据了，直接返回。
                }
            }
        }
        // 为结果集合中的每个商品图片地址增加前缀
        for(Items i : result){
            i.setImg(nginxServer+i.getImg());
        }
        return new Result<>(200, result);
    }
}
