package org.schweisguth.xt.common.domain;

import java.io.Serializable;
import org.schweisguth.xt.common.util.contract.Assert;

public class Transfer implements Serializable {
    private static final long serialVersionUID = -4574825160499333873L;

    // Fields
    private final int mRackPosition;
    private final Position mBoardPosition;

    // Constructors

    public Transfer(int pRackPosition, int pX, int pY) {
        this(pRackPosition, new Position(pX, pY));
    }

    public Transfer(int pRackPosition, Position pBoardPosition) {
        Rack.assertIsValid(pRackPosition);
        Assert.assertNotNull(pBoardPosition);

        mRackPosition = pRackPosition;
        mBoardPosition = pBoardPosition;

    }

    // Methods

    public int getRackPosition() {
        return mRackPosition;
    }

    public Position getBoardPosition() {
        return mBoardPosition;
    }

    // Overrides

    public boolean equals(Object pOther) {
        if (pOther == null || ! pOther.getClass().equals(getClass())) {
            return false;
        }
        Transfer other = (Transfer) pOther;
        return other.getRackPosition() == getRackPosition() &&
            other.getBoardPosition().equals(getBoardPosition());
    }

    public int hashCode() {
        return 3 * getRackPosition() + getBoardPosition().hashCode();
    }

    public String toString() {
        return "" + getRackPosition() + "->" + getBoardPosition().toString();
    }

}
