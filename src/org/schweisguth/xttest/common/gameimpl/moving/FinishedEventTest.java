package org.schweisguth.xttest.common.gameimpl.moving;

import org.schweisguth.xt.common.command.FinishCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.approving.ApprovingState;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.moving.FinishedEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class FinishedEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new FinishCommand());
    private static final FinishedEvent EVENT = new FinishedEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new FinishedEvent(GAME2, REQUEST));
        tester.addOther(
            new FinishedEvent(GAME, new Request("player2", new FinishCommand())));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("FinishedEvent(" + GAME_IMPL + ", " + REQUEST + ")",
            EVENT);
    }

    public void testToHTML() {
        Game game = new GameImpl(
            new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO));
        assertEquals(format(FinishedEvent.FINISHED, "player1"),
            new FinishedEvent(game, REQUEST).toHTML());
    }

}
