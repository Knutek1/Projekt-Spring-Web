package com.crud.tasks.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "info.company")
@Configuration
public class CompanyConfig {
    private String name;
    private String email;
    private String phone;

}
