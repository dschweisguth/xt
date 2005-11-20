package org.schweisguth.xttest.common.gameimpl.ended;

import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.StartNewGameCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.ended.StartedNewGameEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class StartedNewGameEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new StartNewGameCommand());
    private static final StartedNewGameEvent EVENT =
        new StartedNewGameEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new StartedNewGameEvent(GAME2, REQUEST));
        tester.addOther(new StartedNewGameEvent(GAME,
            new Request("player2", new StartNewGameCommand())));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches(
            "StartedNewGameEvent(" + GAME_IMPL + ", " + REQUEST + ")", EVENT);
    }

    public void testToHTML() {
        Game game = new GameImpl();
        assertEquals(format(StartedNewGameEvent.STARTED, "player1"),
            new StartedNewGameEvent(game, REQUEST).toHTML());
    }

}
