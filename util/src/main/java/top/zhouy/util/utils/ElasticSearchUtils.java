package top.zhouy.util.utils;

import cn.hutool.system.UserInfo;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.avg.ParsedAvg;
import org.elasticsearch.search.aggregations.metrics.max.ParsedMax;
import org.elasticsearch.search.aggregations.metrics.min.ParsedMin;
import org.elasticsearch.search.aggregations.metrics.percentiles.ParsedPercentiles;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentile;
import org.elasticsearch.search.aggregations.metrics.stats.ParsedStats;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouYan
 * @date 2020/4/16 15:57
 */
@Component
public class ElasticSearchUtils {

    private static Logger log = LoggerFactory.getLogger(ElasticSearchUtils.class);

    private static RestHighLevelClient restHighLevelClient;

    @Autowired
    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        ElasticSearchUtils.restHighLevelClient = restHighLevelClient;
    }

    /**
     * 创建索引
     * @param request
     * @return
     */
    public static Boolean createIndex(CreateIndexRequest request) {
        Boolean success = false;
        try {
            // RestHighLevelClient 执行创建索引
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            // 判断是否创建成功
            success = createIndexResponse.isAcknowledged();
            log.info("是否创建成功：{}", success);
        } catch (IOException e) {
            log.error("创建索引失败", e);
        }
        return success;
    }

    /**
     * 删除索引
     * @param indexName
     * @return
     */
    public static Boolean deleteIndex(String indexName) {
        Boolean success = false;
        try {
            // 新建删除索引请求对象
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            // 执行删除索引
            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
            // 判断是否删除成功
            success = acknowledgedResponse.isAcknowledged();
        } catch (IOException e) {
            log.error("删除失败", e);
        }
        return success;
    }

    /**
     * 保存文档信息
     * @param index
     * @param id
     * @param object
     * @param isDelete
     */
    public static void saveDocument(String index, String id, Object object, Boolean isDelete){
        try {
            // 创建索引请求对象
            GetRequest getRequest = new GetRequest(index, "doc", id);
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            if (!getResponse.isExists()) {
                IndexRequest indexRequest = new IndexRequest(index, "doc", id);
                indexRequest.source(JSON.toJSONBytes(object), XContentType.JSON);
                // 执行增加文档
                IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
                log.info("创建状态：{}", response.status());
            } else {
                if (isDelete) {
                    // 创建删除请求对象
                    DeleteRequest deleteRequest = new DeleteRequest(index, "doc", id);
                    // 执行删除文档
                    DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
                    log.info("删除状态：{}", response.status());
                    return;
                }
                // 创建索引请求对象
                UpdateRequest updateRequest = new UpdateRequest(index, "doc", id);
                // 设置更新文档内容
                updateRequest.doc(JSON.toJSONBytes(object), XContentType.JSON);
                // 执行更新文档
                UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
                log.info("创建状态：{}", response.status());
            }
        } catch (IOException e){
            log.error("保存文档失败");
        }
    }

    /**
     * 查询数据
     * @param searchRequest
     * @return
     */
    public static Object query(SearchRequest searchRequest) {
        List<Object> returnObject = new ArrayList<>();
        try {
            // 执行查询，然后处理响应结果
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            // 根据状态和数据条数验证是否返回了数据
            if (RestStatus.OK.equals(searchResponse.status()) && searchResponse.getHits().totalHits > 0) {
                SearchHits hits = searchResponse.getHits();
                for (SearchHit hit : hits) {
                    // 输出查询信息
                    log.info(JSON.parseObject(hit.getSourceAsString(), UserInfo.class).toString());
                    returnObject.add(hit.getSourceAsString());
                }
            }
        } catch (IOException e) {
            log.error("查询数据失败", e);
        }
        return returnObject;
    }
}
