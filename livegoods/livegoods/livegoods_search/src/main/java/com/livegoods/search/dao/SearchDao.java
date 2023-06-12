package com.livegoods.search.dao;

import com.livegoods.search.pojo.House4ES;

import java.util.List;
import java.util.Map;

public interface SearchDao {
    // 批量保存房屋到ES。
    void saveHouses(List<House4ES> list);

    /**
     * 搜索。注意高亮！！！
     * @param city 城市
     * @param content 搜索关键字
     * @param page 页码
     * @return
     *  {
     *      "pages":总计页数，数学类型,
     *      "contents":[当前页要显示的房屋集合]
     *  }
     */
    Map<String, Object> search(String city, String content, int page, int rows);
}
