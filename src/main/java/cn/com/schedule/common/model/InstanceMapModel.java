package cn.com.schedule.common.model;

import java.math.BigInteger;

/**
 * @DESC: 实例关系model
 * @Auther: Levi.Ding
 * @Date: 2020/12/9 9:35
 */
public class InstanceMapModel {


    /**
     * 编号
     */
    private BigInteger id;
    /**
     * 实例id
     */
    private BigInteger instanceId;

    /**
     * 实例model
     */
    private InstanceModel instanceModel;

    /**
     * 触发器id
     */
    private BigInteger triggerId;

    /**
     * 触发器model
     */
    private TriggerModel triggerModel;

    /**
     * 实际执行的次数
     */
    private int executeCount;

    public BigInteger getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(BigInteger instanceId) {
        this.instanceId = instanceId;
    }

    public InstanceModel getInstanceModel() {
        return instanceModel;
    }

    public void setInstanceModel(InstanceModel instanceModel) {
        this.instanceModel = instanceModel;
    }

    public BigInteger getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(BigInteger triggerId) {
        this.triggerId = triggerId;
    }

    public TriggerModel getTriggerModel() {
        return triggerModel;
    }

    public void setTriggerModel(TriggerModel triggerModel) {
        this.triggerModel = triggerModel;
    }

    public int getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(int executeCount) {
        this.executeCount = executeCount;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public InstanceMapModel(BigInteger id,BigInteger instanceId, BigInteger triggerId, int executeCount) {
        this.id = id;
        this.instanceId = instanceId;
        this.triggerId = triggerId;
        this.executeCount = executeCount;
    }
}
