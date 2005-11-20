package org.schweisguth.xttest.common.gameimpl.moving;

import org.schweisguth.xt.common.command.EndGameCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.approving.ApprovedEvent;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.moving.EndedGameEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class EndedGameEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new EndGameCommand());
    private static final EndedGameEvent EVENT =
        new EndedGameEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new EndedGameEvent(GAME2, REQUEST));
        tester.addOther(new EndedGameEvent(GAME,
            new Request("player2", new EndGameCommand())));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("EndedGameEvent(" + GAME_IMPL + ", " + REQUEST + ")",
            EVENT);
    }

    public void testToHTMLWin() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        scores.endGame();
        Game game = new GameImpl(
            new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE, scores, MOVE_TWO));
        assertEquals(
            format(EndedGameEvent.ENDED_GAME, "player1") +
                format(ApprovedEvent.STILL_HAS, "player1", "A, A, A, A and A") +
                format(ApprovedEvent.STILL_HAS, "player2",
                    "E, E, E, E, E, E and E") +
                format(EndedGameEvent.WINS, "player1", 1),
            new EndedGameEvent(game, REQUEST).toHTML());
    }

    public void testToHTMLTie() {
        Game game = new GameImpl(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        assertEquals(
            format(EndedGameEvent.ENDED_GAME, "player1") +
                format(ApprovedEvent.STILL_HAS, "player1",
                    "A, A, A, A, A, A and A") +
                format(ApprovedEvent.STILL_HAS, "player2",
                    "E, E, E, E, E, E and E") +
                format(EndedGameEvent.TIED_GAME, "player1 and player2", -7),
            new EndedGameEvent(game, REQUEST).toHTML());
    }

}
