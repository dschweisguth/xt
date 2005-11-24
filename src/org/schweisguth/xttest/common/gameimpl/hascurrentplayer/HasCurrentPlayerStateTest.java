package org.schweisguth.xttest.common.gameimpl.hascurrentplayer;

import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.RearrangeRackCommand;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.hascurrentplayer.RearrangedRackEvent;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.common.gameimpl.base.TestClient;
import org.schweisguth.xttest.testutil.BaseTest;

public class HasCurrentPlayerStateTest extends BaseTest {
    public void testRearrangeRackCurrentPlayer() {
        ListenableGame game = new GameImpl(
            new MovingState(TWO_PLAYERS, new String[] { "A", EEEEEEE }));
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new RearrangeRackCommand(0, 1);
        client1.execute(command);

        Game expectedGame = new GameImpl(
            new MovingState(TWO_PLAYERS, new String[] { "-A-----", EEEEEEE }));
        assertEquals(expectedGame, game);

        Event event = new RearrangedRackEvent(expectedGame,
            new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testRearrangeRackCurrentPlayerAfterTransfer()

    {
        ListenableGame game = new GameImpl(
            new MovingState(TWO_PLAYERS, new String[] { "AB", EEEEEEE },
                new TransferSet(1, 7, 7)));
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new RearrangeRackCommand(0, 1);
        client1.execute(command);

        Game expectedGame = new GameImpl(new MovingState(TWO_PLAYERS,
            new String[] { "BA", EEEEEEE }, new TransferSet(0, 7, 7)));
        assertEquals(expectedGame, game);

        Event event = new RearrangedRackEvent(expectedGame,
            new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testRearrangeRackOtherPlayer() {
        ListenableGame game = new GameImpl(
            new MovingState(TWO_PLAYERS, new String[] { AAAAAAA, "E" }));
        TestClient client2 = new TestClient(game, "player2");
        final Command command = new RearrangeRackCommand(0, 1);
        client2.execute(command);

        Game expectedGame = new GameImpl(
            new MovingState(TWO_PLAYERS, new String[] { AAAAAAA, "-E-----" }));
        assertEquals(expectedGame, game);

        Event event = new RearrangedRackEvent(expectedGame,
            new Request("player2", command));
        assertEquals(CollectionUtil.asList(event), client2.getEvents());

    }

    public void testRearrangeRackOtherPlayerAfterTransfer() {
        final TransferSet expectedTransferSet = new TransferSet(0, 0, 0);
        ListenableGame game = new GameImpl(new MovingState(
            TWO_PLAYERS, new String[] { AAAAAAA, "E" }, expectedTransferSet));
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new RearrangeRackCommand(0, 1);
        client2.execute(command);

        Game expectedGame = new GameImpl(new MovingState(TWO_PLAYERS,
            new String[] { AAAAAAA, "-E" }, expectedTransferSet));
        assertEquals(expectedGame, game);

        Event event = new RearrangedRackEvent(expectedGame,
            new Request("player2", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testRearrangeRackBadArgs() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        assertFalse(
            state.canExecute("player1", new RearrangeRackCommand(0, 0)));
    }

}
