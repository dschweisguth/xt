package org.schweisguth.xttest.common.gameimpl.approving;

import org.schweisguth.xt.common.command.ApproveCommand;
import org.schweisguth.xt.common.command.ChallengeCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.approving.ApprovedEvent;
import org.schweisguth.xt.common.gameimpl.approving.ApprovingState;
import org.schweisguth.xt.common.gameimpl.approving.ChallengedEvent;
import org.schweisguth.xt.common.gameimpl.drawingnewtiles.DrawingNewTilesState;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class ApprovedEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player2", new ApproveCommand());
    private static final ApprovedEvent EVENT =
        new ApprovedEvent(GAME, REQUEST, "player1");

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new ChallengedEvent(GAME,
            new Request("player2", new ChallengeCommand()), "player1"));
        tester.addOther(new ApprovedEvent(GAME2, REQUEST, "player1"));
        tester.addOther(new ApprovedEvent(GAME,
            new Request("player3", new ApproveCommand()), "player1"));
        tester.addOther(new ApprovedEvent(GAME, REQUEST, "player3"));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches(
            "ApprovedEvent(" + GAME_IMPL + ", " + REQUEST + ", player1)", EVENT);
    }

    public void testToHTMLApproving() {
        Game game = new GameImpl(
            new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO));
        assertEquals(format(ApprovedEvent.APPROVED, "player2", "player1"),
            new ApprovedEvent(game, REQUEST, "player1").toHTML());
    }

    public void testToHTMLDrawingNewTiles() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        Game game = new GameImpl(new DrawingNewTilesState(
            TWO_PLAYERS, AAAAAAA_EEEEEEE, scores, MOVE_TWO));
        assertEquals(
            format(ApprovedEvent.APPROVED, "player2", "player1") +
                format(ApprovedEvent.SCORED, "player1", 1, 1) +
                format(ApprovedEvent.DRAW_YOUR_NEW_TILES, "player1"),
            new ApprovedEvent(game, REQUEST, "player1").toHTML());
    }

    public void testToHTMLMoving() {
        Board board = new Board();
        board.place(new Rack("AAAAAAA"), MOVE_TWO);
        board.approve();
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        Game game = new GameImpl(
            new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, board, scores, 1));
        assertEquals(
            format(ApprovedEvent.APPROVED, "player2", "player1") +
                format(ApprovedEvent.SCORED, "player1", 1, 1) +
                ApprovedEvent.NO_MORE_TILES +
                format(ApprovedEvent.ITS_YOUR_TURN, "player2"),
            new ApprovedEvent(game, REQUEST, "player1").toHTML());
    }

    public void testToHTMLEndedWin() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(7);
        scores.endGame();
        scores.incrementScore(7);
        scores.incrementScore(-7);
        Game game = new GameImpl(
            new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE, scores, MOVE_SEVEN));
        assertEquals(
            format(ApprovedEvent.APPROVED, "player2", "player1") +
                format(ApprovedEvent.SCORED, "player1", 7, 7) +
                format(ApprovedEvent.USED_ALL, "player1") +
                format(ApprovedEvent.STILL_HAS, "player2",
                    "E, E, E, E, E, E and E") +
                format(ApprovedEvent.BONUS, "player1", 7, 14) +
                format(ApprovedEvent.WINS, "player1", 14),
            new ApprovedEvent(game, REQUEST, "player1").toHTML());
    }

    public void testToHTMLEndedTie() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS, 1);
        scores.incrementScore(21);
        scores.incrementScore(7);
        scores.endGame();
        scores.incrementScore(7);
        scores.incrementScore(-7);
        Game game = new GameImpl(
            new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE, scores, MOVE_SEVEN));
        assertEquals(
            format(ApprovedEvent.APPROVED, "player2", "player1") +
                format(ApprovedEvent.SCORED, "player1", 7, 7) +
                format(ApprovedEvent.USED_ALL, "player1") +
                format(ApprovedEvent.STILL_HAS, "player2",
                    "E, E, E, E, E, E and E") +
                format(ApprovedEvent.BONUS, "player1", 7, 14) +
                format(ApprovedEvent.TIED_GAME, "player1 and player2", 14),
            new ApprovedEvent(game, REQUEST, "player1").toHTML());
    }

}
