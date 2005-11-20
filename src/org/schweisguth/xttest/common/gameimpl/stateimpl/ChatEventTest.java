package org.schweisguth.xttest.common.gameimpl.stateimpl;

import org.schweisguth.xt.common.command.ChatCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.stateimpl.ChatEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class ChatEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new ChatCommand("Hi Grandma!"));
    private static final ChatEvent EVENT = new ChatEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new ChatEvent(GAME2, REQUEST));
        tester.addOther(new ChatEvent(GAME,
            new Request("player2", new ChatCommand("Hi Grandma!"))));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("ChatEvent(" + GAME_IMPL + ", " + REQUEST + ")", EVENT);
    }

    public void testToHTML() {
        assertEquals(format(ChatEvent.MESSAGE, "player1", "Hi Grandma!"),
            EVENT.toHTML());
    }

}
