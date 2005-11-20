package org.schweisguth.xttest.client.action;

import java.rmi.RemoteException;
import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.common.command.ChallengeCommand;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.approving.ApprovingState;
import org.schweisguth.xt.common.gameimpl.challenging.ChallengingState;
import org.schweisguth.xt.common.gameimpl.drawingnewtiles.DrawingNewTilesState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.util.collection.CollectionUtil;

public class ChallengeActionTest extends BaseActionTest {
    protected Command createCommand() {
        return new ChallengeCommand();
    }

    public void testEarly() {
        assertEnabled(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO),
            false, false, false);
    }

    public void testBefore() {
        assertEnabled(new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO),
            true, true, false);
    }

    public void testAfter() {
        assertEnabled(
            new ChallengingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO),
            false, false, false);
    }

    public void testAfterApproved() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        assertEnabled(
            new DrawingNewTilesState(TWO_PLAYERS, AAAAAAA_EEEEEEE, scores,
                MOVE_TWO),
            false, false, false);
    }

    public void testAfterApproved31() {
        ApprovingState state = new ApprovingState(THREE_PLAYERS,
            AAAAAAA_EEEEEEE_IIIIIII, MOVE_TWO);
        state.setApprovals(CollectionUtil.asStickySet("player2"));
        assertEnabled(state, true, false, true, false);
    }

    public void testExecute() throws RemoteException {
        ListenableGame game = new GameImpl(
            new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO));
        CommandAction action1 = createAction(game, "player1");
        Action action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action1.execute();

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

}
