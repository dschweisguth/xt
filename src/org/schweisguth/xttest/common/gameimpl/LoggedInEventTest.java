package org.schweisguth.xttest.common.gameimpl;

import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.LogInCommand;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.LoggedInEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class LoggedInEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new LogInCommand());
    private static final LoggedInEvent EVENT = new LoggedInEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new LoggedInEvent(GAME2, REQUEST));
        tester.addOther(new LoggedInEvent(GAME,
            new Request("player2", new LogInCommand())));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("LoggedInEvent(" + GAME_IMPL + ", " + REQUEST + ")",
            EVENT);
    }

    public void testToHTML() {
        assertEquals(format(LoggedInEvent.LOGGED_IN, "player1"), EVENT.toHTML());
    }

}
