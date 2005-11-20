package org.schweisguth.xttest.client.action;

import java.rmi.RemoteException;
import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.StartCommand;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingforfirst.DrawingForFirstState;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;

public class StartActionTest extends BaseActionTest {
    protected Command createCommand() {
        return new StartCommand();
    }

    public void testEarly() {
        assertEnabled(new JoiningState(new String[] { "player1" }),
            false, false);
    }

    public void testBefore() {
        assertEnabled(new JoiningState(TWO_PLAYERS), true, true, false);
    }

    public void testAfter() {
        assertEnabled(new DrawingForFirstState(TWO_PLAYERS),
            false, false, false);
    }

    public void testExecute() throws RemoteException {
        ListenableGame game = new GameImpl(new JoiningState(TWO_PLAYERS));
        CommandAction action1 = createAction(game, "player1");
        Action action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action1.execute();

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

}
