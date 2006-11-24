package org.schweisguth.xttest.common.gameimpl.moving;

import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.TakeBackAllCommand;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.moving.TookBackAllEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class TookBackAllEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new TakeBackAllCommand());
    private static final TookBackAllEvent EVENT =
        new TookBackAllEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new TookBackAllEvent(GAME2, REQUEST));
        tester.addOther(new TookBackAllEvent(GAME,
            new Request("player2", new TakeBackAllCommand())));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("TookBackAllEvent(" + GAME_IMPL + ", " + REQUEST + ")",
            EVENT);
    }

    public void testToHTML() {
        assertEquals("", EVENT.toHTML());
    }

}
