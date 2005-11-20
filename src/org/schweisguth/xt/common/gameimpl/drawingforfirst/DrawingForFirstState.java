package org.schweisguth.xt.common.gameimpl.drawingforfirst;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.schweisguth.xt.common.command.DrawForFirstCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.state.State;
import org.schweisguth.xt.common.gameimpl.stateimpl.StateImpl;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.collection.SetList;
import org.schweisguth.xt.common.util.contract.Assert;

public class DrawingForFirstState extends StateImpl {
    private static final long serialVersionUID = -3557251902905159246L;

    // Fields
    private final SortedMap mTilesDrawnForFirst = new TreeMap();

    // Constructors

    public DrawingForFirstState(State pSource) {
        this(pSource.getPlayers());
    }

    public DrawingForFirstState(String[] pPlayers) {
        this(new ArraySetList(pPlayers));
    }

    private DrawingForFirstState(SetList pPlayers) {
        super(pPlayers, createEmptyRacks(pPlayers), new BoxLid(), new Board(),
            new ScoreSheet(pPlayers));
        assertPlayerCountAfterStarting();
        initTilesDrawnForFirst();
        Assert.assertTrue(getScoreSheet().isIn(ScoreSheet.EMPTY));
    }

    private void initTilesDrawnForFirst() {
        Iterator players = getPlayers().iterator();
        while (players.hasNext()) {
            mTilesDrawnForFirst.put(players.next(), null);
        }
    }

    // Methods: queries

    public String getName() {
        return Game.DRAWING_FOR_FIRST;
    }

    public SortedMap getTilesDrawnForFirst() {
        return mTilesDrawnForFirst;
    }

    public boolean noPlayersHaveDrawnForFirstYetThisRound() {
        return CollectionUtil.containsOnlyNull(mTilesDrawnForFirst.values());
    }

    // Methods: commands

    public void setBoxLid(BoxLid pBoxLid) {
        super.setBoxLid(pBoxLid);
    }

    public void setTilesDrawnForFirst(Map pTilesDrawnForFirst) {
        Assert.assertNotNull(pTilesDrawnForFirst);
        Assert.assertTrue(
            getPlayers().containsAll(pTilesDrawnForFirst.keySet()));
        Assert.assertTrue(CollectionUtil.containsOnlyNullOrInstancesOf(
            pTilesDrawnForFirst.values(), Tile.class));

        mTilesDrawnForFirst.clear();
        mTilesDrawnForFirst.putAll(pTilesDrawnForFirst);

    }

    public boolean canExecute(String pPlayer, DrawForFirstCommand pCommand) {
        return mTilesDrawnForFirst.containsKey(pPlayer) &&
            mTilesDrawnForFirst.get(pPlayer) == null;
    }

    public Event execute(String pPlayer, DrawForFirstCommand pCommand) {
        Tile tile = getBoxLid().draw();
        mTilesDrawnForFirst.put(pPlayer, tile);
        if (! mTilesDrawnForFirst.values().contains(null)) {
            // Everyone has drawn
            removeLosers(mTilesDrawnForFirst);
            if (mTilesDrawnForFirst.size() == 1) {
                // Go to the next state
                getBoxLid().initialize();
                String currentPlayer =
                    (String) mTilesDrawnForFirst.keySet().iterator().next();
                int currentPlayerIndex = getPlayers().indexOf(currentPlayer);
                getContext().goToDrawingStartingTilesState(this,
                    currentPlayerIndex);
            } else {
                // Set up for the next round
                getBoxLid().initialize();
                setValuesToNull(mTilesDrawnForFirst);
            }
        }
        return new DrewForFirstEvent(getContext().getGame(),
            new Request(pPlayer, pCommand), tile);
    }

    private static void removeLosers(Map pTilesDrawnForFirst) {
        Assert.assertFalse(pTilesDrawnForFirst.values().contains(null));

        Tile earliestTileDrawnForFirst =
            (Tile) Collections.min(pTilesDrawnForFirst.values());
        Iterator entries = pTilesDrawnForFirst.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            if (! entry.getValue().equals(earliestTileDrawnForFirst)) {
                entries.remove();
            }
        }

    }

    private static void setValuesToNull(Map pMap) {
        Iterator entries = pMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            entry.setValue(null);
        }
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        return super.equals(pOther) &&
            ((DrawingForFirstState) pOther).mTilesDrawnForFirst.equals(
                mTilesDrawnForFirst);
    }

    public int hashCode() {
        return 3 * super.hashCode() + mTilesDrawnForFirst.hashCode();
    }

}
