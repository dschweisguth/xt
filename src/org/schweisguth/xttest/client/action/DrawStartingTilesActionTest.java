package org.schweisguth.xttest.client.action;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.DrawStartingTilesCommand;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingforfirst.DrawingForFirstState;
import org.schweisguth.xt.common.gameimpl.drawingstartingtiles.DrawingStartingTilesState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;

public class DrawStartingTilesActionTest extends BaseActionTest {
    protected Command createCommand() {
        return new DrawStartingTilesCommand();
    }

    public void testEarly() {
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        Map tilesDrawnForFirst = new HashMap();
        tilesDrawnForFirst.put("player1", Tile.get('A'));
        tilesDrawnForFirst.put("player2", null);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        assertEnabled(state, false, false, false);
    }

    public void testBefore() {
        assertEnabled(new DrawingStartingTilesState(TWO_PLAYERS),
            true, false, false);
    }

    public void testAfter1() {
        assertEnabled(
            new DrawingStartingTilesState(TWO_PLAYERS,
                new String[] { "", EEEEEEE }),
            true, false, false);
    }

    public void testAfter2() {
        assertEnabled(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            false, false, false);
    }

    public void testExecute1() throws RemoteException {
        ListenableGame game = new GameImpl(new DrawingStartingTilesState(TWO_PLAYERS));
        CommandAction action1 = createAction(game, "player1");
        Action action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action1.execute();

        assertFalse(action1.isEnabled());
        assertTrue(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

    public void testExecute2() throws RemoteException {
        ListenableGame game = new GameImpl(new DrawingStartingTilesState(
            TWO_PLAYERS, new String[] { "", EEEEEEE }));
        CommandAction action1 = createAction(game, "player1");
        Action action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action1.execute();

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

}
