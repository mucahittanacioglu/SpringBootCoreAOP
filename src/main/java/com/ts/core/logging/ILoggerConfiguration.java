package com.ts.core.logging;

public interface ILoggerConfiguration {
    void debug(String message);
    void info(String message);
    void warn(String message);
    void error(String message);
}
