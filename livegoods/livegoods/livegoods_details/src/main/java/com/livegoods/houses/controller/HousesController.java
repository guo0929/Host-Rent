package com.livegoods.houses.controller;

import com.livegoods.commons.pojo.Result;
import com.livegoods.houses.service.HousesService;
import com.livegoods.pojo.Houses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class HousesController {
    @Autowired
    private HousesService housesService;

    /**
     * 根据主键查询房屋
     * @param id
     * @return
     */
    @GetMapping("/details")
    public Houses showHouseDetailsById(String id){
        return housesService.getHouseById(id);
    }

    /**
     * 更新房屋库存方法
     * @param houses
     * @return
     */
    @PostMapping("/modifyHousesNum")
    public Result<Houses> modifyHousesNum(@RequestBody Houses houses){
        return housesService.modifyHousesNum(houses);
    }

    /**
     * 查询MongoDB中所有的房屋，为ES初始化数据提供基础。
     * @return
     */
    @GetMapping("/getAll")
    public List<Houses> getAll(){
        return housesService.getAll();
    }
}
