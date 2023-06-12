package com.livegoods.houses.dao.impl;

import com.livegoods.houses.dao.HousesDao;
import com.livegoods.pojo.Houses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HousesDaoImpl implements HousesDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateHousesNum(Houses houses) {
        Query query = Query.query(
                Criteria.where("_id").is(houses.getId())
        );
        Update update = Update.update("nums", houses.getNums());
        mongoTemplate.updateFirst(query, update, Houses.class);
    }

    /**
     * 主键查询
     * @param id
     * @return
     */
    @Override
    public Houses findById(String id) {
        return mongoTemplate.findById(id, Houses.class);
    }

    /**
     * 查询所有房屋
     * @return
     */
    @Override
    public List<Houses> findAll() {
        return mongoTemplate.findAll(Houses.class);
    }
}
