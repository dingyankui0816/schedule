package cn.com.schedule.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @DESC: 时间工具类
 * @Auther: Levi.Ding
 * @Date: 2020/12/9 9:10
 */
public class DateUtil {

    /**
     * @DESC: 根据间隔时间和间隔次数计算 总时间 + 1s  防止触发器结束时间和设置的结束时间 相同
     * @Auther: Levi.Ding
     * @Date:   2020/12/9  9:13
     * @param startTime
     * @param count
     * @param intervalTime
     * @return: java.util.Date
     */
    public static Date calDateAddOneSecond(Date startTime,int count,long intervalTime){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.MILLISECOND, (int) (count*intervalTime) + 1000);
        return calendar.getTime();
    }
}
