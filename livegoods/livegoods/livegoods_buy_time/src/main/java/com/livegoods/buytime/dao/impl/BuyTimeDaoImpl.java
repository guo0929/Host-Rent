package com.livegoods.buytime.dao.impl;

import com.livegoods.buytime.dao.BuyTimeDao;
import com.livegoods.pojo.Houses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BuyTimeDaoImpl implements BuyTimeDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Houses findHouseById(String id) {

        return mongoTemplate.findById(id, Houses.class);
    }
}
