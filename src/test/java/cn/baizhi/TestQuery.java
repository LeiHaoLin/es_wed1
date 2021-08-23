package cn.baizhi;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class TestQuery {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void test1() throws IOException {
        //创建查询对象
        SearchRequest searchRequest = new SearchRequest();

        //创键查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //指定查询条件 查所有
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        searchRequest.indices("ems")//索引
                .types("emp")       //查询类型
                .source(searchSourceBuilder);//查询条件

        //使用客户端对象 完成查询操作 需要一个查询对象
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println(hits.getMaxScore());//最大得分
        System.out.println(hits.getTotalHits());//查询总条数

        SearchHit[] hits1 = hits.getHits();
        for (SearchHit hit : hits1) {
            System.out.println("当前文档得分: "+hit.getScore());
            System.out.println("当前文档id:  "+hit.getId());
        }
    }
}