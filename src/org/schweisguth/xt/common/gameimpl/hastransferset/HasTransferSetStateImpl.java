package org.schweisguth.xt.common.gameimpl.hastransferset;

import java.util.Iterator;
import org.schweisguth.xt.common.command.RearrangeRackCommand;
import org.schweisguth.xt.common.command.TransferAnythingCommand;
import org.schweisguth.xt.common.command.TransferCommand;
import org.schweisguth.xt.common.command.TransferSetCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.domain.Transfer;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.hascurrentplayer.HasCurrentPlayerStateImpl;
import org.schweisguth.xt.common.gameimpl.state.HasCurrentPlayerState;
import org.schweisguth.xt.common.gameimpl.state.HasTransferSetState;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.contract.Assert;

public abstract class HasTransferSetStateImpl extends HasCurrentPlayerStateImpl
    implements HasTransferSetState {
    private static final long serialVersionUID = -6641197663380854622L;

    // Fields
    private final TransferSet mTransferSet;

    // Constructors

    protected HasTransferSetStateImpl(HasCurrentPlayerState pSource) {
        this(pSource, new TransferSet());
    }

    protected HasTransferSetStateImpl(HasTransferSetState pSource) {
        this(pSource, pSource.getTransferSet());
    }

    private HasTransferSetStateImpl(HasCurrentPlayerState pSource,
        TransferSet pTransferSet) {
        super(pSource);
        Assert.assertNotNull(pTransferSet);
        mTransferSet = pTransferSet;
        assertPostConditions();
    }

    protected HasTransferSetStateImpl(String[] pPlayers, String[] pRacks,
        Board pBoard, ScoreSheet pScoreSheet, int pCurrentPlayerIndex) {
        super(new ArraySetList(pPlayers), stringsToMap(pPlayers, pRacks),
            pBoard, pScoreSheet, pCurrentPlayerIndex);
        mTransferSet = new TransferSet();
        assertPostConditions();
    }

    private void assertPostConditions() {
        Assert.assertFalse(getScoreSheet().isIn(ScoreSheet.GAME_ENDED));
        Assert.assertTrue(
            getScoreSheet().getCurrentPlayerIndex() == getCurrentPlayerIndex());
    }

    // Methods: queries

    public TransferSet getTransferSet() {
        return mTransferSet;
    }

    // Methods: commands for subclasses

    protected boolean canExecute(String pPlayer,
        TransferAnythingCommand pCommand) {
        return isCurrent(pPlayer);
    }

    protected boolean canExecute(String pPlayer, TransferCommand pCommand) {
        Transfer transfer = pCommand.getTransfer();
        return canExecute(pPlayer, new TransferAnythingCommand()) &&
            getRack(pPlayer).contains(transfer.getRackPosition()) &&
            getBoard().canPlace(transfer.getBoardPosition());
    }

    protected Event execute(String pPlayer, TransferCommand pCommand) {
        Transfer transfer = pCommand.getTransfer();
        int rackPosition = transfer.getRackPosition();
        Rack rack = getRack(pPlayer);
        getBoard().place(rack.get(rackPosition), transfer.getBoardPosition());
        rack.remove(rackPosition);
        getTransferSet().add(transfer);
        return new TransferredEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

    protected boolean canExecute(String pPlayer, TransferSetCommand pCommand) {
        for (Iterator transfers = pCommand.getTransferSet().iterator();
            transfers.hasNext();) {
            if (! canExecute(pPlayer,
                new TransferCommand((Transfer) transfers.next()))) {
                return false;
            }
        }
        return true;
    }

    protected void execute(String pPlayer, TransferSetCommand pCommand) {
        for (Iterator transfers = pCommand.getTransferSet().iterator();
            transfers.hasNext();) {
            execute(pPlayer,
                new TransferCommand((Transfer) transfers.next()));
        }
    }

    protected void takeBack(Transfer pTransfer) {
        Position boardPosition = pTransfer.getBoardPosition();
        getCurrentRack().add(getBoard().getTile(boardPosition),
            pTransfer.getRackPosition());
        getBoard().remove(boardPosition);
        mTransferSet.takeBack(pTransfer);
    }

    protected void retractMove() {
        TransferSet copy = new TransferSet(mTransferSet);
        for (Iterator transfers = copy.iterator(); transfers.hasNext();) {
            takeBack((Transfer) transfers.next());
        }
        incrementScore(0);
    }

    protected void approve() {
        incrementScore(getScore());
        getBoard().approve();
        getTransferSet().clear();
        if (getBoxLid().isEmpty()) {
            if (getCurrentRack().isEmpty()) {
                doEndGame();
            } else {
                startNextTurn();
            }
        } else {
            getContext().goToDrawingNewTilesState(this);
        }
    }

    private void incrementScore(int pScore) {
        getScoreSheet().incrementScore(pScore);
    }

    private int getScore() {
        int score = getBoard().getScore();

        // Bonus for playing a full rack
        if (getTransferSet().size() == Rack.MAX_TILE_COUNT) {
            score += 50;
        }

        return score;
    }

    protected void doEndGame() {
        addFinalScores();
        getContext().goToEndedState(this);
    }

    private void addFinalScores() {
        ScoreSheet scores = getScoreSheet();
        scores.endGame();
        //for (Iterator racks = getRacks().values().iterator(); racks.hasNext();)
        for (Iterator players = getPlayers().iterator(); players.hasNext();) {
            Rack rack = getRack((String) players.next());
            // If the rack is empty, it's that of the player who went out;
            // increase that player's score by the sum of the scores of the other
            // players' remaining tiles. If it's not empty, deduct it.
            int score = rack.isEmpty() ? getRackScoreSum() : - rack.getScore();
            scores.incrementScore(score);
        }
    }

    private int getRackScoreSum() {
        int sumOfRackScores = 0;
        Iterator racks = getRacks().values().iterator();
        while (racks.hasNext()) {
            sumOfRackScores += ((Rack) racks.next()).getScore();
        }
        return sumOfRackScores;
    }

    protected void startNextTurn() {
        incrementCurrentPlayerIndex();
        getContext().goToMovingState(this);
    }

    // Methods: overrides HasCurrentPlayerState

    public Event execute(String pPlayer, RearrangeRackCommand pCommand) {
        int destination = pCommand.getDestination();
        if (isCurrent(pPlayer) &&
            mTransferSet.contains(destination)) {
            mTransferSet.move(destination, pCommand.getSource());
        }
        return super.execute(pPlayer, pCommand);
    }

    // Methods: overrides Object

    public boolean equals(Object pOther) {
        return super.equals(pOther) &&
            ((HasTransferSetStateImpl) pOther).mTransferSet.equals(mTransferSet);
    }

    public int hashCode() {
        return 3 * super.hashCode() + mTransferSet.hashCode();
    }

}
