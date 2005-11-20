package org.schweisguth.xttest.client.action;

import java.rmi.RemoteException;
import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.SustainChallengeCommand;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.approving.ApprovingState;
import org.schweisguth.xt.common.gameimpl.challenging.ChallengingState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;

public class SustainChallengeActionTest extends BaseActionTest {
    protected Command createCommand() {
        return new SustainChallengeCommand();
    }

    public void testEarly() {
        assertEnabled(new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO),
            false, false, false);
    }

    public void testBefore() {
        assertEnabled(
            new ChallengingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO),
            true, true, false);
    }

    /**
     * Game state after sustaining or overruling is the same, so this test tests
     * both.
     */
    public void testAfterOverrule() {
        assertEnabled(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            false, false, false);
    }

    public void testExecute() throws RemoteException {
        ListenableGame game = new GameImpl(
            new ChallengingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO));
        Action action1 = createAction(game, "player1");
        CommandAction action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action2.execute();

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

}
