package com.livegoods.hotproduct.dao;

import com.livegoods.pojo.Items;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface HotProductDao {
    List<Items> findItems4HotProduct(Query query);
}
