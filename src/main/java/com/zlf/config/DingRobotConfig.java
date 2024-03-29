package com.zlf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "zlf.robot")
public class DingRobotConfig {

    private List<RobotProperties> rbs;

}
