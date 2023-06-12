package com.livegoods.comments.dao.impl;

import com.livegoods.comments.dao.CommentsDao;
import com.livegoods.pojo.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CommentsDaoImpl implements CommentsDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Comments> findCommentsByHouseId(String id, int page, int rows) {
        Query query = Query.query(
                Criteria.where("houseId").is(id)
        );
        query.with( // 主键降序排列
                PageRequest.of(page, rows, Sort.by(Sort.Direction.DESC, "_id"))
        );

        return mongoTemplate.find(query, Comments.class);
    }

    /**
     * 聚合查询，查询数据行数
     * @param id 房屋主键。
     * @return
     */
    @Override
    public long findCommentsRowsByHouseId(String id) {
        TypedAggregation<Comments> type = TypedAggregation.newAggregation(
                Comments.class,
                Aggregation.match(Criteria.where("houseId").is(id)), // 条件，房屋主键
                Aggregation.group().count().as("rows")
        );
        AggregationResults<Map> results = mongoTemplate.aggregate(type, Map.class);
        List<Map> list = results.getMappedResults();
        if (list == null || list.size() == 0) {
            // 没有对应数据。返回0；
            return 0L;
        }
        // 返回查询的结果数据。 即某房屋对应的评论总数。
        return Long.parseLong(list.get(0).get("rows").toString());
    }
}
