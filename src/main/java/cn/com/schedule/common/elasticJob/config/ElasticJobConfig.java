package cn.com.schedule.common.elasticJob.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @DESC:
 * @Auther: Levi.Ding
 * @Date: 2020/12/11 14:30
 */
@Configuration
@ConfigurationProperties(prefix = "my.elastic.job")
public class ElasticJobConfig {

//    @Bean
//    public ZookeeperConfiguration zookeeperConfiguration(){
//        return new ZookeeperConfiguration("172.27.144.185:2181,172.27.144.185:2182,172.27.144.185:2183","levi-job");
//    }
//
//    public


}
