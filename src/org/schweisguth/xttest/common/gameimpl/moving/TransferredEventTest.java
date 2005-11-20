package org.schweisguth.xttest.common.gameimpl.moving;

import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.TransferCommand;
import org.schweisguth.xt.common.domain.Transfer;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.hastransferset.TransferredEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class TransferredEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new TransferCommand(new Transfer(0, 0, 0)));
    private static final TransferredEvent EVENT =
        new TransferredEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new TransferredEvent(GAME2, REQUEST));
        tester.addOther(new TransferredEvent(GAME,
            new Request("player2", new TransferCommand(new Transfer(0, 0, 0)))));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("TransferredEvent(" + GAME_IMPL + ", " + REQUEST + ")",
            EVENT);
    }

    public void testToHTML() {
        assertEquals("", EVENT.toHTML());
    }

}
