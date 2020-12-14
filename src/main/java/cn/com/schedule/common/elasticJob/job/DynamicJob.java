package cn.com.schedule.common.elasticJob.job;

import cn.com.schedule.common.springQuartz.service.ScheduleService;
import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @DESC: 文件介绍
 * @Auther: Levi.Ding
 * @Date: 2020/12/14 14:40
 */
@Slf4j
@Component
public class DynamicJob implements SimpleJob {

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public void execute(ShardingContext shardingContext) {

        log.info("scheduleService : {}", JSON.toJSON(scheduleService));
        log.info("------- Thread Id : {}, 任务总分片 : {}, 当前分片项 : {}, 当前分片参数 : {}",Thread.currentThread().getId()
                ,shardingContext.getShardingTotalCount(),shardingContext.getShardingItem(),shardingContext.getShardingParameter());


    }
}
