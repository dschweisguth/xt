package org.schweisguth.xttest.client.action;

import java.rmi.RemoteException;
import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.StartNewGameCommand;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;

public class JoinActionTest extends BaseActionTest {
    protected Command createCommand() {
        return new JoinCommand();
    }

    public void testCreateDisabled() {
        assertEnabled(new JoiningState(new String[] { "player1" }), true);
    }

    public void testCreateEnabled() {
        assertEnabled(new JoiningState(), true);
    }

    public void testExecuteDisablesSelf() throws RemoteException {
        ListenableGame game = new GameImpl(new JoiningState());
        CommandAction action = createAction(game, "player1");
        action.execute();

        assertFalse(action.isEnabled());

    }

    public void testExecuteDisablesOther() throws RemoteException {
        ListenableGame game = new GameImpl(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        Action action = new CommandAction(new LocalClient(game, "player2"), new StartNewGameCommand());
        new CommandAction(new LocalClient(game, "player1"), new StartNewGameCommand()).execute();

        assertFalse(action.isEnabled());

    }

    public void testExecuteNoEffectOnOther() throws RemoteException {
        ListenableGame game = new GameImpl(new JoiningState());
        Action action = createAction(game, "player2");
        createAction(game, "player1").execute();

        assertTrue(action.isEnabled());

    }

    public void testExecuteEnablesOther() throws RemoteException {
        ListenableGame game = new GameImpl(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        Action action = createAction(game, "player2");
        new CommandAction(new LocalClient(game, "player1"), new StartNewGameCommand()).execute();

        assertTrue(action.isEnabled());

    }

}
