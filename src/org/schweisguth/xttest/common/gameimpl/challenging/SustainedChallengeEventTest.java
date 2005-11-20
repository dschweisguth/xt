package org.schweisguth.xttest.common.gameimpl.challenging;

import org.schweisguth.xt.common.command.ApproveCommand;
import org.schweisguth.xt.common.command.SustainChallengeCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.approving.ApprovedEvent;
import org.schweisguth.xt.common.gameimpl.challenging.SustainedChallengeEvent;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class SustainedChallengeEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST2 =
        new Request("player2", new SustainChallengeCommand());
    private static final SustainedChallengeEvent EVENT2 =
        new SustainedChallengeEvent(GAME, REQUEST2, "player1");

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new ApprovedEvent(GAME,
            new Request("player2", new ApproveCommand()), "player1"));
        tester.addOther(new SustainedChallengeEvent(GAME2, REQUEST2, "player1"));
        tester.addOther(new SustainedChallengeEvent(GAME,
            new Request("player3", new SustainChallengeCommand()), "player1"));
        tester.addOther(new SustainedChallengeEvent(GAME, REQUEST2, "player3"));
        tester.doAssert(EVENT2);
    }

    public void testToString() {
        assertMatches(
            "SustainedChallengeEvent(" + GAME_IMPL + ", " + REQUEST2 +
                ", player1)",
            EVENT2);
    }

    public void testToHTML() {
        Game game =
            new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, 1));
        assertEquals(
            format(SustainedChallengeEvent.SUSTAINED, "player2", "player1") +
                format(SustainedChallengeEvent.ITS_YOUR_TURN, "player2"),
            new SustainedChallengeEvent(game, REQUEST2, "player1").toHTML());
    }

}
