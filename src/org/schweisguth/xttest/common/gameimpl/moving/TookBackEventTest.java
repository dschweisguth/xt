package org.schweisguth.xttest.common.gameimpl.moving;

import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.TakeBackCommand;
import org.schweisguth.xt.common.domain.Transfer;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.moving.TookBackEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class TookBackEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST = new Request("player1",
        new TakeBackCommand(new Transfer(0, 0, 0)));
    private static final TookBackEvent EVENT = new TookBackEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new TookBackEvent(GAME2, REQUEST));
        tester.addOther(new TookBackEvent(GAME,
            new Request("player2", new TakeBackCommand(new Transfer(0, 0, 0)))));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("TookBackEvent(" + GAME_IMPL + ", " + REQUEST + ")",
            EVENT);
    }

    public void testToHTML() {
        assertEquals("", EVENT.toHTML());
    }

}
