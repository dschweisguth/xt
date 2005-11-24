package org.schweisguth.xttest.common.gameimpl.approving;

import org.schweisguth.xt.common.command.ApproveCommand;
import org.schweisguth.xt.common.command.ChallengeCommand;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.RearrangeRackCommand;
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
import org.schweisguth.xt.common.gameimpl.approving.ApprovedEvent;
import org.schweisguth.xt.common.gameimpl.approving.ApprovingState;
import org.schweisguth.xt.common.gameimpl.approving.ChallengedEvent;
import org.schweisguth.xt.common.gameimpl.challenging.ChallengingState;
import org.schweisguth.xt.common.gameimpl.drawingnewtiles.DrawingNewTilesState;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xttest.common.gameimpl.base.CanExecuteTester;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.common.gameimpl.base.TestClient;
import org.schweisguth.xttest.testutil.BaseTest;

public class ApprovingStateTest extends BaseTest {
    public void testSerializable() throws Exception {
        ApprovingState state =
            new ApprovingState(THREE_PLAYERS, AAAAAAA_EEEEEEE_IIIIIII, MOVE_TWO);
        state.setApprovals(CollectionUtil.asStickySet("player2"));
        assertIsSerializable(state);
    }

    public void testCreate2() {
        Game game = new GameImpl(
            new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO));
        CanExecuteTester tester1 = new CanExecuteTester();
        tester1.addTrue(new ChallengeCommand());
        tester1.doAssert(game, "player1");
        CanExecuteTester tester2 = new CanExecuteTester();
        tester2.addTrue(new ApproveCommand());
        tester2.addTrue(new ChallengeCommand());
        tester2.addTrue(new RearrangeRackCommand(0, 1));
        tester2.doAssert(game, "player2");
        new CanExecuteTester().doAssert(game, "observer");
    }

    public void testCreate3() {
        ApprovingState state =
            new ApprovingState(THREE_PLAYERS, AAAAAAA_EEEEEEE_IIIIIII, MOVE_TWO);
        state.setApprovals(CollectionUtil.asStickySet("player2"));
        Game game = new GameImpl(state);
        CanExecuteTester tester1 = new CanExecuteTester();
        tester1.addTrue(new ChallengeCommand());
        tester1.doAssert(game, "player1");
        CanExecuteTester tester2 = new CanExecuteTester();
        tester2.addTrue(new RearrangeRackCommand(0, 1));
        tester2.doAssert(game, "player2");
        CanExecuteTester tester3 = new CanExecuteTester();
        tester3.addTrue(new ApproveCommand());
        tester3.addTrue(new ChallengeCommand());
        tester3.addTrue(new RearrangeRackCommand(0, 1));
        tester3.doAssert(game, "player3");
        new CanExecuteTester().doAssert(game, "observer");
    }

    public void testApprove2() {
        ApprovingState state =
            new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO);
        final BoxLid expectedBoxLid = new BoxLid("GGG");
        state.setBoxLid(expectedBoxLid);
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new ApproveCommand();
        client2.execute(command);

        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(4);
        DrawingNewTilesState expectedState = new DrawingNewTilesState(
            TWO_PLAYERS, AAAAAAA_EEEEEEE, expectedScores, MOVE_TWO);
        expectedState.setBoxLid(expectedBoxLid);
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event = new ApprovedEvent(expectedGame,
            new Request("player2", command), "player1");
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testApprove3() {
        ListenableGame game = new GameImpl(
            new ApprovingState(THREE_PLAYERS, AAAAAAA_EEEEEEE_IIIIIII, MOVE_TWO));
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new ApproveCommand();
        client2.execute(command);

        ApprovingState expectedState =
            new ApprovingState(THREE_PLAYERS, AAAAAAA_EEEEEEE_IIIIIII, MOVE_TWO);
        expectedState.setApprovals(CollectionUtil.asStickySet("player2"));
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event = new ApprovedEvent(expectedGame,
            new Request("player2", command), "player1");
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testApproveTilesInRackButNotInBoxLid() {
        ApprovingState state =
            new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO);
        final BoxLid expectedBoxLid = new BoxLid("");
        state.setBoxLid(expectedBoxLid);
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new ApproveCommand();
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

        Event event = new ApprovedEvent(expectedGame,
            new Request("player2", command), "player1");
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testApproveLastTurnOne() {
        Board board = new Board();
        board.place(new Rack(AAAAAAA), MOVE_TWO);
        board.approve();
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(4);
        ApprovingState state = new ApprovingState(TWO_PLAYERS,
            new String[] { "AAAAA", "E" }, board, scores, 1,
            new TransferSet(new int[] { 0 }, 9, 7, Axis.X));
        final BoxLid expectedBoxLid = new BoxLid("");
        state.setBoxLid(expectedBoxLid);
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new ApproveCommand();
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

        Event event = new ApprovedEvent(expectedGame,
            new Request("player1", command), "player2");
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testApproveLastTurnSeven() {
        ApprovingState state =
            new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_SEVEN);
        final BoxLid expectedBoxLid = new BoxLid("");
        state.setBoxLid(expectedBoxLid);
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new ApproveCommand();
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

        Event event = new ApprovedEvent(expectedGame,
            new Request("player2", command), "player1");
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testApproveSelf() {
        ApprovingState state =
            new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO);
        assertFalse(state.canExecute("player1", new ApproveCommand()));
    }

    public void testChallenge() {
        ListenableGame game = new GameImpl(
            new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO));
        TestClient client1 = new TestClient(game, "player1");
        LocalClient client2 = new LocalClient(game, "player2");
        client1.clear();
        final Command command = new ChallengeCommand();
        client2.execute(command);

        Game expectedGame = new GameImpl(
            new ChallengingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO));
        assertEquals(expectedGame, game);

        Event event = new ChallengedEvent(expectedGame,
            new Request("player2", command), "player1");
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

}
