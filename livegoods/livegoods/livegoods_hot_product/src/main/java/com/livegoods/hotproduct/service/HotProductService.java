package com.livegoods.hotproduct.service;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Items;

public interface HotProductService {
    Result<Items> getHotProducts(String city);
}
