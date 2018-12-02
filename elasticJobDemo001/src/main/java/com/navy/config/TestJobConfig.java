package com.navy.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.navy.job.TestJob;

@Configuration
public class TestJobConfig {
	
	@Value("${testJob.cron}")
	private String cron;
	
	@Value("${testJob.shardingTotalCount}")
	private int shardingTotalCount;
	
	@Value("${testJob.shardingItemParameter}")
	private String shardingItemParameter;
	
	@Autowired
	private TestJob testJob;
	
	@Autowired
	private ZookeeperRegistryCenter elasticJobRegCenter;
	
	@Bean(initMethod="init")
	public JobScheduler testJobJobScheduler() {
		return new SpringJobScheduler(testJob, elasticJobRegCenter, getLiteJobConfiguration());
	}
	
	private LiteJobConfiguration getLiteJobConfiguration() {
		SimpleJobConfiguration sjc = getSimpleJobConfiguration();
		LiteJobConfiguration ljc = LiteJobConfiguration.newBuilder(sjc)
													   .monitorExecution(false)
													   .overwrite(true)
													   .build();
		return ljc;
	}
	
	private SimpleJobConfiguration getSimpleJobConfiguration() {
		JobCoreConfiguration jcc = getJobCoreConfiguration();
		SimpleJobConfiguration sjc = new SimpleJobConfiguration(jcc, TestJob.class.getCanonicalName());
		return sjc;
	}
	
	private JobCoreConfiguration getJobCoreConfiguration() {
		JobCoreConfiguration jcc = JobCoreConfiguration.newBuilder(TestJob.class.getName(),cron,shardingTotalCount)
													   .shardingItemParameters(shardingItemParameter)
													   .build();
		return jcc;
	}
}
