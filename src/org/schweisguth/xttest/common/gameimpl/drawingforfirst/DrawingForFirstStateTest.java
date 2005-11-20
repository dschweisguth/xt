package org.schweisguth.xttest.common.gameimpl.drawingforfirst;

import java.util.Map;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.DrawForFirstCommand;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingforfirst.DrawingForFirstState;
import org.schweisguth.xt.common.gameimpl.drawingforfirst.DrewForFirstEvent;
import org.schweisguth.xt.common.gameimpl.drawingstartingtiles.DrawingStartingTilesState;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.collection.HashStickyMap;
import org.schweisguth.xttest.common.gameimpl.base.BaseGameStateTest;
import org.schweisguth.xttest.common.gameimpl.base.CanExecuteTester;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.common.gameimpl.base.TestClient;

public class DrawingForFirstStateTest extends BaseGameStateTest {
    public void testSerializable() throws Exception {
        Map tilesDrawnForFirst = new HashStickyMap();
        tilesDrawnForFirst.put("player1", Tile.get('A'));
        tilesDrawnForFirst.put("player2", null);
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        assertIsSerializable(new GameImpl(state));
    }

    public void testCreateTwoPlayersNoneHaveDrawn() {
        Game game = new GameImpl(new DrawingForFirstState(TWO_PLAYERS));
        CanExecuteTester tester1 = new CanExecuteTester();
        tester1.addTrue(new DrawForFirstCommand());
        tester1.doAssert(game, "player1");
        new CanExecuteTester().doAssert(game, "observer");
    }

    public void testCreateTwoPlayersOneHasDrawn() {
        Map tilesDrawnForFirst = new HashStickyMap();
        tilesDrawnForFirst.put("player1", Tile.get('A'));
        tilesDrawnForFirst.put("player2", null);
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        Game game = new GameImpl(state);
        new CanExecuteTester().doAssert(game, "player1");
        CanExecuteTester tester2 = new CanExecuteTester();
        tester2.addTrue(new DrawForFirstCommand());
        tester2.doAssert(game, "player2");
        new CanExecuteTester().doAssert(game, "observer");
    }

    public void testCreateThreePlayersTwoHaveTied() {
        Map tilesDrawnForFirst = new HashStickyMap();
        tilesDrawnForFirst.put("player1", null);
        tilesDrawnForFirst.put("player2", null);
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        Game game = new GameImpl(state);
        CanExecuteTester tester12 = new CanExecuteTester();
        tester12.addTrue(new DrawForFirstCommand());
        tester12.doAssert(game, "player1");
        tester12.doAssert(game, "player2");
        new CanExecuteTester().doAssert(game, "player3");
        new CanExecuteTester().doAssert(game, "observer");
    }

    public void testDrawForFirst() {
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        state.setBoxLid(new BoxLid("A"));
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new DrawForFirstCommand();
        client1.execute(command);

        Map expectedTilesDrawnForFirst = new HashStickyMap();
        expectedTilesDrawnForFirst.put("player1", Tile.get('A'));
        expectedTilesDrawnForFirst.put("player2", null);
        DrawingForFirstState expectedState =
            new DrawingForFirstState(TWO_PLAYERS);
        expectedState.setTilesDrawnForFirst(expectedTilesDrawnForFirst);
        expectedState.setBoxLid(new BoxLid(""));
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event = new DrewForFirstEvent(expectedGame,
            new Request("player1", command), Tile.get('A'));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testDrawForFirst21() {
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        Map tilesDrawnForFirst = new HashStickyMap();
        tilesDrawnForFirst.put("player1", Tile.get('A'));
        tilesDrawnForFirst.put("player2", null);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        state.setBoxLid(new BoxLid("B"));
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new DrawForFirstCommand();
        client2.execute(command);

        Game expectedGame =
            new GameImpl(new DrawingStartingTilesState(TWO_PLAYERS));
        assertEquals(expectedGame, game);

        Event event = new DrewForFirstEvent(expectedGame,
            new Request("player2", command), Tile.get('B'));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testDrawForFirst22() {
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        Map tilesDrawnForFirst = new HashStickyMap();
        tilesDrawnForFirst.put("player1", Tile.get('B'));
        tilesDrawnForFirst.put("player2", null);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        state.setBoxLid(new BoxLid("A"));
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new DrawForFirstCommand();
        client2.execute(command);

        Game expectedGame =
            new GameImpl(new DrawingStartingTilesState(TWO_PLAYERS, 1));
        assertEquals(expectedGame, game);

        Event event = new DrewForFirstEvent(expectedGame,
            new Request("player2", command), Tile.get('A'));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testDrawForFirst31() {
        DrawingForFirstState state = new DrawingForFirstState(THREE_PLAYERS);
        Map tilesDrawnForFirst = new HashStickyMap();
        tilesDrawnForFirst.put("player1", Tile.get('A'));
        tilesDrawnForFirst.put("player2", Tile.get('A'));
        tilesDrawnForFirst.put("player3", null);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        state.setBoxLid(new BoxLid("B"));
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client3 = new LocalClient(game, "player3");
        client1.clear();
        final Command command = new DrawForFirstCommand();
        client3.execute(command);

        DrawingForFirstState expectedState =
            new DrawingForFirstState(THREE_PLAYERS);
        Map expectedTilesDrawnForFirst = new HashStickyMap();
        expectedTilesDrawnForFirst.put("player1", null);
        expectedTilesDrawnForFirst.put("player2", null);
        expectedState.setTilesDrawnForFirst(expectedTilesDrawnForFirst);
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event = new DrewForFirstEvent(expectedGame,
            new Request("player3", command), Tile.get('B'));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testDrawForFirst32() {
        DrawingForFirstState state = new DrawingForFirstState(THREE_PLAYERS);
        Map tilesDrawnForFirst = new HashStickyMap();
        tilesDrawnForFirst.put("player1", Tile.get('A'));
        tilesDrawnForFirst.put("player2", null);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        state.setBoxLid(new BoxLid("B"));
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new DrawForFirstCommand();
        client2.execute(command);

        Game expectedGame =
            new GameImpl(new DrawingStartingTilesState(THREE_PLAYERS));
        assertEquals(expectedGame, game);

        Event event = new DrewForFirstEvent(expectedGame,
            new Request("player2", command), Tile.get('B'));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testDrawForFirstEarly() {
        assertWillFail(new JoiningState(TWO_PLAYERS),
            "player1", new DrawForFirstCommand());
    }

    public void testDrawForFirstTwice() {
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        Map tilesDrawnForFirst = new HashStickyMap();
        tilesDrawnForFirst.put("player1", Tile.get('A'));
        tilesDrawnForFirst.put("player2", null);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        assertWillFail(state, "player1", new DrawForFirstCommand());
    }

    public void testDrawForFirstLate() {
        assertWillFail(new DrawingStartingTilesState(TWO_PLAYERS),
            "player1", new DrawForFirstCommand());
    }

}
