package org.schweisguth.xttest.common.gameimpl.moving;

import java.util.Arrays;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.EndGameCommand;
import org.schweisguth.xt.common.command.ExchangeCommand;
import org.schweisguth.xt.common.command.FinishCommand;
import org.schweisguth.xt.common.command.PassCommand;
import org.schweisguth.xt.common.command.RearrangeBoardCommand;
import org.schweisguth.xt.common.command.RearrangeRackCommand;
import org.schweisguth.xt.common.command.TakeBackCommand;
import org.schweisguth.xt.common.command.TransferCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.domain.Transfer;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.approving.ApprovingState;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.hastransferset.TransferredEvent;
import org.schweisguth.xt.common.gameimpl.moving.EndedGameEvent;
import org.schweisguth.xt.common.gameimpl.moving.ExchangedEvent;
import org.schweisguth.xt.common.gameimpl.moving.FinishedEvent;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.gameimpl.moving.PassedEvent;
import org.schweisguth.xt.common.gameimpl.moving.RearrangedBoardEvent;
import org.schweisguth.xt.common.gameimpl.moving.TookBackEvent;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.collection.HashStickySet;
import org.schweisguth.xttest.common.gameimpl.base.CanExecuteTester;
import org.schweisguth.xttest.common.gameimpl.base.TestClient;
import org.schweisguth.xttest.testutil.BaseTest;

