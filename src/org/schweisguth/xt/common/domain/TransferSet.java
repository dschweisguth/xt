package org.schweisguth.xt.common.domain;

import java.io.Serializable;
import java.util.Iterator;
import org.schweisguth.xt.common.util.collection.HashStickySet;
import org.schweisguth.xt.common.util.collection.StickySet;
import org.schweisguth.xt.common.util.contract.Assert;
import org.schweisguth.xt.common.util.contract.AssertionFailedError;

public class TransferSet implements Serializable {
    private static final long serialVersionUID = -3817623760433342906L;

    // Fields
    private final StickySet mTransfers = new HashStickySet();

    // Constructors

    public TransferSet() {
    }

    public TransferSet(TransferSet pSource) {
        Assert.assertNotNull(pSource);
        mTransfers.addAll(pSource.mTransfers);
    }

    public TransferSet(Transfer pTransfer) {
        Assert.assertNotNull(pTransfer);
        add(pTransfer);
    }

    public TransferSet(int pRackPosition, Position pSource) {
        Rack.assertIsValid(pRackPosition);
        Assert.assertNotNull(pSource);

        add(pRackPosition, pSource);

    }

    public TransferSet(int pRackPosition, int pX, int pY) {
        Rack.assertIsValid(pRackPosition);
        Position.assertIsValid(pX, pY);

        add(pRackPosition, pX, pY);

    }

    public TransferSet(int[] pRackPositions, int pX, int pY, Axis pAxis) {
        Assert.assertNotNull(pRackPositions);
        Assert.assertNotNull(pAxis);
        for (int i = 0; i < pRackPositions.length; i++) {
            Rack.assertIsValid(pRackPositions[i]);
            Position.assertIsValid(pX + (pAxis.equals(Axis.X) ? i : 0),
                pY + (pAxis.equals(Axis.Y) ? i : 0));
        }

        for (int i = 0; i < pRackPositions.length; i++) {
            add(pRackPositions[i],
                pX + (pAxis.equals(Axis.X) ? i : 0),
                pY + (pAxis.equals(Axis.Y) ? i : 0));
        }

    }

    public TransferSet(int[] pRackPositions, Position pBoardPosition,
        Axis pAxis) {
        this(pRackPositions, pBoardPosition.getX(), pBoardPosition.getY(),
            pAxis);
    }

    // Methods: queries

    public boolean contains(int pRackPosition) {
        Rack.assertIsValid(pRackPosition);

        Iterator transfers = iterator();
        while (transfers.hasNext()) {
            if (((Transfer) transfers.next()).getRackPosition() ==
                pRackPosition) {
                return true;
            }
        }
        return false;

    }

    private boolean contains(Position pBoardPosition) {
        Assert.assertNotNull(pBoardPosition);

        Iterator transfers = iterator();
        while (transfers.hasNext()) {
            if (((Transfer) transfers.next()).getBoardPosition().equals(
                pBoardPosition)) {
                return true;
            }
        }
        return false;

    }

    private Transfer get(int pRackPosition) {
        Rack.assertIsValid(pRackPosition);
        Assert.assertTrue(contains(pRackPosition));

        Iterator transfers = iterator();
        while (transfers.hasNext()) {
            Transfer transfer = (Transfer) transfers.next();
            if (transfer.getRackPosition() == pRackPosition) {
                return transfer;
            }
        }
        // Compiler doesn't realize that Assert.fail() always throws
        throw new AssertionFailedError();

    }

    private Transfer get(Position pPosition) {
        Assert.assertNotNull(pPosition);
        Assert.assertTrue(contains(pPosition));

        Iterator transfers = iterator();
        while (transfers.hasNext()) {
            Transfer transfer = (Transfer) transfers.next();
            if (transfer.getBoardPosition().equals(pPosition)) {
                return transfer;
            }
        }
        // Compiler doesn't realize that Assert.fail() always throws
        throw new AssertionFailedError();

    }

    public Iterator iterator() {
        return mTransfers.iterator();
    }

    public int size() {
        return mTransfers.size();
    }

    // Methods: commands

    public final void add(Transfer pTransfer) {
        Assert.assertNotNull(pTransfer);
        Assert.assertFalse(contains(pTransfer.getRackPosition()) ||
            contains(pTransfer.getBoardPosition()));

        mTransfers.add(pTransfer);

    }

    public final void add(int pRackPosition, Position pBoardPosition) {
        Rack.assertIsValid(pRackPosition);
        Assert.assertNotNull(pBoardPosition);
        Assert.assertFalse(contains(pRackPosition) || contains(pBoardPosition));

        add(new Transfer(pRackPosition, pBoardPosition));

    }

    public final void add(int pRackPosition, int pX, int pY) {
        Rack.assertIsValid(pRackPosition);
        Position.assertIsValid(pX, pY);
        Assert.assertFalse(contains(pRackPosition) ||
            contains(new Position(pX, pY)));

        add(new Transfer(pRackPosition, pX, pY));

    }

    public void move(int pSource, int pDestination) {
        Rack.assertIsValid(pSource);
        Assert.assertTrue(contains(pSource));
        Rack.assertIsValid(pDestination);

        if (contains(pDestination)) {
            Transfer source = get(pSource);
            Transfer destination = get(pDestination);
            mTransfers.remove(source);
            mTransfers.remove(destination);
            add(new Transfer(pDestination, source.getBoardPosition()));
            add(new Transfer(pSource, destination.getBoardPosition()));
        } else {
            Transfer old = get(pSource);
            mTransfers.remove(old);
            add(new Transfer(pDestination, old.getBoardPosition()));
        }

    }

    public void move(Position pSource, Position pDestination) {
        Assert.assertNotNull(pSource);
        Assert.assertTrue(contains(pSource));
        Assert.assertNotNull(pDestination);

        if (contains(pDestination)) {
            Transfer source = get(pSource);
            Transfer destination = get(pDestination);
            mTransfers.remove(source);
            mTransfers.remove(destination);
            add(new Transfer(source.getRackPosition(), pDestination));
            add(new Transfer(destination.getRackPosition(), pSource));
        } else {
            Transfer old = get(pSource);
            mTransfers.remove(old);
            add(new Transfer(old.getRackPosition(), pDestination));
        }

    }

    private void remove(Position pPosition) {
        Assert.assertNotNull(pPosition);
        Assert.assertTrue(contains(pPosition));

        mTransfers.remove(get(pPosition));

    }

    public void clear() {
        mTransfers.clear();
    }

    public void takeBack(Transfer pTransfer) {
        Assert.assertNotNull(pTransfer);
        Assert.assertTrue(contains(pTransfer.getBoardPosition()));

        Position boardPosition = pTransfer.getBoardPosition();
        int targetRackPosition = pTransfer.getRackPosition();
        int originalRackPosition = get(boardPosition).getRackPosition();
        remove(boardPosition);
        if (contains(targetRackPosition)) {
            move(targetRackPosition, originalRackPosition);
        }

    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        return pOther != null && pOther.getClass().equals(getClass()) &&
            mTransfers.equals(((TransferSet) pOther).mTransfers);
    }

    public int hashCode() {
        return mTransfers.hashCode();
    }

    public String toString() {
        return mTransfers.toString();
    }

}
