package org.schweisguth.xt.common.gameimpl.hascurrentplayer;

import java.util.Iterator;
import java.util.Map;
import org.schweisguth.xt.common.command.RearrangeRackCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.rackscanhavetiles.RacksCanHaveTilesState;
import org.schweisguth.xt.common.gameimpl.state.HasCurrentPlayerState;
import org.schweisguth.xt.common.util.collection.SetList;
import org.schweisguth.xt.common.util.contract.Assert;

public abstract class HasCurrentPlayerStateImpl
    extends RacksCanHaveTilesState implements HasCurrentPlayerState {
    private static final long serialVersionUID = -1127745876364033139L;

    // Fields
    private int mCurrentPlayerIndex;

    // Constructors

    protected HasCurrentPlayerStateImpl(HasCurrentPlayerState pSource) {
        super(pSource);
        setCurrentPlayerIndex(pSource.getCurrentPlayerIndex());
        assertPostConditions();
    }

    protected HasCurrentPlayerStateImpl(SetList pPlayers, Map pRacks,
        Board pBoard, ScoreSheet pScoreSheet, int pCurrentPlayerIndex) {
        super(pPlayers, pRacks, pBoard, pScoreSheet);
        setCurrentPlayerIndex(pCurrentPlayerIndex);
        assertPostConditions();
    }

    private void setCurrentPlayerIndex(int pCurrentPlayerIndex) {
        Assert.assertTrue(0 <= pCurrentPlayerIndex);
        Assert.assertTrue(pCurrentPlayerIndex <= getPlayerCount());

        mCurrentPlayerIndex = pCurrentPlayerIndex;

    }

    private void assertPostConditions() {
        Assert.assertTrue(getBoard().getUnapprovedTileCount() <=
            getCurrentRack().getSpaceCount());
    }

    // Methods: queries

    public String getCurrentPlayer() {
        return (String) getPlayers().get(mCurrentPlayerIndex);
    }

    public int getCurrentPlayerIndex() {
        return mCurrentPlayerIndex;
    }

    // Methods: queries for subclasses

    protected boolean isCurrent(String pPlayer) {
        return getCurrentPlayer().equals(pPlayer);
    }

    protected Rack getCurrentRack() {
        return getRack(getCurrentPlayer());
    }

    // Methods: commands

    public boolean canExecute(String pPlayer, RearrangeRackCommand pCommand) {
        return getRacks().containsKey(pPlayer) &&
            getRack(pPlayer).canMove(pCommand.getSource(),
                pCommand.getDestination());
    }

    public Event execute(String pPlayer, RearrangeRackCommand pCommand) {
        int source = pCommand.getSource();
        int destination = pCommand.getDestination();
        getRack(pPlayer).move(source, destination);
        return new RearrangedRackEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

    // Methods: commands for subclasses

    protected void assertCurrentRackIsNotFullAndOtherRacksAreNotEmpty() {
        Iterator players = getPlayers().iterator();
        while (players.hasNext()) {
            String player = (String) players.next();
            Rack rack = getRack(player);
            if (isCurrent(player)) {
                Assert.assertFalse(rack.isFull());
            } else {
                Assert.assertFalse(rack.isEmpty());
            }
        }
    }

    protected void incrementCurrentPlayerIndex() {
        mCurrentPlayerIndex++;
        mCurrentPlayerIndex %= getPlayerCount();
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        return super.equals(pOther) && mCurrentPlayerIndex ==
            ((HasCurrentPlayerStateImpl) pOther).mCurrentPlayerIndex;
    }

    public int hashCode() {
        return 3 * super.hashCode() + mCurrentPlayerIndex;
    }

}
