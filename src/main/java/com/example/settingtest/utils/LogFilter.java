package com.example.settingtest.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getMessage().contains("select 1") || event.getThreadName().contains("commons-pool-evictor-thread")) {
            return FilterReply.DENY;
        }
        return FilterReply.NEUTRAL;
    }
}
