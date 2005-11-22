package org.schweisguth.xttest.common.gameimpl;

import org.schweisguth.xt.common.command.GoOffLineCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.WentOffLineEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class WentOffLineEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new GoOffLineCommand());
    private static final WentOffLineEvent EVENT =
        new WentOffLineEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new WentOffLineEvent(GAME2, REQUEST));
        tester.addOther(new WentOffLineEvent(GAME,
            new Request("player2", new GoOffLineCommand())));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("WentOffLineEvent(" + GAME_IMPL + ", " + REQUEST + ")",
            EVENT);
    }

    public void testToHTML() {
        assertEquals(format(WentOffLineEvent.WENT_OFF_LINE, "player1"),
            EVENT.toHTML());
    }

}
