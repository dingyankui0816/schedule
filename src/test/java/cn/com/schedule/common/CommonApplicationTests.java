package cn.com.schedule.common;

import cn.com.schedule.common.constant.CommonConstant;
import cn.com.schedule.common.elasticJob.job.DynamicJob;
import cn.com.schedule.common.elasticJob.service.JobOperationService;
import cn.com.schedule.common.springQuartz.model.InstanceMapModel;
import cn.com.schedule.common.springQuartz.service.ScheduleService;
import cn.com.schedule.common.timer.Jdk1_5TimerTask;
import cn.com.schedule.common.timer.JdkTimerTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.Timer;
import java.util.concurrent.*;

@Slf4j
@SpringBootTest
class CommonApplicationTests {

    @Autowired
    private ScheduleService scheduleService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testJdkTimer() throws InterruptedException {
        Timer timer = new Timer();
        log.info("start jdk timer");
        timer.schedule(new JdkTimerTask(5),1000,1000);
        log.info("end jdk timer");

        //Unit tests do not execute asynchronous methods
        //Add thread sleep
        TimeUnit.MINUTES.sleep(1);
    }

    @Test
    public void testJdk1_5Scheduled() throws InterruptedException, ExecutionException {
        //Thread Pool Num ScheduledThreadPoolExecutor
        //thread stop eg. scheduledExecutorService.shutdown();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        //执行定时任务 延迟1s 只执行一次
        scheduledExecutorService.schedule(new Jdk1_5TimerTask(5,"schedule"),1,TimeUnit.SECONDS);
        //执行定时任务 延迟10s 进行第一次执行 每次执行间隔1s，间隔时间以上一次开始时间作为标准
        ScheduledFuture<?> scheduleAtFixedRate = scheduledExecutorService.scheduleAtFixedRate(new Jdk1_5TimerTask(5, "scheduleAtFixedRate"), 10, 1, TimeUnit.SECONDS);
        //执行定时任务 延迟10s 进行第一次执行 每次执行间隔1s，间隔时间以上一次结束时间作为标准
        scheduledExecutorService.scheduleWithFixedDelay(new Jdk1_5TimerTask(5,"scheduleWithFixedDelay"),10,1,TimeUnit.SECONDS);
//        log.info("scheduledExecutorService:{}", JSON.toJSONString(scheduleAtFixedRate.get()));
        TimeUnit.MINUTES.sleep(2);
    }


    @Test
    public void testSpringQuartz() throws Exception {
        //设置JobDetail

        //设置Trigger



        TimeUnit.MINUTES.sleep(2);


    }

    @Test
    public void testSpringQuartzAdd() throws InterruptedException {
        InstanceMapModel instanceMapModel = new InstanceMapModel(new BigInteger("2"),new BigInteger("1"),new BigInteger("1"),0);
        instanceMapModel.setInstanceModel(CommonConstant.instanceModels.get(0));
        instanceMapModel.setTriggerModel(CommonConstant.triggerModels.get(0));
        log.info("add instance ------------- start");
        scheduleService.addInstanceMapModel(instanceMapModel);
        TimeUnit.MINUTES.sleep(1);
        log.info("delete instance ----------- end");
        scheduleService.deleteTrigger(new BigInteger("1"));
        TimeUnit.MINUTES.sleep(1);
    }

//    @Autowired
    private JobOperationService jobOperationService;

    @Autowired
    private DynamicJob dynamicJob;

    /**
     * @DESC: 未实现多节点job自动同步的功能, gitHub中有人实现了当前功能 : https://github.com/yinjihuan/elastic-job-spring-boot-starter
     * @Auther: Levi.Ding
     * @Date:   2020/12/14  15:35
     * @param
     * @return: void
     */
    @Test
    public void testElasticJob() throws InterruptedException {
        jobOperationService.addjobs("test-1",dynamicJob,"0/10 * * * * ?",1,"1=A");

        TimeUnit.MINUTES.sleep(3);
    }

}
