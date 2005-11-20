package org.schweisguth.xt.common.domain;

import java.io.Serializable;
import org.schweisguth.xt.common.util.contract.Assert;

public class Position implements Serializable {
    private static final long serialVersionUID = 4219924874945558986L;

    // Constants
    public static final int BOARD_SIZE = 15;

    // Fields
    private final int mX;
    private final int mY;

    // Constructors

    public Position(int pX, int pY) {
        assertIsValid(pX, pY);

        mX = pX;
        mY = pY;

    }

    // Methods: getters

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public int get(Axis pAxis) {
        Assert.assertNotNull(pAxis);
        return pAxis.equals(Axis.X) ? getX() : getY();
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        if (pOther == null || ! pOther.getClass().equals(getClass())) {
            return false;
        }
        Position other = (Position) pOther;
        return other.getX() == getX() && other.getY() == getY();
    }

    public int hashCode() {
        return 3 * getX() + getY();
    }

    public String toString() {
        return "(" + getX() + ", " + getY() + ')';
    }

    // Methods: static

    public static boolean isValid(int pX, int pY) {
        int boardSize = BOARD_SIZE;
        return 0 <= pX && pX < boardSize && 0 <= pY && pY < boardSize;
    }

    public static void assertIsValid(int pX, int pY) {
        Assert.assertTrue(isValid(pX, pY));
    }

}
