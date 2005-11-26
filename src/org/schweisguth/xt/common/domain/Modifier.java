package org.schweisguth.xt.common.domain;

import java.io.Serializable;

public class Modifier implements Serializable {
    private static final long serialVersionUID = 4892494604293559148L;

    // Constants
    public static final Modifier DEFAULT = new Modifier(1, 1);
    public static final Modifier DOUBLE_LETTER = new Modifier(2, 1);
    public static final Modifier TRIPLE_LETTER = new Modifier(3, 1);
    public static final Modifier DOUBLE_WORD = new Modifier(1, 2);
    public static final Modifier TRIPLE_WORD = new Modifier(1, 3);

    // Fields
    private final int mLetterModifier;
    private final int mWordModifier;

    // Constructors

    private Modifier(int pLetterModifier, int pWordModifier) {
        mLetterModifier = pLetterModifier;
        mWordModifier = pWordModifier;
    }

    // Methods: other

    public int getLetterMultiplier() {
        return mLetterModifier;
    }

    public int getWordMultiplier() {
        return mWordModifier;
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        if (pOther == null || ! pOther.getClass().equals(getClass())) {
            return false;
        }
        Modifier other = (Modifier) pOther;
        return other.mLetterModifier == mLetterModifier &&
            other.mWordModifier == mWordModifier;
    }

    public int hashCode() {
        return 3 * mLetterModifier + mWordModifier;
    }

    public String toString() {
        if (mLetterModifier > 1) {
            return mLetterModifier + "LS";
        } else if (mWordModifier > 1) {
            return mWordModifier + "WS";
        } else {
            return "   ";
        }
    }

}
