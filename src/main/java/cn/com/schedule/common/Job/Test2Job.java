package cn.com.schedule.common.Job;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @DESC: Spring Quartz  Test1
 * @Auther: Levi.Ding
 * @Date: 2020/12/3 17:01
 */
@Slf4j
public class Test2Job extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("Test2Job ---- {}", JSON.toJSONString(context));
    }
}
