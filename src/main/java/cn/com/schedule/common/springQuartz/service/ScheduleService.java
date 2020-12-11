package cn.com.schedule.common.springQuartz.service;

import cn.com.schedule.common.springQuartz.config.DynamicScheduleFactoryConfig;
import cn.com.schedule.common.constant.CommonConstant;
import cn.com.schedule.common.springQuartz.model.InstanceMapModel;
import cn.com.schedule.common.springQuartz.model.TriggerModel;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @DESC: 定时Service
 * @Auther: Levi.Ding
 * @Date: 2020/12/8 16:24
 */
@Slf4j
@Service
public class ScheduleService {

    @Resource(name = "dynamicScheduleFactory")
    private Scheduler scheduler ;

    /**
     * @DESC: 从数据库中获取对应定时实例
     * @Auther: Levi.Ding
     * @Date:   2020/12/8  16:25
     * @param
     * @return: java.util.List<cn.com.schedule.common.springQuartz.model.InstanceModel>
     */
    public List<InstanceMapModel> getJdbcInstanceModels() {
        //实例列表
        return CommonConstant.instanceMapModels.parallelStream()
                .map(instanceMapModel -> {
                    instanceMapModel.setTriggerModel(CommonConstant.triggerModels.parallelStream()
                            .filter(triggerModel -> !triggerModel.getStatus().equals(CommonConstant.TRIGGER_STATUS_FINISHED))
                            .filter(triggerModel -> instanceMapModel.getTriggerId().equals(triggerModel.getId()))
                            .findAny()
                            .orElse(null));
                    instanceMapModel.setInstanceModel(CommonConstant.instanceModels.parallelStream()
                            .filter(instanceModel -> instanceModel.getId().equals(instanceMapModel.getInstanceId()))
                            .findAny()
                            .orElse(null));
                    return instanceMapModel;
                })
                .filter(instanceMapModel -> instanceMapModel.getTriggerModel()!=null&&instanceMapModel.getInstanceModel()!=null)
                .collect(Collectors.toList());
    }

    /**
     * @DESC: 执行次数 + 1
     * @Auther: Levi.Ding
     * @Date:   2020/12/9  10:09
     * @param instanceMapModel
     * @return: void
     */
    public void incrTriggerExecuteCount(InstanceMapModel instanceMapModel){
        CommonConstant.instanceMapModels.parallelStream().forEach(imm -> {
            if (!imm.getId().equals(instanceMapModel.getId())){
                return;
            }
            imm.setExecuteCount(instanceMapModel.getExecuteCount() + 1);
        });
    }

    /**
     * @DESC: 修改触发器状态
     * @Auther: Levi.Ding
     * @Date:   2020/12/9  10:14
     * @param id 触发器编号
     * @param status 触发器状态
     * @return: void
     */
    public void updateTriggerStatus(BigInteger id,int status){
        CommonConstant.triggerModels.parallelStream().forEach(triggerModel -> {
            if (!triggerModel.getId().equals(id)||triggerModel.getStatus().equals(status)){
                return;
            }
            triggerModel.setStatus(status);
        });
    }

    /**
     * @DESC: 获取触发状态
     * @Auther: Levi.Ding
     * @Date:   2020/12/9  10:26
     * @param triggerModel
     * @return: int
     */
    public int getTriggerStatus(TriggerModel triggerModel){
        Date now = new Date();
        if (triggerModel.getStartTime().after(now)){
            return CommonConstant.TRIGGER_STATUS_NOT_START;
        }
        if (triggerModel.getEndTime() != null && triggerModel.getEndTime().before(now)){
            return CommonConstant.TRIGGER_STATUS_FINISHED;
        }
        return CommonConstant.TRIGGER_STATUS_PROCESS;
    }

    /**
     * @DESC: 刷新触发器状态
     * @Auther: Levi.Ding
     * @Date:   2020/12/9  10:33
     * @param triggerModel
     * @return: cn.com.schedule.common.springQuartz.model.TriggerModel
     */
    public TriggerModel refreshTriggerStatus(TriggerModel triggerModel) {
        int calStatus = this.getTriggerStatus(triggerModel);
        if (calStatus == triggerModel.getStatus().intValue()) {
            return triggerModel;
        }
        this.updateTriggerStatus(triggerModel.getId(), calStatus);
        triggerModel.setStatus(calStatus);
        return triggerModel;
    }

    /**
     * @DESC: 添加job
     * @Auther: Levi.Ding
     * @Date:   2020/12/9  10:41
     * @param instanceMapModel
     * @return: void
     */
    public void addInstanceMapModel(InstanceMapModel instanceMapModel){
        CommonConstant.instanceModels.add(instanceMapModel.getInstanceModel());
        CommonConstant.triggerModels.add(instanceMapModel.getTriggerModel());
        CommonConstant.instanceMapModels.add(instanceMapModel);
        JobDetail jobDetail = DynamicScheduleFactoryConfig.getJobDetail(instanceMapModel.getInstanceModel());
        try {
            scheduler.scheduleJob(jobDetail,DynamicScheduleFactoryConfig.getTrigger(instanceMapModel,jobDetail));
        } catch (SchedulerException e) {
            log.error("scheduleJob fail , InstanceMapModel : {}",instanceMapModel,e);
        }
    }

    /**
     * @DESC: 删除定时器
     * @Auther: Levi.Ding
     * @Date:   2020/12/9  11:08
     * @param id
     * @return: void
     */
    public void deleteTrigger(BigInteger id) {
        CommonConstant.triggerModels = CommonConstant.triggerModels.parallelStream().filter(triggerModel -> !triggerModel.getId().equals(id)).collect(Collectors.toList());
        List<InstanceMapModel> deleteInstanceMapModels = CommonConstant.instanceMapModels.parallelStream().filter(instanceMapModel -> instanceMapModel.getTriggerId().equals(id)).collect(Collectors.toList());
        CommonConstant.instanceMapModels = CommonConstant.instanceMapModels.parallelStream().filter(instanceMapModel -> !instanceMapModel.getTriggerId().equals(id)).collect(Collectors.toList());
        List<TriggerKey> triggerKeys = deleteInstanceMapModels.parallelStream().map(DynamicScheduleFactoryConfig::getTriggerKey).collect(Collectors.toList());
        triggerKeys.parallelStream().forEach(triggerKey -> {
            try {
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
            } catch (SchedulerException e) {
                log.error("pause trigger or unschedule trigger fail , triggerKey:{}", triggerKey, e);
            }
        });
    }
}
