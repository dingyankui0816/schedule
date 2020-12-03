package cn.com.schedule.common.timer;

import cn.com.schedule.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @DESC: jdk1.5 执行定时任务
 * @Auther: Levi.Ding
 * @Date: 2020/12/3 12:49
 */
@Slf4j
public class Jdk1_5TimerTask implements Runnable {


    private int count = 1;
    private int total = 10;
    private String name ;

    @Override
    public void run() {
        Date now = new Date();
        log.info("------------------------- {} Task Start {} ---------------------",Thread.currentThread().getName(),name);
        log.info("名称:{},执行时间:{},执行次数:{}",name,CommonConstant.FAST_DATE_FORMAT.format(now),count);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("exec schedule fail!",e);
        }
        log.info("------------------------- {} Task End {} -----------------------",Thread.currentThread().getName(),name);
        count ++ ;
        if (count > total){
            log.info("执行次数达到最大值");
        }
    }

    public Jdk1_5TimerTask(int total,String name) {
        this.total = total;
        this.name = name;
    }

    public Jdk1_5TimerTask() {
    }
}
