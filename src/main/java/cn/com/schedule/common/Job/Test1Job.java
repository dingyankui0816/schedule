package cn.com.schedule.common.Job;

import cn.com.schedule.common.constant.JobTypeEnum;
import cn.com.schedule.common.model.InstanceModel;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @DESC: Spring Quartz  Test1
 * @Auther: Levi.Ding
 * @Date: 2020/12/3 16:55
 */
@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
// https://blog.csdn.net/fly_captain/article/details/83029440 注解作用
@DisallowConcurrentExecution
public class Test1Job extends AbstractJob {

    @Override
    protected void executeSubJob() {
        log.info("----------------------Test1Job start-------------------------------");
        log.info("Test1Job ---- {}", JSON.toJSONString(instanceMapModel.getInstanceId()));
        log.info("Test1Job ---- {}", JSON.toJSONString(instanceMapModel.getTriggerId()));
        log.info("----------------------Test1Job end---------------------------------");
    }

    @Override
    public JobTypeEnum getJobTypeEnum() {
        return JobTypeEnum.TEST_1;
    }
}
