package org.schweisguth.xt.common.domain;

import java.io.Serializable;

public class Axis implements Serializable {
    private static final long serialVersionUID = -6310703825271128476L;

    // Fields

    public static final Axis X = new Axis('X');
    public static final Axis Y = new Axis('Y');

    private final char mIdentity;

    // Constructors

    private Axis(char pIdentity) {
        mIdentity = pIdentity;
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        return pOther != null && pOther.getClass().equals(getClass()) &&
            ((Axis) pOther).mIdentity == mIdentity;
    }

    public int hashCode() {
        return (int) mIdentity;
    }

    public String toString() {
        return String.valueOf(mIdentity);
    }

    // Methods: other

    public Axis other() {
        return equals(X) ? Y : X;
    }

}
