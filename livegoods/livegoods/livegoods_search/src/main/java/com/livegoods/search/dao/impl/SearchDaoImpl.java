package com.livegoods.search.dao.impl;

import com.livegoods.search.dao.SearchDao;
import com.livegoods.search.pojo.House4ES;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDaoImpl implements SearchDao {
    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    /**
     * 搜索, 条件： 城市等值判断。 标题（title），房屋类型（houseType），租赁方式（rentType）模糊匹配
     * 高亮处理结果。
     * @param city 城市
     * @param content 搜索关键字
     * @param page 页码
     * @return
     *  {
     *      "pages":总计页数,
     *      "contents":数据集合
     *  }
     */
    @Override
    public Map<String, Object> search(String city, String content, int page, int rows) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        // 城市等值判断,短语搜索，条件不分词。必要条件
        builder.must(QueryBuilders.matchPhraseQuery("city", city));
        // 标题模糊匹配，可选条件
        builder.should(QueryBuilders.matchQuery("title", content));
        builder.should(QueryBuilders.matchQuery("houseType", content));
        builder.should(QueryBuilders.matchQuery("rentType", content));

        // 高亮字段包括： 标题，房屋类型，租赁方式。
        HighlightBuilder.Field titleField = new HighlightBuilder.Field("title");
        titleField.preTags("<span style='color:red'>");
        titleField.postTags("</span>");
        titleField.fragmentSize(10);
        titleField.numOfFragments(1);
        HighlightBuilder.Field houseTypeField = new HighlightBuilder.Field("houseType");
        houseTypeField.preTags("<span style='color:red'>");
        houseTypeField.postTags("</span>");
        houseTypeField.fragmentSize(10);
        houseTypeField.numOfFragments(1);
        HighlightBuilder.Field rentTypeField = new HighlightBuilder.Field("rentType");
        rentTypeField.preTags("<span style='color:red'>");
        rentTypeField.postTags("</span>");
        rentTypeField.fragmentSize(10);
        rentTypeField.numOfFragments(1);

        SearchQuery query =
                new NativeSearchQueryBuilder()
                        .withQuery(builder) // 搜索条件
                        .withHighlightFields(titleField,houseTypeField,rentTypeField)
                        .withPageable(
                                PageRequest.of(page, rows)
                        )
                        .build();

        AggregatedPage<House4ES> resultPage = restTemplate.queryForPage(query, House4ES.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                // 搜索结果对象，包括总计行数。
                SearchHits hits = searchResponse.getHits();
                // 搜索结果集合
                SearchHit[] hitArray = hits.getHits();
                List<T> resultList = new ArrayList<>();
                for(SearchHit hit : hitArray){
                    House4ES house4ES = new House4ES();
                    // 获取保存的源数据
                    Map<String, Object> hitMap = hit.getSourceAsMap();
                    house4ES.setId(hitMap.get("id").toString());
                    house4ES.setPrice(hitMap.get("price").toString());
                    house4ES.setImg(hitMap.get("img").toString());
                    house4ES.setCity(hitMap.get("city").toString());
                    // 获取当前数据的高亮
                    Map<String, HighlightField> highlightFieldMap = hit.getHighlightFields();
                    if(highlightFieldMap.containsKey("title")){
                        // 有标题高亮
                        house4ES.setTitle(highlightFieldMap.get("title").getFragments()[0].toString());
                    }else{
                        // 没有标题高亮
                        house4ES.setTitle(hitMap.get("title").toString());
                    }
                    if(highlightFieldMap.containsKey("houseType")){
                        // 有房屋类型高亮
                        house4ES.setHouseType(highlightFieldMap.get("houseType").getFragments()[0].toString());
                    }else{
                        // 没有房屋类型高亮
                        house4ES.setHouseType(hitMap.get("houseType").toString());
                    }
                    if(highlightFieldMap.containsKey("rentType")){
                        // 有租赁方式高亮
                        house4ES.setRentType(highlightFieldMap.get("rentType").getFragments()[0].toString());
                    }else{
                        // 没有租赁方式高亮
                        house4ES.setRentType(hitMap.get("rentType").toString());
                    }
                    resultList.add((T) house4ES);
                }
                return new AggregatedPageImpl<>(
                        resultList, pageable, hits.getTotalHits()
                );
            }

            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                return null;
            }
        });

        Map<String, Object> resultMap = new HashMap<>();
        // 总计页数
        resultMap.put("pages", resultPage.getTotalPages());
        // 当前也显示的数据集合
        resultMap.put("contents", resultPage.getContent());

        return resultMap;
    }

    @Override
    public void saveHouses(List<House4ES> list) {
        // 批量保存数据到ES
        List<IndexQuery> queries = new ArrayList<>();
        for(House4ES h : list){
            queries.add(
                    new IndexQueryBuilder().withObject(h).build()
            );
        }
        restTemplate.bulkIndex(queries);
    }
}
