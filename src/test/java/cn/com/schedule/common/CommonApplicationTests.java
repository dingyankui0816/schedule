package cn.com.schedule.common;

import cn.com.schedule.common.timer.JdkTimerTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

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

}
