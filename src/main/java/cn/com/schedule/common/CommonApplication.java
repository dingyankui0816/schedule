package cn.com.schedule.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * [timer jdk原生,
 * ScheduledExecutorService jdk1.5线程池 并行处理，
 * Spring Schedule spring支持定时器,
 * Quarts 定时器框架 支持集群模式，但是使用数据库锁模式效率低
 * ] 博客 : https://www.jianshu.com/p/7fc2e3834899
 *
 * 当当Job改造Quarts 支持切片，集群，拥有后台管理界面，用于调试执行 博客 :
 */
@EnableScheduling
@SpringBootApplication
@ImportResource("applicationContext.xml")
public class CommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class, args);
    }

}
