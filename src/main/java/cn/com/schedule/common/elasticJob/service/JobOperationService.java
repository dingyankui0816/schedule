package cn.com.schedule.common.elasticJob.service;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @DESC: 作业操作实体
 * @Auther: Levi.Ding
 * @Date: 2020/12/14 14:24
 */
@Service
public class JobOperationService {

    @Autowired
    private ZookeeperRegistryCenter regCenter;

    /**
     *
     * @param jobName:任务名称，注意不要带#特殊字符。
     * @param simpleJob:实现simpleJob自己的任务类
     * @param cron
     * @param shardingTotalCount：分片总数
     * @param shardingItemParameters：个性化参数，可以和分片项匹配对应关系，用于将分片项的数字转换为更加可读的业务代码。
     */
    public void addjobs(final String jobName , final SimpleJob simpleJob, final String cron, final int shardingTotalCount,
                        final String shardingItemParameters){
        simpleJobScheduler(jobName,simpleJob,cron,shardingTotalCount,shardingItemParameters).init();
    }


    public JobScheduler simpleJobScheduler(final String jobName , final SimpleJob simpleJob, final String cron, final int shardingTotalCount,
                                           final String shardingItemParameters) {
        LiteJobConfiguration liteJobConfiguration =  getLiteJobConfiguration(jobName ,simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters);
        return new SpringJobScheduler(simpleJob, regCenter,liteJobConfiguration /*, jobEventConfiguration*/);
    }

    private LiteJobConfiguration getLiteJobConfiguration(final String jobName ,final Class<? extends SimpleJob> jobClass, final String cron, final int shardingTotalCount, final String shardingItemParameters) {

        /**
         * JobCoreConfiguration.newBuilder(String jobName, String cron, int shardingTotalCount)
         * 其中jobName是任务名称
         * cron是cron表达式
         * shardingTotalCount是分片总数
         * 这样就可以把jobClass.getName()变成我们自己命名的jobName
         */
//        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(
//                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build();

        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(
                jobName, cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build();


        JobTypeConfiguration jobTypeConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, jobClass.getCanonicalName());

        return LiteJobConfiguration.newBuilder(jobTypeConfiguration).overwrite(true).build();

       /* return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(
                jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();*/
    }

}
