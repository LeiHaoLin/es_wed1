package cn.baizhi;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class TestQuery2 {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void test1() throws IOException {
        //2 查询对象
        SearchRequest searchRequest = new SearchRequest();
        //4 定义了查询条件对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //5 条件是 查所有      分页
        searchSourceBuilder.query(QueryBuilders.matchAllQuery())
                .from(0) //从哪条开始
                .size(2) //一页几条
                .sort("age", SortOrder.ASC); //根据age 升序排序
        //3 通过查询对象 查哪个索引 哪个类型
        searchRequest.indices("ems").types("emp").source(searchSourceBuilder);
        //1 通过客户端对象 进行查询操作  需要参数:查询对象
        SearchResponse response = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        //6 得到查询结果
        SearchHits hits = response.getHits();
        //7 得到查询结果所有文档
        SearchHit[] hits1 = hits.getHits();
        //8 遍历 得到每一个文档
        for (SearchHit document : hits1) {
            System.out.println(document.getScore()); //得到文档分数
            System.out.println(document.getId());    //得到文档id
            System.out.println(document.getSourceAsString());// json字符串
        }
    }
}
