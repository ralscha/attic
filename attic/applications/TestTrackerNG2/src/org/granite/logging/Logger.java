package org.granite.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Logger {

    ///////////////////////////////////////////////////////////////////////////
    // Fields.

    private final Log log;
    private final LoggingFormatter formatter;

    ///////////////////////////////////////////////////////////////////////////
    // Constructor.

    protected Logger(Log log, LoggingFormatter formatter) {
        this.log = log;
        this.formatter = formatter;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Static initializers.

    public static Logger getLogger() {
        return getLogger(new DefaultLoggingFormatter());
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName(), new DefaultLoggingFormatter());
    }

    public static Logger getLogger(String name) {
        return getLogger(name, new DefaultLoggingFormatter());
    }

    public static Logger getLogger(@SuppressWarnings("unused")LoggingFormatter formatter) {
        Throwable t = new Throwable();
        StackTraceElement[] stes = t.getStackTrace();
        if (stes.length < 2)
            throw new RuntimeException("Illegal instantiation context (stacktrace elements should be of length >= 2)", t);
        return getLogger(stes[1].getClassName());
    }

    public static Logger getLogger(Class<?> clazz, LoggingFormatter formatter) {
        return getLogger(clazz.getName(), formatter);
    }

    public static Logger getLogger(String name, LoggingFormatter formatter) {
        return new Logger(LogFactory.getLog(name), formatter);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Logging methods.

    public void info(String message, Object... args) {
        if (isInfoEnabled())
            log.info(formatter.format(message, args));
    }

    public void info(Throwable t, String message, Object... args) {
        if (isInfoEnabled())
            log.info(formatter.format(message, args), t);
    }

    public void trace(String message, Object... args) {
        if (isTraceEnabled())
            log.trace(formatter.format(message, args));
    }

    public void trace(Throwable t, String message, Object... args) {
        if (isTraceEnabled())
            log.trace(formatter.format(message, args), t);
    }

    public void warn(String message, Object... args) {
        if (isWarnEnabled())
            log.warn(formatter.format(message, args));
    }

    public void warn(Throwable t, String message, Object... args) {
        if (isWarnEnabled())
            log.warn(formatter.format(message, args), t);
    }

    public void debug(String message, Object... args) {
        if (isDebugEnabled())
            log.debug(formatter.format(message, args));
    }

    public void debug(Throwable t, String message, Object... args) {
        if (isDebugEnabled())
            log.debug(formatter.format(message, args), t);
    }

    public void error(String message, Object... args) {
        if (isErrorEnabled())
            log.error(formatter.format(message, args));
    }

    public void error(Throwable t, String message, Object... args) {
        if (isErrorEnabled())
            log.error(formatter.format(message, args), t);
    }

    public void fatal(String message, Object... args) {
        if (isFatalEnabled())
            log.fatal(formatter.format(message, args));
    }

    public void fatal(Throwable t, String message, Object... args) {
        if (isFatalEnabled())
            log.fatal(formatter.format(message, args), t);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Configuration.

    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    public boolean isFatalEnabled() {
        return log.isFatalEnabled();
    }

    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }
}
