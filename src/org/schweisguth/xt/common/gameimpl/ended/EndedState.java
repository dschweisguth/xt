package org.schweisguth.xt.common.gameimpl.ended;

import java.util.Iterator;
import org.schweisguth.xt.common.command.StartNewGameCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.rackscanhavetiles.RacksCanHaveTilesState;
import org.schweisguth.xt.common.gameimpl.state.State;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.contract.Assert;

public class EndedState extends RacksCanHaveTilesState {
    private static final long serialVersionUID = 4595362072121133893L;

    // Constructors

    public EndedState(State pSource) {
        super(pSource);
        assertPostConditions();
    }

    public EndedState(String[] pPlayers, String[] pRacks) {
        this(pPlayers, pRacks, new Board(), getScoreSheet(pPlayers, pRacks));
    }

    private static ScoreSheet getScoreSheet(String[] pPlayers, String[] pRacks) {
        ScoreSheet scores = new ScoreSheet(pPlayers);
        for (int i = 0; i < pPlayers.length; i++) {
            scores.incrementScore(0);
        }
        scores.endGame();
        for (int i = 0; i < pPlayers.length; i++) {
            scores.incrementScore(- new Rack(pRacks[i]).getScore());
        }
        return scores;
    }

    public EndedState(String[] pPlayers, String[] pRacks,
        ScoreSheet pScoreSheet, TransferSet pTransferSet) {
        this(pPlayers, getRacks(pRacks, 0, pTransferSet),
            getBoard(pRacks[0], pTransferSet), pScoreSheet);
    }

    public EndedState(String[] pPlayers, String[] pRacks, Board pBoard,
        ScoreSheet pScoreSheet) {
        super(new ArraySetList(pPlayers), stringsToMap(pPlayers, pRacks),
            pBoard, pScoreSheet);
        assertPostConditions();
    }

    private void assertPostConditions() {
        assertAtMostOneRackIsEmpty();
        Assert.assertFalse(getBoard().hasUnapprovedTiles());
        Assert.assertTrue(getScoreSheet().isIn(ScoreSheet.GAME_ENDED));
    }

    private void assertAtMostOneRackIsEmpty() {
        boolean foundEmptyRack = false;
        for (Iterator racks = getRacks().values().iterator(); racks.hasNext();)
        {
            boolean isEmpty = ((Rack) racks.next()).isEmpty();
            if (foundEmptyRack) {
                Assert.assertFalse(isEmpty);
            } else {
                foundEmptyRack = isEmpty;
            }
        }
    }

    // Methods: queries

    public String getName() {
        return Game.ENDED;
    }

    // Methods: commands

    public boolean canExecute(String pPlayer, StartNewGameCommand pCommand) {
        return true;
    }

    public Event execute(String pPlayer, StartNewGameCommand pCommand) {
        getContext().goToJoiningState();
        return new StartedNewGameEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

}
