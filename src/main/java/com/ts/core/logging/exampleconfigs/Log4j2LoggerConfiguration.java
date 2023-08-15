package com.ts.core.logging.exampleconfigs;

import com.ts.core.logging.ILoggerConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2LoggerConfiguration implements ILoggerConfiguration {

    private final Logger logger;

    public Log4j2LoggerConfiguration() {
        this.logger = LogManager.getLogger(this.getClass());
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }
}