package org.schweisguth.xttest.common.gameimpl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import org.schweisguth.xt.common.command.GoOffLineCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.LogInCommand;
import org.schweisguth.xt.common.command.LogOutCommand;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.ListenerOperations;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.LoggedInEvent;
import org.schweisguth.xt.common.gameimpl.LoggedOutEvent;
import org.schweisguth.xt.common.gameimpl.WentOffLineEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xttest.common.gameimpl.base.TestClient;
import org.schweisguth.xttest.testutil.BaseTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class GameImplTest extends BaseTest {
    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new GameImpl(new JoiningState()));
        tester.addOther(new GameImpl(new JoiningState(TWO_PLAYERS)));
        tester.doAssert(
            new GameImpl(new JoiningState(new String[] { "player1" })));
    }

    public void testLogIn() {
        ListenableGame game = new GameImpl(new JoiningState());
        TestClient client1 = new TestClient(game, "player1");
        new TestClient(game, "player2");

        Game expectedGame = new GameImpl(new JoiningState());
        assertEquals(expectedGame, game);

        Event event = new LoggedInEvent(expectedGame,
            new Request("player2", new LogInCommand()));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testLogOut() {
        ListenableGame game = new GameImpl(new JoiningState());
        TestClient client2 = new TestClient(game, "player2");
        TestClient client1 = new TestClient(game, "player1");
        client2.disconnect();

        Game expectedGame = new GameImpl(new JoiningState());
        assertEquals(expectedGame, game);

        Event event = new LoggedOutEvent(expectedGame,
            new Request("player2", new LogOutCommand()));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testRemoveListener() {
        ListenableGame game = new GameImpl(new JoiningState());
        TestClient client = new TestClient(game, "player1");
        ErringListener listener = new ErringListener();
        game.addListener("ErringListener", listener);
        client.clear();
        client.execute(new JoinCommand());

        List expectedEvents = new ArrayList();
        expectedEvents.add(
            new JoinedEvent(game, new Request("player1", new JoinCommand())));
        expectedEvents.add(
            new WentOffLineEvent(game,
                new Request("ErringListener", new GoOffLineCommand())));
        assertEquals(expectedEvents, client.getEvents());
    }

    private static class ErringListener implements ListenerOperations {
        public void send(Event pEvent) throws RemoteException {
            throw new RemoteException();
        }
    }

}
