package cn.com.schedule.common.config;

import cn.com.schedule.common.constant.CommonConstant;
import cn.com.schedule.common.model.InstanceMapModel;
import cn.com.schedule.common.model.InstanceModel;
import cn.com.schedule.common.model.TriggerModel;
import cn.com.schedule.common.service.ScheduleService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
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
        List<InstanceMapModel> instanceMapModels = scheduleService.getJdbcInstanceModels();
        //获取可用的实例job
        instanceMapModels = instanceMapModels.parallelStream()
                .map(instanceMapModel -> {
                    TriggerModel triggerModel = scheduleService.refreshTriggerStatus(instanceMapModel.getTriggerModel());
                    instanceMapModel.setTriggerModel(triggerModel);
                    return instanceMapModel;
                })
                .filter(instanceMapModel -> !instanceMapModel.getTriggerModel().getStatus().equals(CommonConstant.TRIGGER_STATUS_FINISHED))
                .collect(Collectors.toList());

        Map<JobDetail,Set<? extends Trigger>> jobDetailSetMap = Maps.newHashMap();
        instanceMapModels.parallelStream().collect(Collectors.groupingBy(InstanceMapModel::getInstanceId)).forEach((k,v) ->{
            InstanceModel instanceModel = v.parallelStream().filter(v1 -> v1.getInstanceId().equals(k)).findAny().get().getInstanceModel();
            JobDetail jobDetail = getJobDetail(instanceModel);
            jobDetailSetMap.put(jobDetail,getTriggers(v,jobDetail));
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
    public static JobDetail getJobDetail(InstanceModel instanceModel){
        return JobBuilder.newJob()
                .ofType(instanceModel.getJobTypeEnum().getJobClazz()).storeDurably(true)
                .withDescription(instanceModel.getName())
                .withIdentity(getJobKey(instanceModel)).build();
    }

    /**
     * @DESC: 获取job key
     * @Auther: Levi.Ding
     * @Date:   2020/12/9  10:54
     * @param instanceModel
     * @return: org.quartz.JobKey
     */
    public static JobKey getJobKey(InstanceModel instanceModel){
        return JobKey.jobKey(instanceModel.getName().concat(instanceModel.getId().toString()),instanceModel.getJobTypeEnum().name());
    }

    /**
     * @DESC: 获取 trigger key
     * @Auther: Levi.Ding
     * @Date:   2020/12/9  10:57
     * @param instanceMapModel
     * @return: org.quartz.TriggerKey
     */
    public static TriggerKey getTriggerKey(InstanceMapModel instanceMapModel){
        InstanceModel instanceModel = instanceMapModel.getInstanceModel();
        TriggerModel triggerModel = instanceMapModel.getTriggerModel();
        return TriggerKey.triggerKey(instanceModel.getName().concat(instanceModel.getId().toString()).concat(triggerModel.getName()),instanceModel.getName().concat(instanceModel.getId().toString()));
    }

    /**
     * @DESC: 根据元数据获取对应 Triggers
     * @Auther: Levi.Ding
     * @Date:   2020/12/8  16:23
     * @param instanceMapModels
     * @param jobDetail
     * @return: java.util.List<org.quartz.Trigger>
     */
    public static Set<SimpleTrigger> getTriggers(List<InstanceMapModel> instanceMapModels, JobDetail jobDetail) {
        return instanceMapModels.parallelStream()
                .map(instanceMapModel -> getTrigger(instanceMapModel,jobDetail))
                .collect(Collectors.toSet());
    }

    /**
     * @DESC: 获取触发器
     * @Auther: Levi.Ding
     * @Date:   2020/12/9  10:39
     * @param instanceMapModel
     * @param jobDetail
     * @return: org.quartz.SimpleTrigger
     */
    public static SimpleTrigger getTrigger(InstanceMapModel instanceMapModel,JobDetail jobDetail){
        //传入job中的参数，job和trigger都可以传入这个参数 如果参数相同会被覆盖
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("instanceMapModel",instanceMapModel);
        //设置触发器属性
        TriggerModel triggerModel = instanceMapModel.getTriggerModel();
        int count = -1;
        if (triggerModel.getCount() > 0) {
            count = triggerModel.getCount() - instanceMapModel.getExecuteCount() -1 ;
        }
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMilliseconds(triggerModel.getDelay());
        //获取当前剩余需要执行的次数    -1:一直执行
        simpleScheduleBuilder.withRepeatCount(count);

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .startAt(triggerModel.getStartTime())
                .endAt(triggerModel.getEndTime())
                .withSchedule(simpleScheduleBuilder)
                .usingJobData(jobDataMap)
                .withIdentity(getTriggerKey(instanceMapModel))
                .build();
    }
}
