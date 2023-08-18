package com.ts.core.logging.exampleconfigs;

import com.ts.core.logging.ILoggerConfiguration;

import java.io.IOException;
import java.util.logging.*;

public class JavaUtilLoggerConfiguration implements ILoggerConfiguration {

    private final java.util.logging.Logger logger;

    public JavaUtilLoggerConfiguration() {
        this.logger = java.util.logging.Logger.getLogger(this.getClass().getName());
        try {
            // Remove default console handler
            for (Handler defaultHandler : logger.getHandlers()) {
                logger.removeHandler(defaultHandler);
            }

            // Create console handler
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            consoleHandler.setFormatter(new SimpleFormatter());

            // Create file handler
            FileHandler fileHandler = new FileHandler("logs/util.log", true); // true stands for "append"
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());

            // Assign handlers to the logger
            logger.addHandler(consoleHandler);
            logger.addHandler(fileHandler);

            // Set logger level
            logger.setLevel(Level.ALL);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void debug(String message) {
        logger.log(Level.FINE, message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warning(message);
    }

    @Override
    public void error(String message) {
        logger.log(Level.SEVERE, message);
    }
}
