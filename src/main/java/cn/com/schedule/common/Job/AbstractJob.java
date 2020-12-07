package cn.com.schedule.common.Job;

import cn.com.schedule.common.constant.JobTypeEnum;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @DESC: 抽象job
 * @Auther: Levi.Ding
 * @Date: 2020/12/7 13:50
 */
public abstract class AbstractJob extends QuartzJobBean {

    public abstract JobTypeEnum getJobTypeEnum();

}
