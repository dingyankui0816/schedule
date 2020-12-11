package cn.com.schedule.common.elasticJob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * @DESC: elastic job detail
 * @Auther: Levi.Ding
 * @Date: 2020/12/11 14:23
 */
@Slf4j
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("------- Thread Id : {}, 任务总分片 : {}, 当前分片项 : {}",Thread.currentThread().getId()
                ,shardingContext.getShardingTotalCount(),shardingContext.getShardingItem());
    }
}
