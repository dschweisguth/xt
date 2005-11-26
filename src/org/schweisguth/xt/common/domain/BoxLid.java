package org.schweisguth.xt.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.contract.Assert;

public class BoxLid implements Serializable {
    private static final long serialVersionUID = 9110037220351484539L;

    // Constants
    private static final SortedMap TILE_COUNTS = new TreeMap();
    private static final List TILES_PROTOTYPE = new ArrayList();

    static {
        char[][] tileCounts = {
            { 'A', 9 },
            { 'B', 2 },
            { 'C', 2 },
            { 'D', 4 },
            { 'E', 12 },
            { 'F', 2 },
            { 'G', 3 },
            { 'H', 2 },
            { 'I', 9 },
            { 'J', 1 },
            { 'K', 1 },
            { 'L', 4 },
            { 'M', 2 },
            { 'N', 6 },
            { 'O', 8 },
            { 'P', 2 },
            { 'Q', 1 },
            { 'R', 6 },
            { 'S', 4 },
            { 'T', 6 },
            { 'U', 4 },
            { 'V', 2 },
            { 'W', 2 },
            { 'X', 1 },
            { 'Y', 2 },
            { 'Z', 1 },
            { ' ', 2 }
        };
        for (int i = 0; i < tileCounts.length; i++) {
            char[] tile = tileCounts[i];
            TILE_COUNTS.put(Tile.get(tile[0]), new Integer(tile[1]));
        }
        Iterator entries = TILE_COUNTS.entrySet().iterator();
        while (entries.hasNext()) {
            Entry entry = (Entry) entries.next();
            Object tile = entry.getKey();
            int count = ((Integer) entry.getValue()).intValue();
            for (int i = 0; i < count; i++) {
                TILES_PROTOTYPE.add(tile);
            }
        }
    }

    private static final int TILES_INITIAL_SIZE = TILES_PROTOTYPE.size();

    // Methods: static

    public static void assertTileCountIsValid(int pTileCount) {
        Assert.assertTrue(0 <= pTileCount);
        Assert.assertTrue(pTileCount <= TILES_INITIAL_SIZE);
    }

    public static SortedMap getTileCounts() {
        return TILE_COUNTS;
    }

    public static int getMaxTileCount() {
        return TILES_INITIAL_SIZE;
    }

    // Fields
    private final List mTiles = new ArrayList();
    private final Random mRandom = new Random();

    // Constructors

    public BoxLid() {
        initialize();
        assertTileCountIs(getMaxTileCount());
    }

    public BoxLid(String pTiles) {
        Assert.assertNotNull(pTiles);
        assertTileCountIsValid(pTiles.length());
        for (int i = 0; i < pTiles.length(); i++) {
            Assert.assertTrue(Tile.isLetter(pTiles.charAt(i)));
        }

        for (int i = 0; i < pTiles.length(); i++) {
            mTiles.add(Tile.get(pTiles.charAt(i)));
        }

        assertTileCountIs(pTiles.length());
    }

    // Methods: queries

    public int getTileCount() {
        return mTiles.size();
    }

    public boolean isEmpty() {
        return mTiles.isEmpty();
    }

    // Methods: commands

    public Tile draw() {
        final int oldTileCount = getTileCount();
        Assert.assertTrue(oldTileCount > 0);

        int boxLidPosition = mRandom.nextInt(mTiles.size());
        Tile tile = (Tile) mTiles.get(boxLidPosition);
        mTiles.remove(boxLidPosition);

        assertTileCountIs(oldTileCount - 1);
        return tile;
    }

    private List draw(int pNumTiles) {
        Assert.assertTrue(0 <= pNumTiles);
        final int oldTileCount = getTileCount();
        Assert.assertTrue(pNumTiles <= oldTileCount);

        List tiles = new ArrayList(pNumTiles);
        for (int i = 0; i < pNumTiles; i++) {
            tiles.add(draw());
        }

        assertTileCountIs(oldTileCount - pNumTiles);
        return tiles;
    }

    public List drawUpTo(int pMaxTilesDrawnCount) {
        Assert.assertTrue(0 <= pMaxTilesDrawnCount);
        final int oldTileCount = getTileCount();
        final int tilesDrawnCount = Math.min(pMaxTilesDrawnCount, oldTileCount);

        List tiles = draw(tilesDrawnCount);

        assertTileCountIs(oldTileCount - tilesDrawnCount);
        return tiles;
    }

    public Rack drawFullRack() {
        return new Rack(draw(Rack.MAX_TILE_COUNT));
    }

    public void draw(Tile pTile) {
        final int oldTileCount = getTileCount();
        Assert.assertTrue(mTiles.contains(pTile));

        mTiles.remove(pTile);

        assertTileCountIs(oldTileCount - 1);
    }

    public final void initialize() {
        mTiles.clear();
        mTiles.addAll(TILES_PROTOTYPE);
    }

    public void putBack(Collection pTiles) {
        Assert.assertNotNull(pTiles);
        Assert.assertTrue(
            CollectionUtil.containsOnlyInstancesOf(pTiles, Tile.class));
        final int oldTileCount = getTileCount();

        for (Iterator tiles = pTiles.iterator(); tiles.hasNext();) {
            mTiles.add(tiles.next());
        }

        assertTileCountIs(oldTileCount + pTiles.size());
    }

    // Methods: helper

    private void assertTileCountIs(int pExpectedTileCount) {
        int tileCount = getTileCount();
        assertTileCountIsValid(tileCount);
        Assert.assertEquals(pExpectedTileCount, tileCount);
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        return pOther != null && pOther.getClass().equals(getClass()) &&
            CollectionUtil.equalsIgnoringOrder(mTiles, ((BoxLid) pOther).mTiles);
    }

    public int hashCode() {
        return mTiles.hashCode();
    }

    public String toString() {
        return mTiles.toString();
    }

}
