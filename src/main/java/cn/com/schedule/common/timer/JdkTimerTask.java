package cn.com.schedule.common.timer;

import cn.com.schedule.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.TimerTask;

/**
 * @DESC: jdk原生timerTask任务执行
 * @Auther: Levi.Ding
 * @Date: 2020/12/1 16:15
 */
@Slf4j
public class JdkTimerTask extends TimerTask {

    private int count = 1;
    private int total = 10;

    @Override
    public void run() {
        Date now = new Date();
        log.info("------------------------- Task Start ---------------------");
        log.info("执行时间:{},执行次数:{}", CommonConstant.FAST_DATE_FORMAT.format(now),count);
        log.info("下次执行时间:{}",CommonConstant.FAST_DATE_FORMAT.format(new Date(super.scheduledExecutionTime())));
        log.info("------------------------- Task End -----------------------");
        count ++ ;
        if (count > total){
            log.info("执行次数达到最大值");
            this.cancel();
        }
    }

    public JdkTimerTask(int total) {
        this.total = total;
    }

    public JdkTimerTask() {
    }
}
