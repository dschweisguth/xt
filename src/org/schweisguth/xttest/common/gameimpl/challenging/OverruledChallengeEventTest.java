package org.schweisguth.xttest.common.gameimpl.challenging;

import org.schweisguth.xt.common.command.ApproveCommand;
import org.schweisguth.xt.common.command.OverruleChallengeCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.approving.ApprovedEvent;
import org.schweisguth.xt.common.gameimpl.challenging.OverruledChallengeEvent;
import org.schweisguth.xt.common.gameimpl.drawingnewtiles.DrawingNewTilesState;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class OverruledChallengeEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player2", new OverruleChallengeCommand());
    private static final OverruledChallengeEvent EVENT =
        new OverruledChallengeEvent(GAME, REQUEST, "player1");

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new ApprovedEvent(GAME,
            new Request("player2", new ApproveCommand()), "player1"));
        tester.addOther(new OverruledChallengeEvent(GAME2, REQUEST, "player1"));
        tester.addOther(new OverruledChallengeEvent(GAME,
            new Request("player3", new OverruleChallengeCommand()), "player1"));
        tester.addOther(new OverruledChallengeEvent(GAME, REQUEST, "player3"));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches(
            "OverruledChallengeEvent(" + GAME_IMPL + ", " + REQUEST +
                ", player1)",
            EVENT);
    }

    public void testToHTMLDrawingNewTiles() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        Game game = new GameImpl(new DrawingNewTilesState(TWO_PLAYERS,
            AAAAAAA_EEEEEEE, scores, MOVE_TWO));
        assertEquals(
            format(OverruledChallengeEvent.APPROVED, "player2", "player1") +
                format(OverruledChallengeEvent.SCORED, "player1", 1, 1) +
                format(OverruledChallengeEvent.DRAW_YOUR_NEW_TILES, "player1"),
            new OverruledChallengeEvent(game, REQUEST, "player1").toHTML());
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
            format(OverruledChallengeEvent.APPROVED, "player2", "player1") +
                format(OverruledChallengeEvent.SCORED, "player1", 1, 1) +
                OverruledChallengeEvent.NO_MORE_TILES +
                format(OverruledChallengeEvent.ITS_YOUR_TURN, "player2"),
            new OverruledChallengeEvent(game, REQUEST, "player1").toHTML());
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
            format(OverruledChallengeEvent.APPROVED, "player2", "player1") +
                format(OverruledChallengeEvent.SCORED, "player1", 7, 7) +
                format(ApprovedEvent.USED_ALL, "player1") +
                format(ApprovedEvent.STILL_HAS, "player2",
                    "E, E, E, E, E, E and E") +
                format(OverruledChallengeEvent.BONUS, "player1", 7, 14) +
                format(OverruledChallengeEvent.WINS, "player1", 14),
            new OverruledChallengeEvent(game, REQUEST, "player1").toHTML());
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
            format(OverruledChallengeEvent.APPROVED, "player2", "player1") +
                format(OverruledChallengeEvent.SCORED, "player1", 7, 7) +
                format(ApprovedEvent.USED_ALL, "player1") +
                format(ApprovedEvent.STILL_HAS, "player2",
                    "E, E, E, E, E, E and E") +
                format(OverruledChallengeEvent.BONUS, "player1", 7, 14) +
                format(OverruledChallengeEvent.TIED_GAME, "player1 and player2", 14),
            new OverruledChallengeEvent(game, REQUEST, "player1").toHTML());
    }

}
