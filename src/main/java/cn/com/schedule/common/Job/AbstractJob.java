package cn.com.schedule.common.Job;

import cn.com.schedule.common.constant.JobTypeEnum;
import cn.com.schedule.common.model.InstanceModel;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @DESC: 抽象job
 * @Auther: Levi.Ding
 * @Date: 2020/12/7 13:50
 */
public abstract class AbstractJob extends QuartzJobBean {



    /**
     * 执行实例
     */
    public InstanceModel instanceModel;

    public void setInstanceModel(InstanceModel instanceModel) {
        this.instanceModel = instanceModel;
    }

    public abstract JobTypeEnum getJobTypeEnum();

    public void afterExecute(){

    }

}
