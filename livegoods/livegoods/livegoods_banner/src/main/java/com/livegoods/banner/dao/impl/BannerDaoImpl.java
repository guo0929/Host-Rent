package com.livegoods.banner.dao.impl;

import com.livegoods.banner.dao.BannerDao;
import com.livegoods.banner.pojo.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class BannerDaoImpl implements BannerDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询数据
     * @return
     */
    @Override
    public List<Banner> getBanners() {
        Date now = new Date();
        Query query = Query.query(
                Criteria.where("beginTime").lt(now)
                        .and("endTime").gt(now)
        );
        return mongoTemplate.find(query, Banner.class);
    }
}
