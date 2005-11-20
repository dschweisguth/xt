package org.schweisguth.xttest.client.error;

import junit.framework.Protectable;

/**
 * A Thread which runs a Protectable and remembers the first Throwable it
 * throws (if any).
 */
class ProtectiveThread extends Thread {
    // Fields

    /**
     * The Protectable to be run in this Thread.
     */
    private final Protectable mProtectable;

    /**
     * The first Throwable thrown by the Protectable.
     */
    private Throwable mThrown = null;

    // Constructors

    /**
     * Constructs a ProtectiveThread ready to run the given Protectable.
     */
    public ProtectiveThread(Protectable pProtectable) {
        mProtectable = pProtectable;
    }

    // Methods: overrides

    /**
     * Runs the Protectable and remembers the first Throwable it throws.
     */
    public void run() {
        try {
            mProtectable.protect();
        }
        catch (Throwable t) {
            rememberFirstThrown(t);
        }
    }

    // Methods: other

    /**
     * Returns the first Throwable thrown by this ProtectiveThread's
     * Protectable, or null if there was none.
     */
    public synchronized Throwable getFirstThrown() {
        return mThrown;
    }

    private synchronized void rememberFirstThrown(Throwable pThrown) {
        if (mThrown != null) {
            mThrown = pThrown;
        }
    }

}
