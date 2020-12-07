package cn.com.schedule.common.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @DESC: 触发器 model
 * @Auther: Levi.Ding
 * @Date: 2020/12/7 13:22
 */
public class TriggerModel {

    private BigInteger id;
    /**
     * 触发器名称
     */
    private String name;
    /**
     * 第一次触发时间
     */
    private Date startTime;
    /**
     * 触发总数
     */
    private Integer count;
    /**
     * 间隔时间 ；毫秒
     */
    private Long delay;

    /**
     * 已执行次数
     */
    private Integer executeCount;

    /**
     * 执行状态 1、未开始 2、进行中 3、已结束
     */
    private Integer status;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public Integer getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(Integer executeCount) {
        this.executeCount = executeCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public TriggerModel(BigInteger id, String name, Date startTime, Integer count, Long delay, Integer executeCount, Integer status) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.count = count;
        this.delay = delay;
        this.executeCount = executeCount;
        this.status = status;
    }

    public TriggerModel() {
    }
}
