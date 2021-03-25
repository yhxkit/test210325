package com.example.demo21032501.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.estgames.lmstool.directive")
@Data
public class DirectiveWordProperty {
    String column1;
    String column2;
    String username;
}
