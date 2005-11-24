package org.schweisguth.xttest.common.gameimpl.drawingnewtiles;

import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.DrawNewTilesCommand;
import org.schweisguth.xt.common.command.RearrangeRackCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingnewtiles.DrawingNewTilesState;
import org.schweisguth.xt.common.gameimpl.drawingnewtiles.DrewNewTilesEvent;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xttest.common.gameimpl.base.CanExecuteTester;
import org.schweisguth.xttest.common.gameimpl.base.TestClient;
import org.schweisguth.xttest.testutil.BaseTest;

public class DrawingNewTilesStateTest extends BaseTest {
    public void testSerializable() throws Exception {
        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(4);
        DrawingNewTilesState state = new DrawingNewTilesState(
            TWO_PLAYERS, AAAAAAA_EEEEEEE, expectedScores, MOVE_TWO);
        assertIsSerializable(state);
    }

    public void testCreate() {
        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(4);
        DrawingNewTilesState state = new DrawingNewTilesState(TWO_PLAYERS,
            AAAAAAA_EEEEEEE, expectedScores, MOVE_TWO);
        CanExecuteTester tester1 = new CanExecuteTester();
        tester1.addTrue(new DrawNewTilesCommand());
        tester1.addTrue(new RearrangeRackCommand(2, 3)); // 0, 1 are on board
        tester1.doAssert(state, "player1");
        CanExecuteTester tester2 = new CanExecuteTester();
        tester2.addTrue(new RearrangeRackCommand(0, 1));
        tester2.doAssert(state, "player2");
        new CanExecuteTester().doAssert(state, "observer");
    }

    public void testDrawNewTiles() {
        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(4);
        DrawingNewTilesState state = new DrawingNewTilesState(TWO_PLAYERS,
            AAAAAAA_EEEEEEE, expectedScores, MOVE_TWO);
        state.setBoxLid(new BoxLid("GGG"));
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new DrawNewTilesCommand();
        client1.execute(command);

        Board expectedBoard = new Board();
        expectedBoard.place(new Rack(AAAAAAA), MOVE_TWO);
        expectedBoard.approve();
        MovingState expectedState = new MovingState(TWO_PLAYERS,
            new String[] { "GGAAAAA", EEEEEEE }, expectedBoard, expectedScores,
            1);
        expectedState.setBoxLid(new BoxLid("G"));
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event = new DrewNewTilesEvent(expectedGame,
            new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }
}
