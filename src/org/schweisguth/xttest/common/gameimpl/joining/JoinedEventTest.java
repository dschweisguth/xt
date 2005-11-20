package org.schweisguth.xttest.common.gameimpl.joining;

import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.StartCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.gameimpl.joining.StartedEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class JoinedEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new JoinCommand());
    private static final JoinedEvent EVENT = new JoinedEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new StartedEvent(GAME, new Request("player1", new StartCommand())));
        tester.addOther(new JoinedEvent(GAME2, REQUEST));
        tester.addOther(
            new JoinedEvent(GAME, new Request("player2", new JoinCommand())));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("JoinedEvent(" + GAME_IMPL + ", " + REQUEST + ")", EVENT);
    }

    public void testToHTML1() {
        Game game = new GameImpl(new JoiningState(new String[] { "player1" }));
        assertEquals("player1 joined the game.",
            new JoinedEvent(game, REQUEST).toHTML());
    }

    public void testToHTML2() {
        Game game = new GameImpl(new JoiningState(TWO_PLAYERS));
        assertEquals(
            format(JoinedEvent.JOINED, "player1") + JoinedEvent.START_OR_WAIT,
            new JoinedEvent(game, REQUEST).toHTML());
    }

}
