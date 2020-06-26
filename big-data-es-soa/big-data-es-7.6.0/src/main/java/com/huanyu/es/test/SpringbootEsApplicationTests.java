package com.huanyu.es.test;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
@Slf4j
class SpringbootEsApplicationTests {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @Test
    private void contextLoads() {
    }


    /**
     * 创建索引
     */
    @Test
    void createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("index1");
        CreateIndexResponse createIndexResponse =
                client.indices().create(request, RequestOptions.DEFAULT);
    }
    //删除索引
    @Test
    void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("index1");
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        boolean isSuccessful = delete.isAcknowledged();

        System.out.println(isSuccessful);
    }
    //返回索引是否存在
    @Test
    void existIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("index1");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);

        System.out.println(exists);
    }

    //添加文档
    @Test
    void addDocument() throws IOException {

    }





}
