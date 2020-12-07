package cn.com.schedule.common.Job;

import cn.com.schedule.common.constant.JobTypeEnum;
import cn.com.schedule.common.model.InstanceModel;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @DESC: Spring Quartz  Test1
 * @Auther: Levi.Ding
 * @Date: 2020/12/3 16:55
 */
@Slf4j
// https://blog.csdn.net/fly_captain/article/details/83029440 注解作用
@DisallowConcurrentExecution
public class Test1Job extends AbstractJob {

    /**
     * 执行实例
     */
    public InstanceModel instanceModel;

    public void setInstanceModel(InstanceModel instanceModel) {
        this.instanceModel = instanceModel;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) {
        if (this.instanceModel == null){
            this.instanceModel = (InstanceModel) context.get("instanceModel");
        }
        log.info("Test1Job ---- {}", JSON.toJSONString(instanceModel));
    }

    @Override
    public JobTypeEnum getJobTypeEnum() {
        return JobTypeEnum.TEST_1;
    }
}
