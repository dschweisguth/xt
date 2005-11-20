package org.schweisguth.xttest.common.gameimpl.hascurrentplayer;

import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.RearrangeRackCommand;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.hascurrentplayer.RearrangedRackEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class RearrangedRackEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new RearrangeRackCommand(0, 1));
    private static final RearrangedRackEvent EVENT =
        new RearrangedRackEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new RearrangedRackEvent(GAME2, REQUEST));
        tester.addOther(new RearrangedRackEvent(GAME,
            new Request("player2", new RearrangeRackCommand(0, 1))));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches(
            "RearrangedRackEvent(" + GAME_IMPL + ", " + REQUEST + ")", EVENT);
    }

    public void testToHTML() {
        assertEquals("", EVENT.toHTML());
    }

}
