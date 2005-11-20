package org.schweisguth.xt.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.contract.Assert;

public class Rack implements Serializable {
    private static final long serialVersionUID = 477943238550716877L;

    // Constants
    public static final int MAX_TILE_COUNT = 7;
    private static final char NO_LETTER = '-';

    // Methods: static

    private static boolean isValid(int pPosition) {
        return 0 <= pPosition && pPosition < MAX_TILE_COUNT;
    }

    public static void assertIsValid(int pPosition) {
        Assert.assertTrue(isValid(pPosition));
    }

    // Fields
    private final List mContents = new ArrayList();

    {
        for (int i = 0; i < MAX_TILE_COUNT; i++) {
            mContents.add(null);
        }
    }

    // Constructors

    public Rack() {
    }

    public Rack(Collection pTiles) {
        Assert.assertNotNull(pTiles);
        Assert.assertTrue(pTiles.size() <= mContents.size());
        Assert.assertTrue(
            CollectionUtil.containsOnlyNullOrInstancesOf(pTiles, Tile.class));

        CollectionUtil.replace(mContents, pTiles);

    }

    public Rack(String pRackAsString) {
        this(asList(pRackAsString));
    }

    private static List asList(String pRackAsString) {
        Assert.assertNotNull(pRackAsString);
        for (int stringPos = 0; stringPos < pRackAsString.length(); stringPos++)
        {
            char letter = pRackAsString.charAt(stringPos);
            Assert.assertTrue(letter == NO_LETTER || Tile.isLetter(letter));
        }

        List list = new ArrayList();
        for (int stringPos = 0; stringPos < pRackAsString.length(); stringPos++)
        {
            char letter = pRackAsString.charAt(stringPos);
            list.add(letter == NO_LETTER ? null : Tile.get(letter));
        }
        return list;

    }

    // Methods: queries

    public boolean contains(int pPosition) {
        assertIsValid(pPosition);
        return get(pPosition) != null;
    }

    public boolean contains(int[] pPositions) {
        for (int position = 0; position < pPositions.length; position++) {
            assertIsValid(pPositions[position]);
        }

        for (int i = 0; i < pPositions.length; i++) {
            if (! contains(pPositions[i])) {
                return false;
            }
        }
        return true;

    }

    public Tile get(int pPosition) {
        assertIsValid(pPosition);

        return (Tile) mContents.get(pPosition);

    }

    public int getScore() {
        int score = 0;
        Iterator tiles = iterator();
        while (tiles.hasNext()) {
            Tile tile = (Tile) tiles.next();
            if (tile != null) {
                score += tile.getValue();
            }
        }
        return score;
    }

    public int getSpaceCount() {
        return MAX_TILE_COUNT - getTileCount();
    }

    public int getTileCount() {
        return CollectionUtil.getNotNullCount(mContents);
    }

    public boolean isEmpty() {
        return getTileCount() == 0;
    }

    public boolean isFull() {
        return getTileCount() == MAX_TILE_COUNT;
    }

    public Iterator iterator() {
        return mContents.iterator();
    }

    // Methods: commands

    public void add(Tile pTile, int pPosition) {
        Assert.assertNotNull(pTile);
        assertIsValid(pPosition);
        Assert.assertFalse(contains(pPosition));

        mContents.set(pPosition, pTile);

    }

    public void add(Collection pTiles) {
        Assert.assertNotNull(pTiles);
        Assert.assertTrue(
            CollectionUtil.containsOnlyInstancesOf(pTiles, Tile.class));
        final int expectedTileCount = getTileCount() + pTiles.size();
        Assert.assertTrue(expectedTileCount <= MAX_TILE_COUNT);

        for (Iterator tiles = pTiles.iterator(); tiles.hasNext();) {
            for (ListIterator i = mContents.listIterator(); i.hasNext();) {
                if (i.next() == null) {
                    i.set(tiles.next());
                    break;
                }
            }
        }

        Assert.assertEquals(expectedTileCount, getTileCount());
    }

    public boolean canMove(int pSource, int pDestination) {
        return isValid(pSource) && contains(pSource) && isValid(pDestination) &&
            pSource != pDestination;
    }

    public void move(int pSource, int pDestination) {
        Assert.assertTrue(canMove(pSource, pDestination));

        Tile sourceTile = get(pSource);
        if (contains(pDestination)) {
            Tile destinationTile = get(pDestination);
            mContents.set(pSource, destinationTile);
        } else {
            remove(pSource);
        }
        mContents.set(pDestination, sourceTile);

    }

    public void remove(int pPosition) {
        assertIsValid(pPosition);
        Assert.assertTrue(contains(pPosition));
        final int oldTileCount = getTileCount();

        mContents.set(pPosition, null);

        Assert.assertEquals(oldTileCount - 1, getTileCount());
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        return pOther != null && pOther.getClass().equals(getClass()) &&
            mContents.equals(((Rack) pOther).mContents);
    }

    public int hashCode() {
        return mContents.hashCode();
    }

    public String toString() {
        return mContents.toString();
    }

}
