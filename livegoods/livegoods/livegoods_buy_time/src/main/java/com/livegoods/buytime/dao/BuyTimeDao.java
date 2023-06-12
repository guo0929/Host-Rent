package com.livegoods.buytime.dao;

import com.livegoods.pojo.Houses;

public interface BuyTimeDao {
    /**
     * 根据主键查询房屋
     * 房屋可预定时间数据来源：
     *  1、 后台管理系统处理。
     *  2、 根据平台中的合同系统自动计算。如：租赁到期时间-30天等。
     *
     * 当前系统，暂时自定义时间。
     * @param id
     * @return
     */
    Houses findHouseById(String id);
}
