package org.schweisguth.xttest.client;

import java.rmi.RemoteException;
import org.schweisguth.xt.client.chat.display.ChatDisplayController;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingforfirst.DrawingForFirstState;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.gameimpl.stateimpl.StateImpl;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.testutil.BaseTest;

public class ChatDisplayTest extends BaseTest {
    public void testCreateJoiningPlayer() throws RemoteException {
        assertFirstMessage(new JoiningState(new String[] { "player1" }),
            "player1", ChatDisplayController.WELCOME_BACK);
    }

    public void testCreateJoiningObserver() throws RemoteException {
        assertFirstMessage(new JoiningState(), "observer",
            ChatDisplayController.WELCOME + ChatDisplayController.JOIN_OR_WATCH);
    }

    public void testCreateOtherStatePlayer() throws RemoteException {
        assertFirstMessage(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            "player1", ChatDisplayController.WELCOME_BACK);
    }

    public void testCreateOtherStateObserver() throws RemoteException {
        assertFirstMessage(new DrawingForFirstState(TWO_PLAYERS), "observer",
            ChatDisplayController.WELCOME + ChatDisplayController.WATCH);
    }

    public void testCreateEndedPlayer() throws RemoteException {
        assertFirstMessage(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            "player1",
            ChatDisplayController.WELCOME_BACK +
                ChatDisplayController.START_NEW_GAME);
    }

    public void testCreateEndedObserver() throws RemoteException {
        assertFirstMessage(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            "observer",
            ChatDisplayController.WELCOME + ChatDisplayController.START_NEW_GAME);
    }

    private static void assertFirstMessage(StateImpl pState, String pPlayer,
        String pMessage) throws RemoteException {
        Client client = new LocalClient(new GameImpl(pState), pPlayer);
        ChatDisplayController controller = new ChatDisplayController(client);
        client.sendRefreshEvent();
        assertTrue(controller.getText().indexOf(pMessage) != -1);
    }

}
