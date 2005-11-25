package org.schweisguth.xt.common.gameimpl.approving;

import java.util.Set;
import org.schweisguth.xt.common.command.ApproveCommand;
import org.schweisguth.xt.common.command.ChallengeCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.hastransferset.HasTransferSetStateImpl;
import org.schweisguth.xt.common.gameimpl.state.HasTransferSetState;
import org.schweisguth.xt.common.util.collection.HashStickySet;
import org.schweisguth.xt.common.util.contract.Assert;

public class ApprovingState extends HasTransferSetStateImpl {
    private static final long serialVersionUID = -8642688482220097135L;

    // Fields
    private final Set mApprovals = new HashStickySet();

    // Constructors

    public ApprovingState(HasTransferSetState pSource) {
        super(pSource);
        assertPostConditions();
    }

    public ApprovingState(String[] pPlayers, String[] pRacks,
        TransferSet pTransferSet) {
        this(pPlayers, pRacks, new Board(), new ScoreSheet(pPlayers), 0,
            pTransferSet);
    }

    public ApprovingState(String[] pPlayers, String[] pRacks, Board pBoard,
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
        return Game.APPROVING;
    }

    // Methods: commands

    public void setApprovals(Set pApprovals) {
        Assert.assertNotNull(pApprovals);
        Assert.assertTrue(getPlayers().containsAll(pApprovals));

        mApprovals.clear();
        mApprovals.addAll(pApprovals);

    }

    public boolean canExecute(String pPlayer, ApproveCommand pCommand) {
        return isPlaying(pPlayer) && ! isCurrent(pPlayer) &&
            ! mApprovals.contains(pPlayer);
    }

    public Event execute(String pPlayer, ApproveCommand pCommand) {
        mApprovals.add(pPlayer);
        String affectedPlayer = getCurrentPlayer();
        if (mApprovals.size() == getPlayerCount() - 1) {
            approve();
        }
        return new ApprovedEvent(getContext().getGame(),
            new Request(pPlayer, pCommand), affectedPlayer);
    }

    public boolean canExecute(String pPlayer, ChallengeCommand pCommand) {
        return isPlaying(pPlayer) && ! mApprovals.contains(pPlayer);
    }

    public Event execute(String pPlayer, ChallengeCommand pCommand) {
        getContext().goToChallengingState(this);
        return new ChallengedEvent(getContext().getGame(),
            new Request(pPlayer, pCommand), getCurrentPlayer());
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        return super.equals(pOther) &&
            ((ApprovingState) pOther).mApprovals.equals(mApprovals);
    }

    public int hashCode() {
        return 3 * super.hashCode() + mApprovals.hashCode();
    }

}
