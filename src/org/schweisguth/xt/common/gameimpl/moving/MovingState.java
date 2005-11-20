package org.schweisguth.xt.common.gameimpl.moving;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.schweisguth.xt.common.command.EndGameCommand;
import org.schweisguth.xt.common.command.ExchangeCommand;
import org.schweisguth.xt.common.command.FinishCommand;
import org.schweisguth.xt.common.command.PassCommand;
import org.schweisguth.xt.common.command.RearrangeBoardCommand;
import org.schweisguth.xt.common.command.TakeBackCommand;
import org.schweisguth.xt.common.command.TransferAnythingCommand;
import org.schweisguth.xt.common.command.TransferCommand;
import org.schweisguth.xt.common.command.TransferSetCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.hastransferset.HasTransferSetStateImpl;
import org.schweisguth.xt.common.gameimpl.state.HasCurrentPlayerState;
import org.schweisguth.xt.common.util.collection.HashStickySet;
import org.schweisguth.xt.common.util.contract.Assert;

public class MovingState extends HasTransferSetStateImpl {
    private static final long serialVersionUID = 4212092676210968287L;

    // Fields
    private final Set mPasses = new HashSet();

    // Constructors

    public MovingState(HasCurrentPlayerState pSource) {
        super(pSource);
        assertPostConditions();
    }

    public MovingState(String[] pPlayers, String[] pRacks) {
        this(pPlayers, pRacks, 0);
    }

    public MovingState(String[] pPlayers, String[] pRacks,
        int pCurrentPlayerIndex) {
        this(pPlayers, pRacks, new Board(),
            new ScoreSheet(pPlayers, pCurrentPlayerIndex), pCurrentPlayerIndex);
    }

    public MovingState(String[] pPlayers, String[] pRacks, Board pBoard,
        ScoreSheet pScoreSheet, int pCurrentPlayerIndex) {
        super(pPlayers, pRacks, pBoard, pScoreSheet, pCurrentPlayerIndex);
        assertPostConditions();
    }

    private void assertPostConditions() {
        assertNoRacksAreEmpty();
        Assert.assertFalse(getBoard().hasUnapprovedTiles());
    }

    public MovingState(String[] pPlayers, String[] pRacks,
        TransferSet pTransferSet) {
        this(pPlayers, pRacks);
        execute(getCurrentPlayer(), new TransferSetCommand(pTransferSet));
        assertNoRacksAreEmpty();
    }

    private void assertNoRacksAreEmpty() {
        Iterator racks = getRacks().values().iterator();
        while (racks.hasNext()) {
            Assert.assertFalse(((Rack) racks.next()).isEmpty());
        }
    }

    // Methods: queries

    public String getName() {
        return Game.MOVING;
    }

    // Methods: commands

    public void setPasses(Set pPasses) {
        Assert.assertNotNull(pPasses);
        Assert.assertTrue(getPlayers().containsAll(pPasses));

        mPasses.clear();
        mPasses.addAll(pPasses);

    }

    public boolean canExecute(String pPlayer, TransferAnythingCommand pCommand) {
        return super.canExecute(pPlayer, pCommand);
    }

    public Event execute(String pPlayer, TransferAnythingCommand pCommand) {
        throw new UnsupportedOperationException();
    }

    public boolean canExecute(String pPlayer, TransferCommand pCommand) {
        return super.canExecute(pPlayer, pCommand);
    }

    public Event execute(String pPlayer, TransferCommand pCommand) {
        return super.execute(pPlayer, pCommand);
    }

    public boolean canExecute(String pPlayer, RearrangeBoardCommand pCommand) {
        return isCurrent(pPlayer) &&
            getBoard().canMove(pCommand.getSource(), pCommand.getDestination());
    }

    public Event execute(String pPlayer, RearrangeBoardCommand pCommand) {
        Position pSource = pCommand.getSource();
        Position pDestination = pCommand.getDestination();
        getBoard().move(pSource, pDestination);
        getTransferSet().move(pSource, pDestination);
        return new RearrangedBoardEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

    public boolean canExecute(String pPlayer, TakeBackCommand pCommand) {
        return isCurrent(pPlayer) && getBoard().hasUnapprovedTile(
            pCommand.getTransfer().getBoardPosition());
    }

    public Event execute(String pPlayer, TakeBackCommand pCommand) {
        takeBack(pCommand.getTransfer());
        return new TookBackEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

    public boolean canExecute(String pPlayer, FinishCommand pCommand) {
        return isCurrent(pPlayer) && getBoard().canFinish();
    }

    public Event execute(String pPlayer, FinishCommand pCommand) {
        getContext().goToApprovingState(this);
        return new FinishedEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

    public boolean canExecute(String pPlayer, ExchangeCommand pCommand) {
        int[] rackPositions = pCommand.getRackPositions();
        return
            isCurrent(pPlayer) &&
                rackPositions.length > 0 &&
                getCurrentRack().contains(rackPositions) &&
                getBoxLid().getTileCount() >= rackPositions.length;
    }

    public Event execute(String pPlayer, ExchangeCommand pCommand) {
        mPasses.clear();
        retractMove();
        exchange(pCommand.getRackPositions());
        startNextTurn();
        return new ExchangedEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

    private void exchange(int[] pPositions) {
        BoxLid boxLid = getBoxLid();
        final int oldBoxLidTileCount = boxLid.getTileCount();
        Rack rack = getCurrentRack();
        List oldTiles = new ArrayList();
        for (int i = 0; i < pPositions.length; i++) {
            oldTiles.add(rack.get(pPositions[i]));
            rack.remove(pPositions[i]);
            rack.add(boxLid.draw(), pPositions[i]);
        }
        boxLid.putBack(oldTiles);
        Assert.assertEquals(oldBoxLidTileCount, boxLid.getTileCount());
    }

    public boolean canExecute(String pPlayer, PassCommand pCommand) {
        return isCurrent(pPlayer);
    }

    public Event execute(String pPlayer, PassCommand pCommand) {
        mPasses.add(pPlayer);
        retractMove();
        startNextTurn();
        return new PassedEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

    public boolean canExecute(String pPlayer, EndGameCommand pCommand) {
        Set passers = new HashSet(mPasses);
        passers.add(pPlayer);
        return isCurrent(pPlayer) &&
            passers.equals(new HashStickySet(getPlayers()));
    }

    public Event execute(String pPlayer, EndGameCommand pCommand) {
        retractMove();
        doEndGame();
        return new EndedGameEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

    // Methods: overrides HasTransferSetState

    protected void startNextTurn() {
        incrementCurrentPlayerIndex();
    }

    // Methods: overrides Object

    public boolean equals(Object pOther) {
        return super.equals(pOther) &&
            ((MovingState) pOther).mPasses.equals(mPasses);
    }

    public int hashCode() {
        return 3 * super.hashCode() + mPasses.hashCode();
    }

}
