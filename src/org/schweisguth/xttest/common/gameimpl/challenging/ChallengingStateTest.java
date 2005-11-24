package org.schweisguth.xttest.common.gameimpl.challenging;

import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.OverruleChallengeCommand;
import org.schweisguth.xt.common.command.RearrangeRackCommand;
import org.schweisguth.xt.common.command.SustainChallengeCommand;
import org.schweisguth.xt.common.domain.Axis;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.challenging.ChallengingState;
import org.schweisguth.xt.common.gameimpl.challenging.OverruledChallengeEvent;
import org.schweisguth.xt.common.gameimpl.challenging.SustainedChallengeEvent;
import org.schweisguth.xt.common.gameimpl.drawingnewtiles.DrawingNewTilesState;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xttest.common.gameimpl.base.CanExecuteTester;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.common.gameimpl.base.TestClient;
import org.schweisguth.xttest.testutil.BaseTest;

public class ChallengingStateTest extends BaseTest {
    public void testSerializable() throws Exception {
        assertIsSerializable(
            new ChallengingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO));
    }

    public void testCreate() {
        ChallengingState state =
            new ChallengingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO);
        CanExecuteTester tester1 = new CanExecuteTester();
        tester1.addTrue(new OverruleChallengeCommand());
        tester1.addTrue(new SustainChallengeCommand());
        tester1.doAssert(state, "player1");
        CanExecuteTester tester2 = new CanExecuteTester();
        tester2.addTrue(new OverruleChallengeCommand());
        tester2.addTrue(new SustainChallengeCommand());
        tester2.addTrue(new RearrangeRackCommand(0, 1));
        tester2.doAssert(state, "player2");
        new CanExecuteTester().doAssert(state, "observer");
    }

    public void testOverruleChallenge() {
        ListenableGame game = new GameImpl(
            new ChallengingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO));
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new OverruleChallengeCommand();
        client2.execute(command);

        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(4);
        Game expectedGame = new GameImpl(new DrawingNewTilesState(TWO_PLAYERS,
            AAAAAAA_EEEEEEE, expectedScores, MOVE_TWO));
        assertEquals(expectedGame, game);

        Event event = new OverruledChallengeEvent(expectedGame,
            new Request("player2", command), "player1");
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testOverruleChallengeTilesInRackButNotInBoxLid() {
        ChallengingState state =
            new ChallengingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO);
        final BoxLid expectedBoxLid = new BoxLid("");
        state.setBoxLid(expectedBoxLid);
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new OverruleChallengeCommand();
        client2.execute(command);

        Board expectedBoard = new Board();
        expectedBoard.place(new Rack(AAAAAAA), MOVE_TWO);
        expectedBoard.approve();
        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(4);
        MovingState expectedState = new MovingState(TWO_PLAYERS,
            new String[] { "--AAAAA", EEEEEEE }, expectedBoard, expectedScores,
            1);
        expectedState.setBoxLid(expectedBoxLid);
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event = new OverruledChallengeEvent(expectedGame,
            new Request("player2", command), "player1");
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testOverruleChallengeLastTurnOne() {
        Board board = new Board();
        board.place(new Rack(AAAAAAA), MOVE_TWO);
        board.approve();
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(4);
        ChallengingState state = new ChallengingState(TWO_PLAYERS,
            new String[] { "AAAAA", "E" }, board, scores, 1,
            new TransferSet(new int[] { 0 }, 9, 7, Axis.X));
        final BoxLid expectedBoxLid = new BoxLid("");
        state.setBoxLid(expectedBoxLid);
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new OverruleChallengeCommand();
        client1.execute(command);

        Board expectedBoard = new Board();
        expectedBoard.place(new Rack("AAE"),
            new TransferSet(new int[] { 0, 1, 2 }, 7, 7, Axis.X));
        expectedBoard.approve();
        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(4);
        expectedScores.incrementScore(3);
        expectedScores.endGame();
        expectedScores.incrementScore(-5);
        expectedScores.incrementScore(5);
        EndedState expectedState = new EndedState(TWO_PLAYERS,
            new String[] { "AAAAA", "" }, expectedBoard, expectedScores);
        expectedState.setBoxLid(expectedBoxLid);
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event = new OverruledChallengeEvent(expectedGame,
            new Request("player1", command), "player2");
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testOverruleChallengeLastTurnSeven() {
        ChallengingState state =
            new ChallengingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_SEVEN);
        final BoxLid expectedBoxLid = new BoxLid("");
        state.setBoxLid(expectedBoxLid);
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new OverruleChallengeCommand();
        client2.execute(command);

        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(66);
        expectedScores.incrementScore(0);
        expectedScores.endGame();
        expectedScores.incrementScore(7);
        expectedScores.incrementScore(-7);
        EndedState expectedState = new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE,
            expectedScores, MOVE_SEVEN);
        expectedState.setBoxLid(expectedBoxLid);
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event = new OverruledChallengeEvent(expectedGame,
            new Request("player2", command), "player1");
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testSustainChallenge() {
        final String[] racks = { "QZNNNNN", EEEEEEE };
        ListenableGame game =
            new GameImpl(new ChallengingState(TWO_PLAYERS, racks, MOVE_TWO));
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new SustainChallengeCommand();
        client2.execute(command);

        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(0);
        Game expectedGame = new GameImpl(new MovingState(TWO_PLAYERS, racks,
            new Board(), expectedScores, 1));
        assertEquals(expectedGame, game);

        Event event = new SustainedChallengeEvent(expectedGame,
            new Request("player2", command), "player1");
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

}
