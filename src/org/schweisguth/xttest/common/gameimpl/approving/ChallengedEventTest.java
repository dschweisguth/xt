package org.schweisguth.xttest.common.gameimpl.approving;

import org.schweisguth.xt.common.command.ApproveCommand;
import org.schweisguth.xt.common.command.ChallengeCommand;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.approving.ApprovedEvent;
import org.schweisguth.xt.common.gameimpl.approving.ChallengedEvent;
import org.schweisguth.xt.common.gameimpl.challenging.ChallengingState;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class ChallengedEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player2", new ChallengeCommand());
    private static final ChallengedEvent EVENT =
        new ChallengedEvent(GAME, REQUEST, "player1");

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new ApprovedEvent(GAME,
            new Request("player2", new ApproveCommand()), "player1"));
        tester.addOther(new ChallengedEvent(GAME2, REQUEST, "player1"));
        tester.addOther(new ChallengedEvent(GAME,
            new Request("player3", new ChallengeCommand()), "player1"));
        tester.addOther(new ChallengedEvent(GAME, REQUEST, "player3"));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches(
            "ChallengedEvent(" + GAME_IMPL + ", " + REQUEST + ", player1)",
            EVENT);
    }

    public void testToHTML() {
        Game game = new GameImpl(
            new ChallengingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO));
        Event event = new ChallengedEvent(game, REQUEST, "player1");
        assertEquals(format(ChallengedEvent.CHALLENGED, "player2", "player1"),
            event.toHTML());
    }

}
