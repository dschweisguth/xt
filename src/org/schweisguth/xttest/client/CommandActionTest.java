package org.schweisguth.xttest.client;

import java.rmi.RemoteException;
import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.StartCommand;
import org.schweisguth.xt.common.command.StartNewGameCommand;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.gameimpl.stateimpl.StateImpl;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.testutil.BaseTest;

public class CommandActionTest extends BaseTest {
    public void testCreateDisabled() {
        assertEnabled(new JoiningState(new String[] { "player1" }), false);
    }

    public void testCreateEnabled() {
        assertEnabled(new JoiningState(), true);
    }

    private static void assertEnabled(StateImpl pState, boolean
        pExpectedEnabled) {
        LocalClient client = new LocalClient(new GameImpl(pState), "player1");
        Action action = createAction(client, new JoinCommand());
        client.sendRefreshEvent();
        assertEquals(pExpectedEnabled, action.isEnabled());
    }

    public void testExecuteDisablesSelf() throws RemoteException {
        ListenableGame game = new GameImpl(new JoiningState());
        Action action = createAction(game, "player1", new JoinCommand());
        action.actionPerformed(null);

        assertFalse(action.isEnabled());

    }

    public void testExecuteDisablesOther() throws RemoteException {
        ListenableGame game =
            new GameImpl(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        Action other =
            createAction(game, "player2", new StartNewGameCommand());
        Action action =
            createAction(game, "player1", new StartNewGameCommand());
        action.actionPerformed(null);

        assertFalse(other.isEnabled());

    }

    public void testExecuteLeavesOtherDisabled() throws RemoteException {
        ListenableGame game = new GameImpl(new JoiningState());
        Action other = createAction(game, "player2", new StartCommand());
        Action action = createAction(game, "player1", new JoinCommand());
        action.actionPerformed(null);

        assertFalse(other.isEnabled());

    }

    public void testExecuteEnablesOther() throws RemoteException {
        ListenableGame game =
            new GameImpl(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        Action action = createAction(game, "player2", new JoinCommand());
        createAction(game, "player1",
            new StartNewGameCommand()).actionPerformed(null);

        assertTrue(action.isEnabled());

    }

    public void testExecuteLeavesOtherEnabled() throws RemoteException {
        ListenableGame game = new GameImpl(new JoiningState());
        Action other = createAction(game, "player2", new JoinCommand());
        Action action = createAction(game, "player1", new JoinCommand());
        action.actionPerformed(null);

        assertTrue(other.isEnabled());

    }

    private static CommandAction createAction(Client pClient, Command pCommand)
    {
        return new CommandAction(pClient, pCommand);
    }

    private static CommandAction createAction(ListenableGame pGame,
        String pPlayer, Command pCommand) {
        return createAction(new LocalClient(pGame, pPlayer), pCommand);
    }

}
