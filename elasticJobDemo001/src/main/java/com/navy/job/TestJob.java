package com.navy.job;

import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

@Component
public class TestJob implements SimpleJob{

	@Override
	public void execute(ShardingContext shardingContext) {
		System.out.println(shardingContext.getShardingItem());
	}
}
