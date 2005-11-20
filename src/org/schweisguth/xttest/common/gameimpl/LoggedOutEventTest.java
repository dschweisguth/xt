package org.schweisguth.xttest.common.gameimpl;

import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.LogOutCommand;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.LoggedOutEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class LoggedOutEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new LogOutCommand());
    private static final LoggedOutEvent EVENT =
        new LoggedOutEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new LoggedOutEvent(GAME2, REQUEST));
        tester.addOther(new LoggedOutEvent(GAME,
            new Request("player2", new LogOutCommand())));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("LoggedOutEvent(" + GAME_IMPL + ", " + REQUEST + ")",
            EVENT);
    }

    public void testToHTML() {
        assertEquals(format(LoggedOutEvent.LOGGED_OUT, "player1"),
            EVENT.toHTML());
    }

}
