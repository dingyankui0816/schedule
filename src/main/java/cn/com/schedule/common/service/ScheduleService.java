package cn.com.schedule.common.service;

import cn.com.schedule.common.constant.CommonConstant;
import cn.com.schedule.common.model.InstanceModel;
import cn.com.schedule.common.model.TriggerModel;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @DESC: 定时Service
 * @Auther: Levi.Ding
 * @Date: 2020/12/8 16:24
 */
@Service
public class ScheduleService {


    /**
     * @DESC: 从数据库中获取对应定时实例
     * @Auther: Levi.Ding
     * @Date:   2020/12/8  16:25
     * @param
     * @return: java.util.List<cn.com.schedule.common.model.InstanceModel>
     */
    public List<InstanceModel> getJdbcInstanceModels() {
        //实例列表
        return CommonConstant.instanceModels.parallelStream()
                .map(instanceModel -> {
                    instanceModel.setTriggerModels(CommonConstant.triggerModels.parallelStream()
                            .filter(triggerModel -> !triggerModel.getStatus().equals(CommonConstant.TRIGGER_STATUS_FINISHED))
                            .filter(triggerModel -> instanceModel.getTriggerIds().contains(triggerModel.getId()))
                            .collect(Collectors.toList()));
                    return instanceModel;
                })
                .filter(instanceModel -> !CollectionUtils.isEmpty(instanceModel.getTriggerModels()))
                .collect(Collectors.toList());
    }

    public void incrTriggerExecuteCount(TriggerModel triggerModel){
        //todo 如果一个job 拥有两个触发器 是否共用 ，如果共用 触发次数该怎么增加
    }
}
