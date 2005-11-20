package org.schweisguth.xttest.common.gameimpl.drawingstartingtiles;

import org.schweisguth.xt.common.command.DrawStartingTilesCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingstartingtiles.DrawingStartingTilesState;
import org.schweisguth.xt.common.gameimpl.drawingstartingtiles.DrewStartingTilesEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class DrewStartingTilesEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player2", new DrawStartingTilesCommand());
    private static final DrewStartingTilesEvent EVENT =
        new DrewStartingTilesEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player2", new JoinCommand())));
        tester.addOther(new DrewStartingTilesEvent(GAME2, REQUEST));
        tester.addOther(new DrewStartingTilesEvent(GAME,
            new Request("player1", new DrawStartingTilesCommand())));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches(
            "DrewStartingTilesEvent(" + GAME_IMPL + ", " + REQUEST + ")", EVENT);
    }

    public void testToHTMLDrawingStartingTiles() {
        Game game = new GameImpl(new DrawingStartingTilesState(TWO_PLAYERS));
        assertEquals(
            format(DrewStartingTilesEvent.DREW_STARTING_TILES, "player2") +
                format(DrewStartingTilesEvent.DRAW_YOUR_STARTING_TILES, "player1"),
            new DrewStartingTilesEvent(game, REQUEST).toHTML());
    }

    public void testToHTMLMoving() {
        Game game = new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        assertEquals(
            format(DrewStartingTilesEvent.DREW_STARTING_TILES, "player2") +
                format(DrewStartingTilesEvent.ITS_YOUR_TURN, "player1"),
            new DrewStartingTilesEvent(game, REQUEST).toHTML());
    }

}
