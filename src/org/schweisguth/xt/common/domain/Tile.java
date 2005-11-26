package org.schweisguth.xt.common.domain;

import java.io.Serializable;
import org.schweisguth.xt.common.util.collection.HashStickySetMap;
import org.schweisguth.xt.common.util.collection.StickySetMap;
import org.schweisguth.xt.common.util.contract.Assert;

public class Tile implements Comparable, Serializable {
    private static final long serialVersionUID = -492381682368041394L;

    // Fields: static
    private static final StickySetMap INSTANCES = new HashStickySetMap();

    static {
        char[][] data = {
            { 'A', 1 },
            { 'B', 3 },
            { 'C', 3 },
            { 'D', 2 },
            { 'E', 1 },
            { 'F', 4 },
            { 'G', 2 },
            { 'H', 4 },
            { 'I', 1 },
            { 'J', 8 },
            { 'K', 5 },
            { 'L', 1 },
            { 'M', 3 },
            { 'N', 1 },
            { 'O', 1 },
            { 'P', 3 },
            { 'Q', 10 },
            { 'R', 1 },
            { 'S', 1 },
            { 'T', 1 },
            { 'U', 1 },
            { 'V', 4 },
            { 'W', 4 },
            { 'X', 8 },
            { 'Y', 4 },
            { 'Z', 10 },
            { ' ', 0 }
        };
        for (int i = 0; i < data.length; i++) {
            char[] datum = data[i];
            char letter = datum[0];
            INSTANCES.put(new Character(letter), new Tile(letter, datum[1]));
        }
    }

    // Methods: static

    public static Tile get(char pLetter) {
        Assert.assertTrue(isLetter(pLetter));
        return (Tile) INSTANCES.get(new Character(pLetter));
    }

    public static boolean isLetter(char pChar) {
        return INSTANCES.containsKey(new Character(pChar));
    }

    // Fields: instance
    private final Character mLetter;
    private final int mValue;

    // Constructors

    private Tile(char pLetter, int pValue) {
        mLetter = new Character(pLetter);
        mValue = pValue;
    }

    // Methods: other

    public char getLetter() {
        return mLetter.charValue();
    }

    public int getValue() {
        return mValue;
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        return pOther != null && pOther.getClass().equals(getClass()) &&
            ((Tile) pOther).mLetter.equals(mLetter);
    }

    public int hashCode() {
        return mLetter.hashCode();
    }

    public String toString() {
        return mLetter.toString();
    }

    // Methods: implements Comparable

    public int compareTo(Object pOther) {
        if (pOther == null) {
            throw new ClassCastException(
                "Attempted to compare " + this + " to null");
        }
        Tile other = (Tile) pOther;
        if (getLetter() == ' ' ^ other.getLetter() == ' ') {
            return -1 * mLetter.compareTo(other.mLetter);
        } else {
            return mLetter.compareTo(other.mLetter);
        }
    }

}
