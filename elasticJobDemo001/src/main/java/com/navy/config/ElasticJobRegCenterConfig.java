package com.navy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

@Configuration
public class ElasticJobRegCenterConfig {
	
	@Value("${elasticJobRegCenter.serverList}")
	private String serverList;
	
	@Value("${elasticJobRegCenter.namespace}")
	private String namespace;
	
	@Bean(initMethod="init")
	public ZookeeperRegistryCenter elasticJobRegCenter() {
		ZookeeperConfiguration zc = getElasticJobRegCenterZookeeperConfiguration();
		ZookeeperRegistryCenter elasticJobRegCenter = new ZookeeperRegistryCenter(zc);
		return elasticJobRegCenter;
	}
	
	private ZookeeperConfiguration getElasticJobRegCenterZookeeperConfiguration() {
		ZookeeperConfiguration zc = new ZookeeperConfiguration(serverList, namespace);
//		zc.setBaseSleepTimeMilliseconds(baseSleepTimeMilliseconds);
//		zc.setConnectionTimeoutMilliseconds(1000*60);
//		zc.setMaxRetries(maxRetries);
//		zc.setMaxSleepTimeMilliseconds(maxSleepTimeMilliseconds);
		return zc;
	}
}
