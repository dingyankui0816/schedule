package cn.com.schedule.common.springQuartz.config;

import cn.com.schedule.common.springQuartz.job.Test1Job;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.*;

/**
 * @DESC: 定时任务配置 静态定时任务
 * @Auther: Levi.Ding
 * @Date: 2020/12/7 9:32
 */
@Configuration
public class StaticScheduleFactoryConfig {

    @Bean(name = "staticSchedulerFactoryBean")
    public SchedulerFactoryBean getSchedulerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean, SimpleTriggerFactoryBean simpleTriggerFactoryBean){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        schedulerFactoryBean.setJobDetails(jobDetailFactoryBean.getObject());
//        schedulerFactoryBean.setTriggers(simpleTriggerFactoryBean.getObject());
        schedulerFactoryBean.setAutoStartup(true);
        return schedulerFactoryBean;
    }

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean(){

        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        Map<String,String> test1 = new HashMap<>();
        test1.put("test1","test1");
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.setJobClass(Test1Job.class);
        jobDetailFactoryBean.setJobDataAsMap(test1);

        return jobDetailFactoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){

        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        simpleTriggerFactoryBean.setRepeatInterval(1000);
        //RepeatCount 是重复次数，并不是总执行次数，第一次不算重复，所以总执行次数 应该是 1 + repeatCount
        simpleTriggerFactoryBean.setRepeatCount(9);
        simpleTriggerFactoryBean.setStartDelay(2000);
        simpleTriggerFactoryBean.afterPropertiesSet();
        return simpleTriggerFactoryBean;

    }
}
