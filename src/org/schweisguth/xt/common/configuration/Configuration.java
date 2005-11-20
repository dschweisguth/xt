package org.schweisguth.xt.common.configuration;

import org.schweisguth.xt.common.util.logging.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    // Constants
    private static final Configuration INSTANCE = new Configuration();
    private static final String FILE_NAME = "crosstease.properties";

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

    public String get(Class pClass, String pKey, String pDefault) {
        return getProperties().getProperty(pClass.getName() + "." + pKey, pDefault);
    }

    public int getInt(Class pClass, String pKey, int pDefault) {
        String property = getProperties().getProperty(pClass.getName() + "." + pKey);
        return property == null ? pDefault : Integer.parseInt(property);
    }

    private synchronized Properties getProperties() {
        if (mProperties == null) {
            Properties properties = new Properties();
            try {
                InputStream stream = new FileInputStream(FILE_NAME);
                try {
                    properties.load(stream);
                }
                catch (IOException e) {
                    Logger.global.warning("Couldn't load " + FILE_NAME, e);
                }
                finally {
                    try {
                        stream.close();
                    }
                    catch (IOException e) {
                        Logger.global.warning("Couldn't close " + FILE_NAME, e);
                    }
                }
            }
            catch (FileNotFoundException e) {
                Logger.global.config("Couldn't find " + FILE_NAME);
            }
            mProperties = properties;
        }
        return mProperties;
    }

}
