package com.jackxue.monitor.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource oneDataSource(){
        return DruidDataSourceBuilder.create().build();
    }


    @Bean
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource twoDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource oneDataSource, DataSource twoDataSource){
        Map<Object, Object> targetSources = new HashMap<>(2);
        targetSources.put(DataSourceNames.ONE, oneDataSource);
        targetSources.put(DataSourceNames.TWO, twoDataSource);
        return new DynamicDataSource(oneDataSource, targetSources);
    }

}