public class MovingStateTest extends BaseTest {
    public void testSerializable() throws Exception {
        assertIsSerializable(
            new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO));
    }

    public void testCreate() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        CanExecuteTester tester1 = new CanExecuteTester();
        tester1.addTrue(new ExchangeCommand(new int[] { 0 }));
        tester1.addTrue(new PassCommand());
        tester1.addTrue(new RearrangeRackCommand(0, 1));
        tester1.doAssert(state, "player1");
        CanExecuteTester tester2 = new CanExecuteTester();
        tester2.addTrue(new RearrangeRackCommand(0, 1));
        tester2.doAssert(state, "player2");
        new CanExecuteTester().doAssert(state, "observer");
    }

    public void testCreateOnePlayerPassed() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        state.setPasses(CollectionUtil.asStickySet("player2"));
        CanExecuteTester tester1 = new CanExecuteTester();
        tester1.addTrue(new ExchangeCommand(new int[] { 0 }));
        tester1.addTrue(new PassCommand());
        tester1.addTrue(new EndGameCommand());
        tester1.addTrue(new RearrangeRackCommand(0, 1));
        tester1.doAssert(state, "player1");
        CanExecuteTester tester2 = new CanExecuteTester();
        tester2.addTrue(new RearrangeRackCommand(0, 1));
        tester2.doAssert(state, "player2");
        new CanExecuteTester().doAssert(state, "observer");
    }

    public void testCreateOneTransfer() {
        int rackPosition = 0;
        Position boardPosition = new Position(7, 7);
        Transfer transfer = new Transfer(rackPosition, boardPosition);
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE,
            new TransferSet(transfer));
        CanExecuteTester tester1 = new CanExecuteTester();
        tester1.addTrue(new TransferCommand(new Transfer(1, 7, 8)));
        // Test that placing second tile out of line is OK
        tester1.addTrue(new TransferCommand(new Transfer(1, 8, 8)));
        tester1.addFalse(new TransferCommand(transfer));
        tester1.addFalse(new TransferCommand(new Transfer(rackPosition, 7, 8)));
        tester1.addFalse(new TransferCommand(new Transfer(1, boardPosition)));
        tester1.addTrue(new ExchangeCommand(new int[] { 1 }));
        tester1.addTrue(new PassCommand());
        tester1.addTrue(new RearrangeRackCommand(1, 2));
        tester1.addTrue(new RearrangeRackCommand(1, rackPosition));
        tester1.addTrue(
            new RearrangeBoardCommand(boardPosition, new Position(7, 8)));
        tester1.doAssert(state, "player1");
        CanExecuteTester tester2 = new CanExecuteTester();
        tester2.addTrue(new RearrangeRackCommand(0, 1));
        tester2.doAssert(state, "player2");
        new CanExecuteTester().doAssert(state, "observer");
    }

    public void testCreateTwoTransfers() {
        MovingState state =
            new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO);
        CanExecuteTester tester1 = new CanExecuteTester();
        tester1.addTrue(new ExchangeCommand(new int[] { 2 }));
        tester1.addTrue(new PassCommand());
        tester1.addTrue(new FinishCommand());
        tester1.addTrue(
            new RearrangeBoardCommand(new Position(7, 7), new Position(7, 8)));
        tester1.doAssert(state, "player1");
        CanExecuteTester tester2 = new CanExecuteTester();
        tester2.addTrue(new RearrangeRackCommand(0, 1));
        tester2.doAssert(state, "player2");
        new CanExecuteTester().doAssert(state, "observer");
    }

    public void testCreateEmptyBoxLid() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        state.setBoxLid(new BoxLid(""));
        CanExecuteTester tester1 = new CanExecuteTester();
        tester1.addTrue(new PassCommand());
        tester1.addTrue(new RearrangeRackCommand(0, 1));
        tester1.doAssert(state, "player1");
        CanExecuteTester tester2 = new CanExecuteTester();
        tester2.addTrue(new RearrangeRackCommand(0, 1));
        tester2.doAssert(state, "player2");
        new CanExecuteTester().doAssert(state, "observer");
    }

    public void testCreateBoxLidWithOneTile() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        state.setBoxLid(new BoxLid("A"));
        CanExecuteTester tester1 = new CanExecuteTester();
        tester1.addTrue(new ExchangeCommand(new int[] { 0 }));
        tester1.addFalse(new ExchangeCommand(new int[] { 0, 1 }));
        tester1.addTrue(new PassCommand());
        tester1.addTrue(new RearrangeRackCommand(0, 1));
        tester1.doAssert(state, "player1");
        CanExecuteTester tester2 = new CanExecuteTester();
        tester2.addTrue(new RearrangeRackCommand(0, 1));
        tester2.doAssert(state, "player2");
        new CanExecuteTester().doAssert(state, "observer");
    }

    public void testTransfer() {
        final String[] racks = { "QNNNNNN", EEEEEEE };
        ListenableGame game = new GameImpl(new MovingState(TWO_PLAYERS, racks));
        TestClient client1 = new TestClient(game, "player1");
        final int rackPosition = 0;
        final Position boardPosition = new Position(0, 0);
        Transfer transfer = new Transfer(rackPosition, boardPosition);
        final Command command = new TransferCommand(transfer);
        client1.execute(command);

        Game expectedGame = new GameImpl(new MovingState(TWO_PLAYERS, racks,
            new TransferSet(transfer)));
        assertEquals(expectedGame, game);

        Event event =
            new TransferredEvent(expectedGame, new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testTransferSecondTile() {
        final Transfer transfer1 = new Transfer(0, 7, 7);
        ListenableGame game = new GameImpl(new MovingState(TWO_PLAYERS,
            AAAAAAA_EEEEEEE, new TransferSet(transfer1)));
        TestClient client1 = new TestClient(game, "player1");
        final Transfer transfer2 = new Transfer(1, 8, 7);
        final Command command = new TransferCommand(transfer2);
        client1.execute(command);

        TransferSet expectedTransferSet = new TransferSet();
        expectedTransferSet.add(transfer1);
        expectedTransferSet.add(transfer2);
        Game expectedGame = new GameImpl(
            new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, expectedTransferSet));
        assertEquals(expectedGame, game);

        Event event =
            new TransferredEvent(expectedGame, new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testTransferWrongPlayer() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        assertFalse(state.canExecute(
            "player2", new TransferCommand(new Transfer(0, 0, 0))));
    }

    public void testTransferBadRackPosition() {
        MovingState state =
            new MovingState(TWO_PLAYERS, new String[] { "-AAAAAA", EEEEEEE });
        assertFalse(state.canExecute(
            "player1", new TransferCommand(new Transfer(0, 0, 0))));
    }

    public void testTransferBadBoardPosition() {
        final Position boardPosition = new Position(0, 0);
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE,
            new TransferSet(0, boardPosition));
        assertFalse(state.canExecute(
            "player1", new TransferCommand(new Transfer(1, boardPosition))));
    }

    public void testMoveBoard() {
        final int rackPosition = 0;
        final Position source = new Position(0, 0);
        final Position destination = new Position(1, 0);
        ListenableGame game = new GameImpl(new MovingState(TWO_PLAYERS,
            AAAAAAA_EEEEEEE, new TransferSet(rackPosition, source)));
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new RearrangeBoardCommand(source, destination);
        client1.execute(command);

        Game expectedGame = new GameImpl(new MovingState(TWO_PLAYERS,
            AAAAAAA_EEEEEEE, new TransferSet(rackPosition, destination)));
        assertEquals(expectedGame, game);

        Event event = new RearrangedBoardEvent(expectedGame,
            new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testMoveBoardWrongPlayer() {
        final Position source = new Position(0, 0);
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE,
            new TransferSet(0, source));
        assertFalse(state.canExecute(
            "player2", new RearrangeBoardCommand(source, new Position(1, 0))));
    }

    public void testMoveBoardBadArgs() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        assertFalse(state.canExecute("player1",
            new RearrangeBoardCommand(
                new Position(0, 0), new Position(1, 0))));
    }

    public void testTakeBack() {
        final String[] racks = { "QNNNNNN", EEEEEEE };
        final Transfer transfer = new Transfer(0, 0, 0);
        ListenableGame game = new GameImpl(new MovingState(TWO_PLAYERS, racks,
            new TransferSet(transfer)));
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new TakeBackCommand(transfer);
        client1.execute(command);

        Game expectedGame = new GameImpl(new MovingState(TWO_PLAYERS, racks));
        assertEquals(expectedGame, game);

        Event event =
            new TookBackEvent(expectedGame, new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testTakeBackWithEarlierEmptySpaceInRack() {
        final String[] racks = { "QZNNNNN", EEEEEEE };
        final Transfer firstTransfer = new Transfer(0, new Position(0, 0));
        final Transfer secondTransfer = new Transfer(1, 1, 0);
        TransferSet transferSet = new TransferSet();
        transferSet.add(firstTransfer);
        transferSet.add(secondTransfer);
        ListenableGame game =
            new GameImpl(new MovingState(TWO_PLAYERS, racks, transferSet));
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new TakeBackCommand(secondTransfer);
        client1.execute(command);

        Game expectedGame = new GameImpl(
            new MovingState(TWO_PLAYERS, racks, new TransferSet(firstTransfer)));
        assertEquals(expectedGame, game);

        Event event =
            new TookBackEvent(expectedGame, new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testTakeBackAfterRearrangeRack() {
        final String[] racks = { "ZQNNNNN", EEEEEEE };
        final Transfer transfer = new Transfer(1, 0, 0);
        ListenableGame game = new GameImpl(
            new MovingState(TWO_PLAYERS, racks, new TransferSet(transfer)));
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new TakeBackCommand(transfer);
        client1.execute(command);

        Game expectedGame = new GameImpl(new MovingState(TWO_PLAYERS, racks));
        assertEquals(expectedGame, game);

        Event event =
            new TookBackEvent(expectedGame, new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testTakeBackEarly() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        assertFalse(state.canExecute(
            "player1", new TakeBackCommand(new Transfer(0, 0, 0))));
    }

    public void testTakeBackWrongPlayer() {
        final Transfer transfer = new Transfer(0, 0, 0);
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE,
            new TransferSet(transfer));
        assertFalse(
            state.canExecute("player2", new TakeBackCommand(transfer)));
    }

    public void testTakeBackWrongPosition() {
        final int rackPosition = 0;
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE,
            new TransferSet(rackPosition, 0, 0));
        assertFalse(state.canExecute(
            "player1", new TakeBackCommand(new Transfer(rackPosition, 1, 0))));
    }

    public void testFinish() {
        ListenableGame game =
            new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO));
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new FinishCommand();
        client1.execute(command);

        Game expectedGame = new GameImpl(new ApprovingState(TWO_PLAYERS,
            AAAAAAA_EEEEEEE, MOVE_TWO));
        assertEquals(expectedGame, game);

        Event event =
            new FinishedEvent(expectedGame, new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testFinishEarly() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        assertFalse(state.canExecute("player1", new FinishCommand()));
    }

    public void testFinishWrongPlayer() {
        MovingState state =
            new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO);
        assertFalse(state.canExecute("player2", new FinishCommand()));
    }

    public void testFinishBadBoard() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE,
            new TransferSet(0, 0, 0));
        assertFalse(state.canExecute("player1", new FinishCommand()));
    }

    public void testExchange1() {
        MovingState state =
            new MovingState(TWO_PLAYERS, new String[] { "QNNNNNN", EEEEEEE });
        state.setBoxLid(new BoxLid("Z"));
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        final int[] tilesToExchange = new int[] { 0 };
        final Command command = new ExchangeCommand(tilesToExchange);
        client1.execute(command);

        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(0);
        MovingState expectedState = new MovingState(TWO_PLAYERS,
            new String[] { "ZNNNNNN", EEEEEEE }, new Board(), expectedScores, 1);
        expectedState.setBoxLid(new BoxLid("Q"));
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event =
            new ExchangedEvent(expectedGame, new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testExchange7() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        state.setBoxLid(new BoxLid("OOOOOOO"));
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        final int[] tilesToExchange = new int[] { 0, 1, 2, 3, 4, 5, 6 };
        final Command command = new ExchangeCommand(tilesToExchange);
        client1.execute(command);

        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(0);
        MovingState expectedState = new MovingState(TWO_PLAYERS,
            new String[] { "OOOOOOO", EEEEEEE }, new Board(), expectedScores, 1);
        expectedState.setBoxLid(new BoxLid("AAAAAAA"));
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event =
            new ExchangedEvent(expectedGame, new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testExchangeAfterTransfer() {
        MovingState state = new MovingState(TWO_PLAYERS,
            new String[] { "QZNNNNN", EEEEEEE }, new TransferSet(0, 0, 0));
        state.setBoxLid(new BoxLid("X"));
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        final int[] tilesToExchange = new int[] { 1 };
        final Command command = new ExchangeCommand(tilesToExchange);
        client1.execute(command);

        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(0);
        MovingState expectedState = new MovingState(TWO_PLAYERS,
            new String[] { "QXNNNNN", EEEEEEE }, new Board(), expectedScores, 1);
        expectedState.setBoxLid(new BoxLid("Z"));
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event =
            new ExchangedEvent(expectedGame, new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testPass() {
        ListenableGame game =
            new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new PassCommand();
        client1.execute(command);

        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(0);
        MovingState expectedState = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE,
            new Board(), expectedScores, 1);
        expectedState.setPasses(CollectionUtil.asStickySet("player1"));
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event =
            new PassedEvent(expectedGame, new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testPassAfterTransfer() {
        final String[] racks = { "QNNNNNN", EEEEEEE };
        ListenableGame game = new GameImpl(
            new MovingState(TWO_PLAYERS, racks, new TransferSet(0, 0, 0)));
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new PassCommand();
        client1.execute(command);

        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(0);
        MovingState expectedState =
            new MovingState(TWO_PLAYERS, racks, new Board(), expectedScores, 1);
        expectedState.setPasses(CollectionUtil.asStickySet("player1"));
        Game expectedGame = new GameImpl(expectedState);
        assertEquals(expectedGame, game);

        Event event =
            new PassedEvent(expectedGame, new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testPassWrongPlayer() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        assertFalse(state.canExecute("player2", new PassCommand()));
    }

    public void testEndGame() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        state.setPasses(CollectionUtil.asStickySet("player2"));
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new EndGameCommand();
        client1.execute(command);

        Game expectedGame =
            new GameImpl(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        assertEquals(expectedGame, game);

        Event event = new EndedGameEvent(expectedGame,
            new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testEndGameAfterTransferring() {
        final String[] racks = { "QNNNNNN", AAAAAAA };
        MovingState state =
            new MovingState(TWO_PLAYERS, racks, new TransferSet(0, 0, 0));
        state.setPasses(CollectionUtil.asStickySet("player2"));
        ListenableGame game = new GameImpl(state);
        TestClient client1 = new TestClient(game, "player1");
        final Command command = new EndGameCommand();
        client1.execute(command);

        Game expectedGame = new GameImpl(new EndedState(TWO_PLAYERS, racks));
        assertEquals(expectedGame, game);

        Event event = new EndedGameEvent(expectedGame,
            new Request("player1", command));
        assertEquals(CollectionUtil.asList(event), client1.getEvents());

    }

    public void testEndGameEarly1() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        assertFalse(state.canExecute("player1", new EndGameCommand()));
    }

    public void testEndGameEarly2() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        assertFalse(state.canExecute("player2", new EndGameCommand()));
    }

    public void testEndGameEarly3() {
        MovingState state =
            new MovingState(THREE_PLAYERS, AAAAAAA_EEEEEEE_IIIIIII);
        state.setPasses(CollectionUtil.asStickySet("player3"));
        assertFalse(state.canExecute("player1", new EndGameCommand()));
    }

    public void testEndGameWrongPlayer() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        state.setPasses(new HashStickySet(Arrays.asList(TWO_PLAYERS)));
        assertFalse(state.canExecute("player2", new EndGameCommand()));
    }

}
