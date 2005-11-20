package org.schweisguth.xttest.client.server;

import java.rmi.RemoteException;
import junit.framework.TestCase;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.client.server.ClientImpl;
import org.schweisguth.xt.client.server.RefreshEvent;
import org.schweisguth.xt.client.server.ServerUtil;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.StartCommand;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.contract.AssertionFailedError;
import org.schweisguth.xt.common.util.logging.Logger;
import org.schweisguth.xt.server.ServerImpl;
import org.schweisguth.xttest.testutil.TestListener;

public class ClientTest extends TestCase {
    public void testCreate() throws Exception {
        Client client = getClient();
        assertEquals("player1", client.getPlayer());
        assertTrue(client.playerIs("player1"));
        assertEquals(new GameImpl(), client.getGame());
        assertTrue(client.canExecute(new JoinCommand()));
        assertFalse(client.canExecute(new StartCommand()));
    }

    public void testExecute() throws Exception {
        Client client = getClient();
        client.execute(new JoinCommand());
        assertEquals(new GameImpl(new JoiningState(new String[] { "player1" })),
            client.getGame());
    }

    public void testExecuteFailure() throws Exception {
        Logger.global.severe("The following error is expected:");
        try {
            getClient().execute(new StartCommand());
        } catch (RemoteException e) {
            assertEquals(AssertionFailedError.class, e.detail.getClass());
            return;
        }
        fail();
    }

    public void testAddListener() throws Exception {
        Client client = getClient();
        TestListener listener = addListener(client);
        client.execute(new JoinCommand());
        Game expectedGame =
            new GameImpl(new JoiningState(new String[] { "player1" }));
        Event event = new JoinedEvent(expectedGame,
            new Request("player1", new JoinCommand()));
        assertEquals(CollectionUtil.asList(event), listener.getEvents());
    }

    public void testRemoveListener() throws Exception {
        Client client = getClient();
        TestListener listener = addListener(client);
        client.removeListener(listener);
        client.execute(new JoinCommand());
        assertTrue(listener.getEvents().isEmpty());
    }

    public void testDisconnect() throws Exception {
        Client client = getClient();
        TestListener listener = addListener(client);
        client.disconnect();
        client.execute(new JoinCommand());
        assertTrue(listener.getEvents().isEmpty());
    }

    public void testSendRefreshEvent() throws Exception {
        Client client = getClient();
        TestListener listener = addListener(client);
        client.sendRefreshEvent();
        assertEquals(CollectionUtil.asList(new RefreshEvent(new GameImpl())),
            listener.getEvents());
    }

    private static Client getClient() throws Exception {
        ClientImpl client = new ClientImpl();
        ServerImpl.clear();
        ServerImpl.main(new String[] { });
        client.logIn(ServerUtil.getServer(), "player1");
        return client;
    }

    private static TestListener addListener(Client pClient) {
        TestListener listener = new TestListener();
        pClient.addListener(listener);
        return listener;
    }

}
