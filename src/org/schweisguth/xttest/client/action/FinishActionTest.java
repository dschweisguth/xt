package org.schweisguth.xttest.client.action;

import java.rmi.RemoteException;
import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.FinishCommand;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.approving.ApprovingState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;

public class FinishActionTest extends BaseActionTest {
    protected Command createCommand() {
        return new FinishCommand();
    }

    public void testEarly() {
        assertEnabled(
            new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE,
                new TransferSet(0, 7, 7)),
            false, false, false);
    }

    public void testBefore() {
        assertEnabled(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO),
            true, false, false);
    }

    public void testAfter() {
        assertEnabled(new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO),
            false, false, false);
    }

    public void testExecute() throws RemoteException {
        ListenableGame game = new GameImpl(
            new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO));
        CommandAction action1 = createAction(game, "player1");
        Action action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action1.execute();

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

}
