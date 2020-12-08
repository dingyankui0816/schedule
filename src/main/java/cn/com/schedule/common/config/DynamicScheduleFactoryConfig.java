package cn.com.schedule.common.config;

import cn.com.schedule.common.model.InstanceModel;
import cn.com.schedule.common.model.TriggerModel;
import cn.com.schedule.common.service.ScheduleService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @DESC: 动态定时工厂
 * @Auther: Levi.Ding
 * @Date: 2020/12/8 15:00
 */
@Slf4j
@Configuration
public class DynamicScheduleFactoryConfig {

    @Bean(name = "dynamicScheduleFactory")
    public SchedulerFactoryBean getDynamicScheduleFactory(QuartzProperties properties){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        if (properties.getSchedulerName() != null) {
            schedulerFactoryBean.setSchedulerName(properties.getSchedulerName());
        }
        schedulerFactoryBean.setAutoStartup(properties.isAutoStartup());
        schedulerFactoryBean.setStartupDelay((int) properties.getStartupDelay().getSeconds());
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(properties.isWaitForJobsToCompleteOnShutdown());
        schedulerFactoryBean.setOverwriteExistingJobs(properties.isOverwriteExistingJobs());
        if (!properties.getProperties().isEmpty()) {
            Properties ps = new Properties();
            ps.putAll(properties.getProperties());
            schedulerFactoryBean.setQuartzProperties(ps);
        }
        return schedulerFactoryBean;
    }

    @Resource(name = "dynamicScheduleFactory")
    private Scheduler scheduler ;

    @Autowired
    private ScheduleService scheduleService;

    @PostConstruct
    public void init(){
        //模拟从数据库获取所有实例和对应的触发器
        List<InstanceModel> instanceModels = scheduleService.getJdbcInstanceModels();

        Map<JobDetail,Set<? extends Trigger>> jobDetailSetMap = Maps.newHashMapWithExpectedSize(instanceModels.size());
        instanceModels.parallelStream()
                .forEach(instanceModel -> {
                    JobDetail jobDetail = getJobDetail(instanceModel);
                    Set<SimpleTrigger> triggers = getTriggers(instanceModel,jobDetail);
                    jobDetailSetMap.put(jobDetail,triggers);
                });

        try {
            scheduler.scheduleJobs(jobDetailSetMap,true);
        } catch (SchedulerException e) {
            log.error("schedule init error! jobDetailSetMap:{}",jobDetailSetMap);
        }
    }




    /**
     * @DESC: 根据元数据获取对应JobDetail
     * @Auther: Levi.Ding
     * @Date:   2020/12/8  16:23
     * @param instanceModel 元数据
     * @return: org.quartz.JobDetail
     */
    public JobDetail getJobDetail(InstanceModel instanceModel){
        return JobBuilder.newJob()
                .ofType(instanceModel.getJobTypeEnum().getJobClazz()).storeDurably(true)
                .withDescription(instanceModel.getName())
                .withIdentity(instanceModel.getName().concat(instanceModel.getId().toString()),instanceModel.getJobTypeEnum().name()).build();
    }

    /**
     * @DESC: 根据元数据获取对应 Triggers
     * @Auther: Levi.Ding
     * @Date:   2020/12/8  16:23
     * @param instanceModel
     * @param jobDetail
     * @return: java.util.List<org.quartz.Trigger>
     */
    public Set<SimpleTrigger> getTriggers(InstanceModel instanceModel, JobDetail jobDetail) {
        //传入job中的参数，job和trigger都可以传入这个参数 如果参数相同会被覆盖
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("instanceModel",instanceModel);
        List<TriggerModel> triggerModels = instanceModel.getTriggerModels();
        return triggerModels.parallelStream().map(triggerModel -> {

            int count = -1;
            if (triggerModel.getCount() > 0) {
                count = triggerModel.getCount() - triggerModel.getExecuteCount() -1 ;
            }
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMilliseconds(triggerModel.getDelay());
            //获取当前剩余需要执行的次数
            if (count != -1) {
                simpleScheduleBuilder.withRepeatCount(count);
            }

            return TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .startAt(triggerModel.getStartTime())
                    .withSchedule(simpleScheduleBuilder)
                    .usingJobData(jobDataMap)
                    .withIdentity(jobDetail.getKey().getName().concat(triggerModel.getName()),jobDetail.getKey().getName())
                    .build();
        }).collect(Collectors.toSet());
    }
}
