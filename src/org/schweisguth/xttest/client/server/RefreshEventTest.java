package org.schweisguth.xttest.client.server;

import org.schweisguth.xt.client.server.RefreshEvent;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class RefreshEventTest extends BaseEventTest {
    // Constants
    private static final RefreshEvent EVENT1 = new RefreshEvent(GAME);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new RefreshEvent(GAME2));
        tester.doAssert(EVENT1);
    }

    public void testToString() {
        assertMatches("RefreshEvent(" + GAME_IMPL + ")", EVENT1);
    }

}
