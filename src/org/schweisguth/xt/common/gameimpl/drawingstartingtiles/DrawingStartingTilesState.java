package org.schweisguth.xt.common.gameimpl.drawingstartingtiles;

import java.util.Map;
import org.schweisguth.xt.common.command.DrawStartingTilesCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.hascurrentplayer.HasCurrentPlayerStateImpl;
import org.schweisguth.xt.common.gameimpl.state.State;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.SetList;
import org.schweisguth.xt.common.util.contract.Assert;

public class DrawingStartingTilesState extends HasCurrentPlayerStateImpl {
    private static final long serialVersionUID = -2655151345669531364L;

    // Constructors

    public DrawingStartingTilesState(State pSource,
        int pCurrentPlayerIndex) {
        this(pSource.getPlayers(), pSource.getRacks(), pCurrentPlayerIndex);
    }

    public DrawingStartingTilesState(String[] pPlayers) {
        this(pPlayers, 0);
    }

    public DrawingStartingTilesState(String[] pPlayers, int pCurrentPlayerIndex) {
        this(new ArraySetList(pPlayers),
            createEmptyRacks(new ArraySetList(pPlayers)), pCurrentPlayerIndex);
    }

    public DrawingStartingTilesState(String[] pPlayers, String[] pRacks) {
        this(pPlayers, pRacks, 0);
    }

    public DrawingStartingTilesState(String[] pPlayers, String[] pRacks,
        int pCurrentPlayerIndex) {
        this(new ArraySetList(pPlayers), stringsToMap(pPlayers, pRacks),
            pCurrentPlayerIndex);
    }

    private DrawingStartingTilesState(SetList pPlayers, Map pRacks,
        int pCurrentPlayerIndex) {
        super(pPlayers, pRacks, new Board(),
            new ScoreSheet(pPlayers, pCurrentPlayerIndex), pCurrentPlayerIndex);
        assertRacksWereFilledInOrder();
        Assert.assertTrue(getScoreSheet().isIn(ScoreSheet.EMPTY));
        assertCurrentPlayerAndScoreSheetMatch();
    }

    private void assertRacksWereFilledInOrder() {
        Assert.assertTrue(getCurrentRack().isEmpty());
        boolean expectingFullRacks = true;
        for (int offset = 1; offset < getPlayerCount(); offset++) {
            int playerIndex = getCurrentPlayerIndex() - offset;
            if (playerIndex < 0) {
                playerIndex += getPlayerCount();
            }
            String player = (String) getPlayers().get(playerIndex);
            Rack rack = getRack(player);
            boolean rackIsEmpty = rack.isEmpty();
            Assert.assertTrue(rack.isFull() || rackIsEmpty);
            if (expectingFullRacks) {
                if (rackIsEmpty) {
                    expectingFullRacks = false;
                }
            } else {
                Assert.assertTrue(rackIsEmpty);
            }

        }
    }

    private void assertCurrentPlayerAndScoreSheetMatch() {
        Assert.assertTrue(
            getScoreSheet().getCurrentPlayerIndex() == getCurrentPlayerIndex());
    }

    // Methods: queries

    public String getName() {
        return Game.DRAWING_STARTING_TILES;
    }

    // Methods: commands

    public boolean canExecute(String pPlayer, DrawStartingTilesCommand pCommand) {
        return isCurrent(pPlayer);
    }

    public Event execute(String pPlayer, DrawStartingTilesCommand pCommand) {
        getRacks().put(pPlayer, getBoxLid().drawFullRack());
        incrementCurrentPlayerIndex();
        getScoreSheet().setCurrentPlayerIndex(getCurrentPlayerIndex());
        if (! getCurrentRack().isEmpty()) {
            getContext().goToMovingState(this);
        }
        return new DrewStartingTilesEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

}
