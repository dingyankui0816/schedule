package cn.com.schedule.common.springQuartz.job;

import cn.com.schedule.common.constant.CommonConstant;
import cn.com.schedule.common.constant.JobTypeEnum;
import cn.com.schedule.common.springQuartz.model.InstanceMapModel;
import cn.com.schedule.common.springQuartz.model.TriggerModel;
import cn.com.schedule.common.springQuartz.service.ScheduleService;
import cn.com.schedule.common.util.SpringUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @DESC: 抽象job
 * @Auther: Levi.Ding
 * @Date: 2020/12/7 13:50
 */
@Slf4j
public abstract class AbstractJob extends QuartzJobBean {

    //todo 因为quartz框架是 反射获取对象实例所以spring 无法管理job bean  因此 无法使用 Spring注入的方式 注入service
    protected ScheduleService scheduleService = SpringUtil.getBean("scheduleService",ScheduleService.class);

    /**
     * 定时器实例
     */
    protected InstanceMapModel instanceMapModel;

    public void setInstanceMapModel(InstanceMapModel instanceMapModel) {
        this.instanceMapModel = instanceMapModel;
    }

    public abstract JobTypeEnum getJobTypeEnum();

    /**
     * @DESC: 执行前操作
     * @Auther: Levi.Ding
     * @Date:   2020/12/9  10:04
     * @param
     * @return: void
     */
    public void beforeExecute(JobExecutionContext context){
        scheduleService.incrTriggerExecuteCount(instanceMapModel);
        TriggerModel triggerModel = instanceMapModel.getTriggerModel();
        if (context.getFireTime().compareTo(triggerModel.getStartTime()) == 0){
            scheduleService.updateTriggerStatus(triggerModel.getId(), CommonConstant.TRIGGER_STATUS_PROCESS);
        }else if (context.getNextFireTime() == null){
            scheduleService.updateTriggerStatus(triggerModel.getId(),CommonConstant.TRIGGER_STATUS_FINISHED);
        }

    }

    /**
     * @DESC: 执行后操作
     * @Auther: Levi.Ding
     * @Date:   2020/12/9  10:04
     * @param
     * @return: void
     */
    public void afterExecute(){

    }

    /**
     * @DESC: 重写该方法 实现定时任务
     * @Auther: Levi.Ding
     * @Date:   2020/12/9  9:29
     * @param
     * @return: void
     */
    protected abstract void executeSubJob();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        beforeExecute(context);
        try {
            executeSubJob();
        }catch (Exception e){
            log.error("job execute fail,instanceMapModel : {},e", JSON.toJSONString(instanceMapModel),e);
        }finally {
            afterExecute();
        }
    }
}
