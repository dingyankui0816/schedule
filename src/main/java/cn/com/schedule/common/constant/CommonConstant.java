package cn.com.schedule.common.constant;

import cn.com.schedule.common.model.InstanceModel;
import cn.com.schedule.common.model.TriggerModel;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.FastDateFormat;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

/**
 * @DESC: 基础常量
 * @Auther: Levi.Ding
 * @Date: 2020/12/1 16:31
 */
public class CommonConstant {
    /**
     * 时间转换
     */
    public static final FastDateFormat FAST_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    /**
     * 模拟数据库中实例列表
     */
    public static List<InstanceModel> instanceModels = Lists.newArrayList();
    /**
     * 模拟数据库中触发器列表
     */
    public static List<TriggerModel> triggerModels = Lists.newArrayList();


    static {
        instanceModels.add(new InstanceModel(BigInteger.valueOf(1),"实例_1","执行实例_1",JobTypeEnum.TEST_1,Lists.newArrayList(BigInteger.valueOf(1))));
        instanceModels.add(new InstanceModel(BigInteger.valueOf(2),"实例_2","执行实例_2",JobTypeEnum.TEST_2,Lists.newArrayList(BigInteger.valueOf(2),BigInteger.valueOf(3))));
        instanceModels.add(new InstanceModel(BigInteger.valueOf(3),"实例_3","执行实例_3",JobTypeEnum.TEST_1,Lists.newArrayList(BigInteger.valueOf(1),BigInteger.valueOf(2),BigInteger.valueOf(3))));
        instanceModels.add(new InstanceModel(BigInteger.valueOf(4),"实例_4","执行实例_4",JobTypeEnum.TEST_2,Lists.newArrayList(BigInteger.valueOf(1),BigInteger.valueOf(2),BigInteger.valueOf(3))));
        instanceModels.add(new InstanceModel(BigInteger.valueOf(5),"实例_5","执行实例_5",JobTypeEnum.TEST_2,Lists.newArrayList(BigInteger.valueOf(1),BigInteger.valueOf(2),BigInteger.valueOf(3))));

        try {
            triggerModels.add(new TriggerModel(BigInteger.valueOf(1),"触发器_1",CommonConstant.FAST_DATE_FORMAT.parse("2020-12-07 12:00:00"),10,1000*60L,0,1));
            triggerModels.add(new TriggerModel(BigInteger.valueOf(2),"触发器_2",CommonConstant.FAST_DATE_FORMAT.parse("2020-12-07 16:00:00"),-1,1000*60L,0,1));
            triggerModels.add(new TriggerModel(BigInteger.valueOf(1),"触发器_3",CommonConstant.FAST_DATE_FORMAT.parse("2020-12-07 14:00:00"),10,1000*60L,3,2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
