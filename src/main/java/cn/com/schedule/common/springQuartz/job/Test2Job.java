package cn.com.schedule.common.springQuartz.job;

import cn.com.schedule.common.constant.JobTypeEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @DESC: Spring Quartz  Test1
 * @Auther: Levi.Ding
 * @Date: 2020/12/3 17:01
 */
@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Test2Job extends AbstractJob {
    @Override
    protected void executeSubJob() {
        log.info("Test2Job ---- {}", JSON.toJSONString(instanceMapModel));
    }

    @Override
    public JobTypeEnum getJobTypeEnum() {
        return JobTypeEnum.TEST_2;
    }
}
