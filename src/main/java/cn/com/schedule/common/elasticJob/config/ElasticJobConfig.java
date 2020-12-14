package cn.com.schedule.common.elasticJob.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @DESC:
 * @Auther: Levi.Ding
 * @Date: 2020/12/11 14:30
 */

/**
 * 关于elastic job 对动态添加作业的讲述:
 * https://shardingsphere.apache.org/elasticjob/current/cn/faq/#2-%E6%98%AF%E5%90%A6%E6%94%AF%E6%8C%81%E5%8A%A8%E6%80%81%E6%B7%BB%E5%8A%A0%E4%BD%9C%E4%B8%9A
 * 目前听下来elastic job 只做了在当前节点增加新job ， 如果需要将当前节点新增的job加在zookeeper保证其他集群能用需要扩展elastic 的功能
 */
@Configuration
@ConfigurationProperties(prefix = "my.elastic.job")
public class ElasticJobConfig {

}
