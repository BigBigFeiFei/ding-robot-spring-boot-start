package com.zlf.starter;


import com.zlf.config.DingRobotConfig;
import com.zlf.service.DingRobotService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 钉钉机器人服务配置类
 *
 * @author zlf
 * @date 2024/3/14
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(DingRobotConfig.class)
public class DingRobotServiceConfiguration {

    @Bean
    public DingRobotService dingRobotService(DingRobotConfig dingRobotConfig) {
        return new DingRobotService(dingRobotConfig);
    }

}
