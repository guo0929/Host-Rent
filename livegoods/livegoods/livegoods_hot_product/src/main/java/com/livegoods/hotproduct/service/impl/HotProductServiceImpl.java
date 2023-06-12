package com.livegoods.hotproduct.service.impl;

import com.livegoods.commons.pojo.Result;
import com.livegoods.hotproduct.dao.HotProductDao;
import com.livegoods.hotproduct.service.HotProductService;
import com.livegoods.pojo.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotProductServiceImpl implements HotProductService {
    @Autowired
    private HotProductDao hotProductDao;
    @Value("${livegoods.nginx.server}")
    private String nginxServer;

    @Override
    public Result<Items> getHotProducts(String city) {
        // 查询当前城市销量降序数据
        Query query = Query.query(
                Criteria.where("city").is(city)
        );
        // 定义排序规则和分页规则
        query.with(
                PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "sales"))
        );
        List<Items> result = hotProductDao.findItems4HotProduct(query);
        // 当前城市商品数量不足4， 查询其他城市商品
        if(result.size() < 4){
            // 查询其他城市商品数据
            query = Query.query(
                    Criteria.where("city").ne(city)
            );
            // 定义排序规则和分页规则
            query.with(
                    PageRequest.of(0, 4-result.size(),
                            Sort.by(Sort.Direction.DESC, "sales"))
            );
            // 查询
            List<Items> others = hotProductDao.findItems4HotProduct(query);
            // 查询结果加入到返回结果集合中。
            result.addAll(others);
        }
        // 处理每个商品中的图片
        for(Items i : result){
            i.setImg(nginxServer + i.getImg());
        }
        return new Result<>(200, result);
    }
}
