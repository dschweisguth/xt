package org.schweisguth.xt.common.gameimpl.challenging;

import org.schweisguth.xt.common.command.OverruleChallengeCommand;
import org.schweisguth.xt.common.command.SustainChallengeCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.hastransferset.HasTransferSetStateImpl;
import org.schweisguth.xt.common.gameimpl.state.HasTransferSetState;
import org.schweisguth.xt.common.util.contract.Assert;

public class ChallengingState extends HasTransferSetStateImpl {
    private static final long serialVersionUID = 2340219184746816318L;

    // Constructors

    public ChallengingState(HasTransferSetState pSource) {
        super(pSource);
        assertPostConditions();
    }

    public ChallengingState(String[] pPlayers, String[] pRacks,
        TransferSet pTransferSet) {
        this(pPlayers, pRacks, new Board(), new ScoreSheet(pPlayers), 0,
            pTransferSet);
    }

    public ChallengingState(String[] pPlayers, String[] pRacks, Board pBoard,
        ScoreSheet pScores, int pCurrentPlayerIndex, TransferSet pTransferSet) {
        super(pPlayers, pRacks, pBoard, pScores, pCurrentPlayerIndex);
        transfer(getCurrentPlayer(), pTransferSet);
        assertPostConditions();
    }

    private void assertPostConditions() {
        assertCurrentRackIsNotFullAndOtherRacksAreNotEmpty();
        Assert.assertTrue(getBoard().canFinish());
    }

    // Methods: queries

    public String getName() {
        return Game.CHALLENGING;
    }

    // Methods: commands

    public boolean canExecute(String pPlayer, OverruleChallengeCommand pCommand) {
        return isPlaying(pPlayer);
    }

    public Event execute(String pPlayer, OverruleChallengeCommand pCommand) {
        String currentPlayer = getCurrentPlayer();
        approve();
        return new OverruledChallengeEvent(getContext().getGame(),
            new Request(pPlayer, pCommand), currentPlayer);
    }

    public boolean canExecute(String pPlayer, SustainChallengeCommand pCommand) {
        return isPlaying(pPlayer);
    }

    public Event execute(String pPlayer, SustainChallengeCommand pCommand) {
        retractMove();
        String currentPlayer = getCurrentPlayer();
        startNextTurn();
        return new SustainedChallengeEvent(getContext().getGame(),
            new Request(pPlayer, pCommand), currentPlayer);
    }

}
