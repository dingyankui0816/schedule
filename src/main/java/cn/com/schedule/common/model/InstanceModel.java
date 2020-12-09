package cn.com.schedule.common.model;

import cn.com.schedule.common.constant.JobTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigInteger;
import java.util.List;

/**
 * @DESC: 实际执行 model
 * @Auther: Levi.Ding
 * @Date: 2020/12/7 13:34
 */
public class InstanceModel {
    /**
     * 实例编号
     */
    private BigInteger id;
    /**
     * 实例名称
     */
    private String name;
    /**
     * 执行内容
     */
    private String executeContent;

    /**
     * 定时任务类型枚举
     */
    private JobTypeEnum jobTypeEnum;

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

    public String getExecuteContent() {
        return executeContent;
    }

    public void setExecuteContent(String executeContent) {
        this.executeContent = executeContent;
    }

    public JobTypeEnum getJobTypeEnum() {
        return jobTypeEnum;
    }

    public void setJobTypeEnum(JobTypeEnum jobTypeEnum) {
        this.jobTypeEnum = jobTypeEnum;
    }

    public InstanceModel(BigInteger id, String name, String executeContent, JobTypeEnum jobTypeEnum) {
        this.id = id;
        this.name = name;
        this.executeContent = executeContent;
        this.jobTypeEnum = jobTypeEnum;
    }

    public InstanceModel() {
    }
}
