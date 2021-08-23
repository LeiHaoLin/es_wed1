package cn.baizhi;

import cn.baizhi.entity.Emp;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class EsWed1ApplicationTests {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //创建一个索引
    @Test
    public void test1() throws IOException {
        Emp emp = new Emp("1","雷先生",22);
        //                                      创建索引：指定索引名  类型名   文档的id
        IndexRequest indexRequest = new IndexRequest("ems","emp",emp.getId());
        //添加一个文档 （需要String类型的） 如果是一个java对象需要转json
        indexRequest.source(JSONObject.toJSONString(emp), XContentType.JSON);
        //通过客户端连接es 创建索引
        IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //查所有
    @Test
    public void test2() throws IOException {
        //获取索引信息                               指定索引名   类型名  文档的id
        GetRequest getRequest = new GetRequest("ems","emp","1");
        //创建索引
        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        //表示文档 以json 的形式返回
        response.getSourceAsString();

        Map<String, Object> map = response.getSourceAsMap();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            Object value = map.get(key);
            System.out.println(key+" "+value);
        }
    }

    //修改
    @Test
    public void test3() throws IOException {
        Emp emp = new Emp(null,"天生一道雷",18);
        UpdateRequest updateRequest = new UpdateRequest("ems","emp","1");
        updateRequest.doc(JSONObject.toJSONString(emp),XContentType.JSON);
        UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //删除
    @Test
    public void test4() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("ems","emp","1");
        DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }
}
