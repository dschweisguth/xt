package org.schweisguth.xttest.common.gameimpl.moving;

import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.PassCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.gameimpl.moving.PassedEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class PassedEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player2", new PassCommand());
    private static final PassedEvent EVENT = new PassedEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player2", new JoinCommand())));
        tester.addOther(new PassedEvent(GAME2, REQUEST));
        tester.addOther(
            new PassedEvent(GAME, new Request("player1", new PassCommand())));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("PassedEvent(" + GAME_IMPL + ", " + REQUEST + ")", EVENT);
    }

    public void testToHTML() {
        Game game = new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        assertEquals(
            format(PassedEvent.PASSED, "player2") +
                format(PassedEvent.ITS_YOUR_TURN, "player1"),
            new PassedEvent(game, REQUEST).toHTML());
    }

}
