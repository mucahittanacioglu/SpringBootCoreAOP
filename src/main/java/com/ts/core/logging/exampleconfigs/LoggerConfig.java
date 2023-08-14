package com.ts.core.logging.exampleconfigs;

import com.ts.core.logging.ILoggerConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@ConditionalOnProperty(name = "logging.enabled", havingValue = "true")
public class LoggerConfig {

    @Bean
    @ConditionalOnProperty(name = "logger.profile", havingValue = "log4j")
    public ILoggerConfiguration log4j() {
        return new Log4j2LoggerConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "logger.profile", havingValue = "util")
    public ILoggerConfiguration javUtilLogger() {
        return new JavaUtilLoggerConfiguration();
    }
}