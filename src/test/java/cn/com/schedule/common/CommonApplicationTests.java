package cn.com.schedule.common;

import cn.com.schedule.common.timer.Jdk1_5TimerTask;
import cn.com.schedule.common.timer.JdkTimerTask;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Timer;
import java.util.concurrent.*;

@Slf4j
@SpringBootTest
class CommonApplicationTests {

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

}
