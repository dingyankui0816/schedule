package cn.com.schedule.common.constant;

import cn.com.schedule.common.Job.Test1Job;
import cn.com.schedule.common.Job.Test2Job;
import org.quartz.Job;

/**
 * @DESC: job类型枚举
 * @Auther: Levi.Ding
 * @Date: 2020/12/7 13:49
 */
public enum JobTypeEnum {
    TEST_1(Test1Job.class),TEST_2(Test2Job.class);

    /**
     * 定时任务类型
     */
    private Class<? extends Job> jobClazz;

    public Class<? extends Job> getJobClazz() {
        return jobClazz;
    }

    JobTypeEnum(Class<? extends Job> jobClazz) {
        this.jobClazz = jobClazz;
    }
}
