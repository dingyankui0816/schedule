package cn.com.schedule.common.Job;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @DESC: Spring Quartz  Test1
 * @Auther: Levi.Ding
 * @Date: 2020/12/3 16:55
 */
@Slf4j
public class Test1Job extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("Test1Job ---- {}", JSON.toJSONString(context));
    }
}
