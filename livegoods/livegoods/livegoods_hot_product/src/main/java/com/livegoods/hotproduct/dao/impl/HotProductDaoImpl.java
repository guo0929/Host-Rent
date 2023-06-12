package com.livegoods.hotproduct.dao.impl;

import com.livegoods.hotproduct.dao.HotProductDao;
import com.livegoods.pojo.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HotProductDaoImpl implements HotProductDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Items> findItems4HotProduct(Query query) {
        return mongoTemplate.find(query, Items.class);
    }
}
