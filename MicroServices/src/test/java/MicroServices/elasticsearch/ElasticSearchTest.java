package MicroServices.elasticsearch;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 	测试Elastic Search搜索功能
 * @author JionJion
 */
@RunWith(SpringRunner.class)
@SpringBootTest	
public class ElasticSearchTest {

	@Autowired
	private TransportClient client;
	
	/** 执行查询*/
	@Test
	public void testQuery() {
		GetResponse response = client.prepareGet("people", "man", "1").get();
		if (response.isExists()) {
			System.out.println(response.toString());
		} else {
			System.err.println("没有查找到结果...");
		}
	}
	
	/** 执行新增*/
	@Test
	public void testAdd() {
		String name = "Jion";
		String country = "China";
		String age = "24";
		String date = "1994-4-12";
		try {
			//使用自带的构建JSON字符串
			XContentBuilder builder = XContentFactory.jsonBuilder()
										.startObject()
										.field("name", name)
										.field("country",country)
										.field("age",age)
										.field("date",date)
										.endObject();
			IndexResponse response = client.prepareIndex("people","man")
										.setSource(builder)
										.get();
			
			System.out.println(response.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 执行删除操作*/
	public void testDelete() {
		String id = "";
		DeleteResponse response = client.prepareDelete("people","man",id).get();
		System.out.println(response);
	}
	
	
	/** 执行更新*/
	@Test
	public void testUpdate() {
		String id = "1";
		String name = "JionJion";
		String age = "21";
		UpdateRequest request = new UpdateRequest("people","man",id);
		try {
			//构建JSON字符串
			XContentBuilder builder = XContentFactory.jsonBuilder()
											.startObject()
											.field("name",name)
											.field("age",age)
											.endObject();
			request.doc(builder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			//执行更新
		 	UpdateResponse response = client.update(request).get();
		 	System.out.println(response);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	/** 复杂的布尔查询	查询age在[20,30)之间,country=China*/
	@Test
	public void boolQueryTest() {
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		//字段查询
		boolQuery.must(QueryBuilders.matchQuery("country", "China"));
		
		//范围查询
		RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age")
											.from(20).to(30);
		//向布尔查询添加过滤条件
		boolQuery.filter(rangeQuery);
		
		// 设定索引,类型
		SearchRequestBuilder builder = client.prepareSearch("people")
										.setTypes("man")
										.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
										.setQuery(boolQuery)
										.setFrom(0)
										.setSize(10);
		//查询请求体
		System.out.println(builder);
		//执行请求
		SearchResponse response = builder.get();
		System.out.println(response);
		
	}
	
}

