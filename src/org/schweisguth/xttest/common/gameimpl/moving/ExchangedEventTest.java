package org.schweisguth.xttest.common.gameimpl.moving;

import org.schweisguth.xt.common.command.ExchangeCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.moving.ExchangedEvent;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class ExchangedEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new ExchangeCommand(new int[] { 0 }));
    private static final ExchangedEvent EVENT =
        new ExchangedEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new ExchangedEvent(GAME2, REQUEST));
        tester.addOther(new ExchangedEvent(GAME,
            new Request("player2", new ExchangeCommand(new int[] { 1 }))));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("ExchangedEvent(" + GAME_IMPL + ", " + REQUEST + ")",
            EVENT);
    }

    public void testExchanged1() {
        testExchanged(1, ExchangedEvent.TILE);
    }

    public void testExchanged2() {
        testExchanged(2, ExchangedEvent.TILES);
    }

    private void testExchanged(int pTileCount, String pTiles) {
        assertEquals(
            format(ExchangedEvent.EXCHANGED, "player1", pTileCount, pTiles),
            ExchangedEvent.exchanged("player1", pTileCount));
    }

    public void testToHTML1() {
        Game game = new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        assertEquals(
            ExchangedEvent.exchanged("player2", 1) +
                format(ExchangedEvent.ITS_YOUR_TURN, "player1"),
            new ExchangedEvent(game, new Request("player2",
                new ExchangeCommand(new int[] { 0 }))).toHTML());
    }

    public void testToHTML2() {
        Game game = new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        assertEquals(
            ExchangedEvent.exchanged("player2", 2) +
                format(ExchangedEvent.ITS_YOUR_TURN, "player1"),
            new ExchangedEvent(game, new Request("player2",
                new ExchangeCommand(new int[] { 0, 1 }))).toHTML());
    }

}
