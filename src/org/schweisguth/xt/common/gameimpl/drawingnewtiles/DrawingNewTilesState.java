package org.schweisguth.xt.common.gameimpl.drawingnewtiles;

import java.util.List;
import org.schweisguth.xt.common.command.DrawNewTilesCommand;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.hascurrentplayer.HasCurrentPlayerStateImpl;
import org.schweisguth.xt.common.gameimpl.state.HasCurrentPlayerState;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.contract.Assert;

public class DrawingNewTilesState extends HasCurrentPlayerStateImpl {
    private static final long serialVersionUID = 1185862060176305939L;

    // Constructors

    public DrawingNewTilesState(HasCurrentPlayerState pSource) {
        super(pSource);
        assertPostConditions();
    }

    public DrawingNewTilesState(String[] pPlayers, String[] pRacks,
        ScoreSheet pScoreSheet, TransferSet pTransferSet) {
        super(new ArraySetList(pPlayers),
            stringsToMap(pPlayers, getRacks(pRacks, 0, pTransferSet)),
            getBoard(pRacks[0], pTransferSet), pScoreSheet, 0);
        assertPostConditions();
    }

    private void assertPostConditions() {
        assertCurrentRackIsNotFullAndOtherRacksAreNotEmpty();
        Assert.assertTrue(getBoard().hasApprovedTiles());
        Assert.assertFalse(getBoard().hasUnapprovedTiles());
        Assert.assertTrue(getScoreSheet().isIn(ScoreSheet.PLAYING));
        Assert.assertTrue(getScoreSheet().getCurrentPlayerIndex() ==
            (getCurrentPlayerIndex() + 1) % getPlayerCount());
        Assert.assertTrue(getScoreSheet().getInt(
            getScoreSheet().getRowCount() - 1, getCurrentPlayer()) > 0);
    }

    // Methods: queries

    public String getName() {
        return Game.DRAWING_NEW_TILES;
    }

    // Methods: commands

    public boolean canExecute(String pPlayer, DrawNewTilesCommand pCommand) {
        return isCurrent(pPlayer);
    }

    public Event execute(String pPlayer, DrawNewTilesCommand pCommand) {
        if (! getBoxLid().isEmpty()) {
            drawNewTiles();
        }
        incrementCurrentPlayerIndex();
        getContext().goToMovingState(this);
        return new DrewNewTilesEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

    private void drawNewTiles() {
        Rack currentRack = getCurrentRack();
        List newTiles = getBoxLid().drawUpTo(currentRack.getSpaceCount());
        currentRack.add(newTiles);
        Assert.assertTrue(getBoxLid().isEmpty() || currentRack.isFull());
    }

}
