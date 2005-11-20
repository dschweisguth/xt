package org.schweisguth.xt.common.util.logging;

import java.util.Date;

public class Logger {
    public static final Logger global = new Logger();

    private Logger() {
    }

    public void log(Level pLevel, String pMessage) {
        String logLevel = Configuration.instance().getLogLevel();
        if (shouldLog(logLevel, pLevel)) {
            System.err.println(new Date());
            System.err.println(pLevel + ": " + pMessage);
        }
    }

    public void log(Level pLevel, String pMessage, Throwable pThrowable) {
        log(pLevel, pMessage);
        String logLevel = Configuration.instance().getLogLevel();
        if (shouldLog(logLevel, pLevel)) {
            pThrowable.printStackTrace(System.err);
        }
    }

    public void severe(String pMessage) {
        log(Level.SEVERE, pMessage);
    }

    public void warning(String pMessage) {
        log(Level.WARNING, pMessage);
    }

    public void warning(String pMessage, Throwable pCause) {
        log(Level.WARNING, pMessage, pCause);
    }

    public void info(String pMessage) {
        log(Level.INFO, pMessage);
    }

    public void config(String pMessage) {
        log(Level.CONFIG, pMessage);
    }

    public void fine(String pMessage) {
        log(Level.FINE, pMessage);
    }

    public void finer(String pMessage) {
        log(Level.FINER, pMessage);
    }

    private static boolean shouldLog(String pLogLevel, Level pMessageLevel) {
        return pMessageLevel.equals(Level.SEVERE) ||
            pMessageLevel.equals(Level.WARNING) &&
                (pLogLevel.equals(Level.WARNING.getName()) ||
                    pLogLevel.equals(Level.INFO.getName()) ||
                    pLogLevel.equals(Level.CONFIG.getName()) ||
                    pLogLevel.equals(Level.FINE.getName()) ||
                    pLogLevel.equals(Level.FINER.getName())) ||
            pMessageLevel.equals(Level.INFO) &&
                (pLogLevel.equals(Level.INFO.getName()) ||
                    pLogLevel.equals(Level.CONFIG.getName()) ||
                    pLogLevel.equals(Level.FINE.getName()) ||
                    pLogLevel.equals(Level.FINER.getName())) ||
            pMessageLevel.equals(Level.CONFIG) &&
                (pLogLevel.equals(Level.CONFIG.getName()) ||
                    pLogLevel.equals(Level.FINE.getName()) ||
                    pLogLevel.equals(Level.FINER.getName())) ||
            pMessageLevel.equals(Level.FINE) &&
                (pLogLevel.equals(Level.FINE.getName()) ||
                    pLogLevel.equals(Level.FINER.getName())) ||
            pMessageLevel.equals(Level.FINER) &&
                pLogLevel.equals(Level.FINER.getName());
    }

}
