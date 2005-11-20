package org.schweisguth.xttest.client.action;

import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.stateimpl.StateImpl;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.testutil.BaseTest;

public abstract class BaseActionTest extends BaseTest {
    // Methods: for subclasses

    void assertEnabled(StateImpl pState, boolean pExpectedEnabled) {
        assertEnabled(pState, "observer", pExpectedEnabled);
    }

    void assertEnabled(StateImpl pState, boolean pPlayer1Enabled,
        boolean pObserverEnabled) {
        assertEnabled(pState, pObserverEnabled);
        assertEnabled(pState, "player1", pPlayer1Enabled);
    }

    protected void assertEnabled(StateImpl pState, boolean pPlayer1Enabled,
        boolean pPlayer2Enabled, boolean pObserverEnabled) {
        assertEnabled(pState, pPlayer1Enabled, pObserverEnabled);
        assertEnabled(pState, "player2", pPlayer2Enabled);
    }

    void assertEnabled(StateImpl pState, boolean pPlayer1Enabled,
        boolean pPlayer2Enabled, boolean pPlayer3Enabled,
        boolean pObserverEnabled) {
        assertEnabled(pState, pPlayer1Enabled, pPlayer2Enabled,
            pObserverEnabled);
        assertEnabled(pState, "player3", pPlayer3Enabled);
    }

    protected CommandAction createAction(ListenableGame pGame, String pPlayer) {
        return createAction(new LocalClient(pGame, pPlayer));
    }

    // Methods: helper

    private void assertEnabled(StateImpl pState, String pPlayer,
        boolean pExpectedEnabled) {
        LocalClient client = new LocalClient(new GameImpl(pState), pPlayer);
        Action action = createAction(client);
        client.sendRefreshEvent();
        assertEquals(pExpectedEnabled, action.isEnabled());
    }

    private CommandAction createAction(Client pClient) {
        return new CommandAction(pClient, createCommand());
    }

    protected abstract Command createCommand();

}
