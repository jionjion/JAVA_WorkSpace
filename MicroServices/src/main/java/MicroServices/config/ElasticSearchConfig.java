package MicroServices.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 	Elastic Search的配置类,项目引入原生的TransportClient,作为数据交互端
 * @author JionJion
 */
@Configuration
public class ElasticSearchConfig {

	/**	向容器中注入Bean,名称为client */
	@Bean
	public TransportClient client() throws UnknownHostException {
		
		/** 配置信息*/
		Settings settings = Settings.builder()
								.put("cluster.name", "elasticsearch")					//集群名称,默认为elasticsearch
								.build();
		
		/** 创建连接端*/
		TransportClient client = new PreBuiltTransportClient(settings);

		/** 定义节点*/
		TransportAddress master_node = new TransportAddress(InetAddress.getByName("localhost"), 9300);
		
		/** 添加节点,可重复添加*/
		client.addTransportAddress(master_node);
		
		return client;
	}
}
