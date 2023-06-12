package com.livegoods.houses.service;

import com.livegoods.commons.pojo.Result;
import com.livegoods.pojo.Houses;

import java.util.List;

public interface HousesService {
    List<Houses> getAll();
    // 根据主键查询房屋
    Houses getHouseById(String id);
    Result<Houses> modifyHousesNum(Houses houses);
}
