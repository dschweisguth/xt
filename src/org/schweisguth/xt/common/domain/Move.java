package org.schweisguth.xt.common.domain;

import java.io.Serializable;
import java.util.Iterator;
import org.schweisguth.xt.common.util.collection.HashStickySet;
import org.schweisguth.xt.common.util.collection.StickySet;
import org.schweisguth.xt.common.util.contract.Assert;

public class Move implements Serializable {
    private static final long serialVersionUID = -5577918057200608424L;

    // Fields
    private final StickySet mPositions = new HashStickySet();

    // Constructors

    public Move() {
        clear();
    }

    // Methods: queries which delegate to mPositions

    public boolean contains(Position pPosition) {
        Assert.assertNotNull(pPosition);
        return mPositions.contains(pPosition);
    }

    public boolean contains(int pX, int pY) {
        Position.assertIsValid(pX, pY);
        return contains(new Position(pX, pY));
    }

    public boolean isEmpty() {
        return mPositions.isEmpty();
    }

    public Iterator iterator() {
        return mPositions.iterator();
    }

    public int size() {
        return mPositions.size();
    }

    // Methods: other queries

    public boolean canFinish() {
        return size() == 1 || hasParallels();
    }

    private boolean hasParallels() {
        return size() > 1 && getParallelsOrNull() != null;
    }

    private void assertHasParallels() {
        Assert.assertTrue(hasParallels());
    }

    public Axis getParallels() {
        assertHasParallels();
        return getParallelsOrNull();
    }

    private Axis getParallelsOrNull() {
        Assert.assertTrue(size() > 1);

        Iterator positions = iterator();
        Position first = (Position) positions.next();
        boolean xAreEqual = true;
        boolean yAreEqual = true;
        while (positions.hasNext()) {
            Position position = (Position) positions.next();
            xAreEqual &= first.getX() == position.getX();
            yAreEqual &= first.getY() == position.getY();
            if (! (xAreEqual || yAreEqual)) {
                return null;
            }
        }
        return xAreEqual ? Axis.Y : Axis.X;

    }

    private Axis getCrosses() {
        assertHasParallels();
        return getParallels().other();
    }

    private int getCrossesAt() {
        assertHasParallels();
        return ((Position) iterator().next()).get(getCrosses());
    }

    public StickySet getEnvelope() {
        Assert.assertTrue(canFinish());

        StickySet envelope = new HashStickySet();
        if (size() == 1) {
            envelope.add(iterator().next());
        } else {
            Axis parallels = getParallels();
            int crossesAt = getCrossesAt();
            Iterator positions = iterator();
            Position first = (Position) positions.next();
            int min = first.get(parallels);
            int max = min;
            while (positions.hasNext()) {
                int current = ((Position) positions.next()).get(parallels);
                min = Math.min(min, current);
                max = Math.max(max, current);
            }
            for (int pos = min; pos <= max; pos++) {
                envelope.add(parallels.equals(Axis.X)
                    ? new Position(pos, crossesAt)
                    : new Position(crossesAt, pos));
            }
        }
        return envelope;

    }

    // Methods: commands

    public void add(Position pNewPosition) {
        Assert.assertNotNull(pNewPosition);
        Assert.assertTrue(! contains(pNewPosition));

        mPositions.add(pNewPosition);

    }

    public void remove(Position pPosition) {
        Assert.assertNotNull(pPosition);
        Assert.assertTrue(contains(pPosition));

        mPositions.remove(pPosition);

    }

    public final void clear() {
        mPositions.clear();
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        return pOther != null && pOther.getClass().equals(getClass()) &&
            mPositions.equals(((Move) pOther).mPositions);
    }

    public int hashCode() {
        return mPositions.hashCode();
    }

    public String toString() {
        return mPositions.toString();
    }

}
