package org.schweisguth.xttest.common.gameimpl.joining;

import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.StartCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingforfirst.DrawingForFirstState;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.joining.StartedEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class StartedEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new StartCommand());
    private static final StartedEvent EVENT = new StartedEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new StartedEvent(GAME2, REQUEST));
        tester.addOther(
            new StartedEvent(GAME, new Request("player2", new StartCommand())));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("StartedEvent(" + GAME_IMPL + ", " + REQUEST + ")", EVENT);
    }

    public void testToHTML() {
        Game game = new GameImpl(new DrawingForFirstState(TWO_PLAYERS));
        assertEquals(format(StartedEvent.STARTED, "player1"),
            new StartedEvent(game, REQUEST).toHTML());
    }

}
