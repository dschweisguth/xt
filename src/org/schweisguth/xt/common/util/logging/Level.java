package org.schweisguth.xt.common.util.logging;

public class Level {
    // Constants
    public static final Level SEVERE = new Level("SEVERE");
    public static final Level WARNING = new Level("WARNING");
    public static final Level INFO = new Level("INFO");
    public static final Level CONFIG = new Level("CONFIG");
    public static final Level FINE = new Level("FINE");
    public static final Level FINER = new Level("FINER");

    // Fields
    private final String mName;

    // Constructors

    private Level(String pName) {
        mName = pName;
    }

    // Methods

    public String getName() {
        return mName;
    }

    // Methods: overrides

    public String toString() {
        return mName;
    }

}
