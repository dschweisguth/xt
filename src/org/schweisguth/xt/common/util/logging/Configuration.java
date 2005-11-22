package org.schweisguth.xt.common.util.logging;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

class Configuration {
    // Constants
    private static final Configuration INSTANCE = new Configuration();
    private static final String FILE_NAME = "logging.properties";

    // Methods: static

    public static Configuration instance() {
        return INSTANCE;
    }

    // Fields: instance
    private Properties mProperties = null;

    // Constructors

    private Configuration() {
    }

    // Methods:

    public String getLogLevel() {
        return getProperties().getProperty("logLevel", "WARNING");
    }

    private synchronized Properties getProperties() {
        if (mProperties == null) {
            Properties properties = new Properties();
            try {
                InputStream stream = new FileInputStream(FILE_NAME);
                try {
                    properties.load(stream);
                } catch (IOException e) {
                    log("WARNING", "Couldn't load " + FILE_NAME, e);
                } finally {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        log("WARNING", "Couldn't close " + FILE_NAME, e);
                    }
                }
            } catch (FileNotFoundException e) {
                log("CONFIG", "Couldn't find " + FILE_NAME);
            }
            mProperties = properties;
        }
        return mProperties;
    }

    private static void log(String pLevel, String pMessage) {
        System.err.println(new Date());
        System.err.println(pLevel + ": " + pMessage);
    }

    private static void log(String pLevel, String pMessage, Throwable pCause) {
        log(pLevel, pMessage);
        System.err.println(pCause);
    }

}
