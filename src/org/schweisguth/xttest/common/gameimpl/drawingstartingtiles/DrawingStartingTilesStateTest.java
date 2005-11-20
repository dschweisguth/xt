package org.schweisguth.xttest.common.gameimpl.drawingstartingtiles;

import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.DrawStartingTilesCommand;
import org.schweisguth.xt.common.command.RearrangeRackCommand;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingforfirst.DrawingForFirstState;
import org.schweisguth.xt.common.gameimpl.drawingstartingtiles.DrawingStartingTilesState;
import org.schweisguth.xt.common.gameimpl.drawingstartingtiles.DrewStartingTilesEvent;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.gameimpl.stateimpl.StateImpl;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xttest.common.gameimpl.base.BaseGameStateTest;
import org.schweisguth.xttest.common.gameimpl.base.CanExecuteTester;
import org.schweisguth.xttest.common.gameimpl.base.TestClient;

public class DrawingStartingTilesStateTest extends BaseGameStateTest {
    public void testSerializable() throws Exception {
        StateImpl state = new DrawingStartingTilesState(
            TWO_PLAYERS, new String[] { "", EEEEEEE });
        assertIsSerializable(new GameImpl(state));
    }

    public void testCreateNoPlayersHaveDrawn() {
        Game game = new GameImpl(new DrawingStartingTilesState(TWO_PLAYERS));
        CanExecuteTester tester1 = new CanExecuteTester();
        tester1.addTrue(new DrawStartingTilesCommand());
        tester1.doAssert(game, "player1");
        new CanExecuteTester().doAssert(game, "player2");
        new CanExecuteTester().doAssert(game, "observer");
    }

    public void testCreateOnePlayerHasDrawn() {
        StateImpl state = new DrawingStartingTilesState(TWO_PLAYERS,
            new String[] { "", EEEEEEE });
        Game game = new GameImpl(state);
        CanExecuteTester tester1 = new CanExecuteTester();
        tester1.addTrue(new DrawStartingTilesCommand());
        tester1.doAssert(game, "player1");
        CanExecuteTester tester2 = new CanExecuteTester();
        tester2.addTrue(new RearrangeRackCommand(0, 1));
        tester2.doAssert(game, "player2");
        new CanExecuteTester().doAssert(game, "observer");
    }

    public void testDrawStartingTiles1() {
        DrawingStartingTilesState state =
            new DrawingStartingTilesState(TWO_PLAYERS);
        state.setBoxLid(new BoxLid(AAAAAAA));
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new DrawStartingTilesCommand();
        client1.execute(command);

        DrawingStartingTilesState expectedState = new DrawingStartingTilesState(
            TWO_PLAYERS, new String[] { AAAAAAA, "" }, 1);
        expectedState.setBoxLid(new BoxLid(""));
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event = new DrewStartingTilesEvent(expectedGame,
            new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testDrawStartingTiles2() {
        DrawingStartingTilesState state = new DrawingStartingTilesState(
            TWO_PLAYERS, new String[] { "", EEEEEEE });
        state.setBoxLid(new BoxLid(AAAAAAA));
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new DrawStartingTilesCommand();
        client1.execute(command);

        MovingState expectedState =
            new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, 1);
        expectedState.setBoxLid(new BoxLid(""));
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event = new DrewStartingTilesEvent(expectedGame,
            new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testDrawStartingTilesEarly() {
        assertWillFail(new DrawingForFirstState(TWO_PLAYERS),
            "player1", new DrawStartingTilesCommand());
    }

    public void testDrawStartingTilesWrongPlayer() {
        assertWillFail(new DrawingStartingTilesState(TWO_PLAYERS),
            "player2", new DrawStartingTilesCommand());
    }

    public void testDrawStartingTilesTwice() {
        assertWillFail(
            new DrawingStartingTilesState(TWO_PLAYERS,
                new String[] { "", EEEEEEE }),
            "player2", new DrawStartingTilesCommand());
    }

    public void testDrawStartingTilesLate() {
        assertWillFail(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            "player1", new DrawStartingTilesCommand());
    }

}
