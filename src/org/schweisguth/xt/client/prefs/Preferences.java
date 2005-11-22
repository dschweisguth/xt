package org.schweisguth.xt.client.prefs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.schweisguth.xt.common.util.collection.HashStickyMap;
import org.schweisguth.xt.common.util.collection.StickyMap;
import org.schweisguth.xt.common.util.logging.Level;
import org.schweisguth.xt.common.util.logging.Logger;

public class Preferences {
    // Constants
    private static final StickyMap INSTANCES = new HashStickyMap();
    private static final String FILE_NAME = "crosstease.prefs.properties";
    private static Properties mProperties = null;

    // Methods: static

    public static Preferences userNodeForPackage(Class pClass) {
        String nodeName = pClass.getName();
        if (! INSTANCES.containsKey(nodeName)) {
            INSTANCES.put(nodeName, new Preferences(nodeName));
        }
        return (Preferences) INSTANCES.get(nodeName);
    }

    // Fields
    private final String mNodeName;

    // Constructors

    private Preferences(String pNodeName) {
        mNodeName = pNodeName;
    }

    // Methods: queries

    public String get(String pKey, String pDefault) {
        return getProperties().getProperty(qualify(pKey), pDefault);
    }

    public int getInt(String pKey, int pDefault) {
        String valueFromStore = getProperties().getProperty(qualify(pKey));
        return valueFromStore == null
            ? pDefault
            : Integer.parseInt(valueFromStore);
    }

    // Methods: commands

    public void put(String pKey, String pValue) {
        getProperties().setProperty(qualify(pKey), pValue);
        save();
    }

    public void putInt(String pKey, int pValue) {
        put(pKey, String.valueOf(pValue));
    }

    // Methods: helper

    private String qualify(String pKey) {
        return mNodeName + "." + pKey;
    }

    private static synchronized Properties getProperties() {
        if (mProperties == null) {
            Properties properties = new Properties();
            try {
                InputStream stream = new FileInputStream(FILE_NAME);
                try {
                    properties.load(stream);
                } catch (IOException e) {
                    Logger.global.log(Level.WARNING,
                        "Couldn't load " + FILE_NAME, e);
                } finally {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        Logger.global.log(Level.WARNING,
                            "Couldn't close " + FILE_NAME, e);
                    }
                }
            } catch (FileNotFoundException e) {
                Logger.global.config("Couldn't find " + FILE_NAME);
            }
            mProperties = properties;
        }
        return mProperties;
    }

    private static synchronized void save() {
        try {
            FileOutputStream stream = new FileOutputStream(FILE_NAME);
            try {
                mProperties.store(stream, "");
                try {
                    stream.close();
                } catch (IOException e) {
                    Logger.global.log(Level.WARNING,
                        "Couldn't close " + FILE_NAME, e);
                }
            } catch (IOException e) {
                Logger.global.log(Level.WARNING, "Couldn't write " + FILE_NAME, e);
            }
        } catch (FileNotFoundException e) {
            Logger.global.log(Level.WARNING, "Couldn't open " + FILE_NAME, e);
        }
    }

}
