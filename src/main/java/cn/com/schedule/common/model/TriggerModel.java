package cn.com.schedule.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
     * 执行状态 1、未开始 2、进行中 3、已结束
     */
    private Integer status;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public TriggerModel(BigInteger id, String name, Date startTime, Integer count, Long delay, Integer status, Date endTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.count = count;
        this.delay = delay;
        this.status = status;
        this.endTime = endTime;
    }

    public TriggerModel() {
    }
}
