package org.schweisguth.xttest.common.gameimpl.drawingnewtiles;

import org.schweisguth.xt.common.command.DrawNewTilesCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingnewtiles.DrewNewTilesEvent;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class DrewNewTilesEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player2", new DrawNewTilesCommand());
    private static final DrewNewTilesEvent EVENT =
        new DrewNewTilesEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player2", new JoinCommand())));
        tester.addOther(new DrewNewTilesEvent(GAME2, REQUEST));
        tester.addOther(new DrewNewTilesEvent(GAME,
            new Request("player1", new DrawNewTilesCommand())));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches("DrewNewTilesEvent(" + GAME_IMPL + ", " + REQUEST + ")",
            EVENT);
    }

    public void testToHTMLNonEmptyBoxLid() {
        Game game = new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        assertEquals(
            format(DrewNewTilesEvent.DREW_NEW_TILES, "player2") +
                format(DrewNewTilesEvent.ITS_YOUR_TURN, "player1"),
            new DrewNewTilesEvent(game, REQUEST).toHTML());
    }

    public void testToHTMLEmptyBoxLid() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        state.setBoxLid(new BoxLid(""));
        Game game = new GameImpl(state);
        assertEquals(
            format(DrewNewTilesEvent.DREW_NEW_TILES, "player2") +
                DrewNewTilesEvent.NO_MORE_TILES +
                format(DrewNewTilesEvent.ITS_YOUR_TURN, "player1"),
            new DrewNewTilesEvent(game, REQUEST).toHTML());
    }

}
