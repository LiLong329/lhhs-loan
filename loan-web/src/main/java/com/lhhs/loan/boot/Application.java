package com.lhhs.loan.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@ImportResource({"spring-context-activiti.xml","consumer.xml"})
@ComponentScan("com.lhhs.loan")
@ComponentScan("com.pathcurve.oss.policy")
@ComponentScan("com.pathcurve.oss.token")
@ComponentScan("com.lhhs.workflow")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
