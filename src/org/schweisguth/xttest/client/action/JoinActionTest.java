package org.schweisguth.xttest.client.action;

import java.rmi.RemoteException;
import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;

public class JoinActionTest extends BaseActionTest {
    protected Command createCommand() {
        return new JoinCommand();
    }

    public void testEarly() {
        assertEnabled(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            false, false, false);
    }

    public void testBefore() {
        assertEnabled(new JoiningState(), true);
    }

    public void testBeforeMax() {
        assertEnabled(new JoiningState(THREE_PLAYERS), true);
    }

    public void testAfter1() {
        assertEnabled(new JoiningState(new String[] { "player1" }), false, true);
    }

    public void testAfter2() {
        assertEnabled(new JoiningState(TWO_PLAYERS), false, false, true);
    }

    public void testAfterMax() {
        assertEnabled(
            new JoiningState(
                new String[] { "player1", "player2", "player3", "player4" }),
            false);
    }

    public void testExecute1() throws RemoteException {
        ListenableGame game = new GameImpl(new JoiningState());
        CommandAction action1 = createAction(game, "player1");
        Action observerAction = createAction(game, "observer");
        action1.execute();

        assertFalse(action1.isEnabled());
        assertTrue(observerAction.isEnabled());

    }

    public void testExecute2() throws RemoteException {
        ListenableGame game = new GameImpl(new JoiningState());
        CommandAction action1 = createAction(game, "player1");
        CommandAction action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action1.execute();
        action2.execute();

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertTrue(observerAction.isEnabled());

    }

    public void testExecuteMax() throws RemoteException {
        ListenableGame game = new GameImpl(new JoiningState());
        CommandAction action1 = createAction(game, "player1");
        CommandAction action2 = createAction(game, "player2");
        CommandAction action3 = createAction(game, "player3");
        CommandAction action4 = createAction(game, "player4");
        Action observerAction = createAction(game, "observer");
        action1.execute();
        action2.execute();
        action3.execute();
        action4.execute();

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(action3.isEnabled());
        assertFalse(action4.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

}
