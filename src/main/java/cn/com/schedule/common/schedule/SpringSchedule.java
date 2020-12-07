package cn.com.schedule.common.schedule;

import cn.com.schedule.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Spring 定时器 默认单节点
 * @DESC: Spring 定时器
 * @Auther: Levi.Ding
 * @Date: 2020/12/3 15:34
 */
@Slf4j
@Component
public class SpringSchedule {

//    @Scheduled(cron = "0/1 * * * * ? ")
    public void scheduleTestA(){

        Date now = new Date();
        log.info("------------------------- {} Task Start scheduleTestA ---------------------",Thread.currentThread().getName());
        log.info("执行时间:{}", CommonConstant.FAST_DATE_FORMAT.format(now));
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            log.error("exec schedule fail!",e);
//        }
        log.info("------------------------- {} Task End scheduleTestA -----------------------",Thread.currentThread().getName());
    }

//    @Scheduled(cron = "0/1 * * * * ? ")
    public void scheduleTestB(){

        Date now = new Date();
        log.info("------------------------- {} Task Start scheduleTestB ---------------------",Thread.currentThread().getName());
        log.info("执行时间:{}", CommonConstant.FAST_DATE_FORMAT.format(now));
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            log.error("exec schedule fail!",e);
//        }
        log.info("------------------------- {} Task End scheduleTestB -----------------------",Thread.currentThread().getName());
    }
}
