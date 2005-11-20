package org.schweisguth.xttest.common.gameimpl.drawingforfirst;

import java.util.HashMap;
import java.util.Map;
import org.schweisguth.xt.common.command.DrawForFirstCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingforfirst.DrawingForFirstState;
import org.schweisguth.xt.common.gameimpl.drawingforfirst.DrewForFirstEvent;
import org.schweisguth.xt.common.gameimpl.drawingstartingtiles.DrawingStartingTilesState;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class DrewForFirstEventTest extends BaseEventTest {
    // Constants
    private static final Request REQUEST =
        new Request("player1", new DrawForFirstCommand());
    private static final DrewForFirstEvent EVENT =
        new DrewForFirstEvent(GAME, REQUEST, Tile.get('A'));

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new DrewForFirstEvent(GAME2, REQUEST, Tile.get('A')));
        tester.addOther(new DrewForFirstEvent(GAME,
            new Request("player2", new DrawForFirstCommand()), Tile.get('A')));
        tester.addOther(new DrewForFirstEvent(GAME, REQUEST, Tile.get('B')));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches(
            "DrewForFirstEvent(" + GAME_IMPL + ", " + REQUEST + ", A)", EVENT);
    }

    public void testToHTMLDrawingForFirstMidRound() {
        Map tilesDrawnForFirst = new HashMap();
        tilesDrawnForFirst.put("player1", Tile.get('A'));
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        Game game = new GameImpl(state);
        assertEquals(format(DrewForFirstEvent.DREW, "player1", "A"),
            new DrewForFirstEvent(game, REQUEST, Tile.get('A')).toHTML());
    }

    public void testToHTMLDrawingForFirstNewRound() {
        Map tilesDrawnForFirst = new HashMap();
        tilesDrawnForFirst.put("player1", null);
        tilesDrawnForFirst.put("player2", null);
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        Game game = new GameImpl(state);
        assertEquals(
            format(DrewForFirstEvent.DREW, "player1", "A") +
                format(DrewForFirstEvent.TIED_DRAWING_FOR_FIRST,
                    "player1 and player2"),
            new DrewForFirstEvent(game, REQUEST, Tile.get('A')).toHTML());
    }

    public void testToHTMLDrawingStartingTiles() {
        Game game = new GameImpl(new DrawingStartingTilesState(TWO_PLAYERS));
        assertEquals(
            format(DrewForFirstEvent.DREW, "player1", "A") +
                format(DrewForFirstEvent.GOES_FIRST, "player1") +
                format(DrewForFirstEvent.DRAW_YOUR_STARTING_TILES, "player1"),
            new DrewForFirstEvent(game, REQUEST, Tile.get('A')).toHTML());
    }

}
