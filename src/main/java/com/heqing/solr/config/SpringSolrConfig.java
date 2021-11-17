package com.heqing.solr.config;

import com.heqing.solr.interceptor.AuthRequestInterceptor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

/**
 * @author heqing
 */
@Configuration
@ComponentScan("com.heqing.solr.*")
@EnableSolrRepositories(basePackages = {"com.heqing.solr.repository"})
public class SpringSolrConfig {

    @Autowired
    SolrProperty solrProperty;

    @Bean
    public SolrClient solrClient() {
        // 定义一个可以保存所有Solr基础配置信息的对象
        ModifiableSolrParams solrParams = new ModifiableSolrParams() ;
        solrParams.set(HttpClientUtil.PROP_BASIC_AUTH_USER, solrProperty.getUsername()) ;
        solrParams.set(HttpClientUtil.PROP_BASIC_AUTH_PASS, solrProperty.getPassword()) ;
        // 允许最大的连接数量
        solrParams.set(HttpClientUtil.PROP_MAX_CONNECTIONS, solrProperty.getMaxConnection()) ;
        // 允许进行数据的压缩传输
        solrParams.set(HttpClientUtil.PROP_ALLOW_COMPRESSION, true) ;
        solrParams.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, solrProperty.getPreMaxConnection()) ;
        // 不进行重定向配置
        solrParams.set(HttpClientUtil.PROP_FOLLOW_REDIRECTS, false) ;
        // 将拦截器整合在当前的HttpClient创建的工具类之中
        HttpClientUtil.addRequestInterceptor(new AuthRequestInterceptor());
        // 设置相关的Solr处理参数
        CloseableHttpClient httpClient = HttpClientUtil.createClient(solrParams);
        HttpSolrClient solrClient = new HttpSolrClient.Builder(solrProperty.getSolrHostUrl())
                .withHttpClient(httpClient).withConnectionTimeout(solrProperty.getConnectionTimeout())
                .withSocketTimeout(solrProperty.getSocketTimeout()).build();
        return solrClient ;
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient solrClient) {
        return new SolrTemplate(solrClient);
    }
}
