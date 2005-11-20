package org.schweisguth.xttest.client.action;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.DrawForFirstCommand;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingforfirst.DrawingForFirstState;
import org.schweisguth.xt.common.gameimpl.drawingstartingtiles.DrawingStartingTilesState;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;

public class DrawForFirstActionTest extends BaseActionTest {
    protected Command createCommand() {
        return new DrawForFirstCommand();
    }

    public void testEarly() {
        assertEnabled(new JoiningState(TWO_PLAYERS), false, false, false);
    }

    public void testBefore() {
        assertEnabled(new DrawingForFirstState(TWO_PLAYERS), true, true, false);
    }

    public void testAfter1() {
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        Map tilesDrawnForFirst = new HashMap();
        tilesDrawnForFirst.put("player1", Tile.get('A'));
        tilesDrawnForFirst.put("player2", null);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        assertEnabled(state, false, true, false);
    }

    public void testAfter2Same() {
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        Map tilesDrawnForFirst = new HashMap();
        tilesDrawnForFirst.put("player1", null);
        tilesDrawnForFirst.put("player2", null);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        assertEnabled(state, true, true, false);
    }

    public void testAfter2Different() {
        assertEnabled(new DrawingStartingTilesState(TWO_PLAYERS),
            false, false, false);
    }

    public void testAfter3() {
        DrawingForFirstState state = new DrawingForFirstState(THREE_PLAYERS);
        Map tilesDrawnForFirst = new HashMap();
        tilesDrawnForFirst.put("player1", null);
        tilesDrawnForFirst.put("player2", null);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        assertEnabled(state, true, true, false, false);
    }

    public void testExecute1() throws RemoteException {
        ListenableGame game = new GameImpl(new DrawingForFirstState(TWO_PLAYERS));
        CommandAction action1 = createAction(game, "player1");
        Action action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action1.execute();

        assertFalse(action1.isEnabled());
        assertTrue(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

    public void testExecute2Same() throws RemoteException {
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        Map tilesDrawnForFirst = new HashMap();
        tilesDrawnForFirst.put("player1", Tile.get('A'));
        tilesDrawnForFirst.put("player2", null);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        state.setBoxLid(new BoxLid("A"));
        ListenableGame game = new GameImpl(state);
        Action action1 = createAction(game, "player1");
        CommandAction action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action2.execute();

        assertTrue(action1.isEnabled());
        assertTrue(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

    public void testExecute2Different() throws RemoteException {
        DrawingForFirstState state = new DrawingForFirstState(TWO_PLAYERS);
        Map tilesDrawnForFirst = new HashMap();
        tilesDrawnForFirst.put("player1", Tile.get('A'));
        tilesDrawnForFirst.put("player2", null);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        state.setBoxLid(new BoxLid("B"));
        ListenableGame game = new GameImpl(state);
        Action action1 = createAction(game, "player1");
        CommandAction action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action2.execute();

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

    public void testExecute3() throws RemoteException {
        DrawingForFirstState state = new DrawingForFirstState(THREE_PLAYERS);
        Map tilesDrawnForFirst = new HashMap();
        tilesDrawnForFirst.put("player1", Tile.get('A'));
        tilesDrawnForFirst.put("player2", Tile.get('A'));
        tilesDrawnForFirst.put("player3", null);
        state.setTilesDrawnForFirst(tilesDrawnForFirst);
        state.setBoxLid(new BoxLid("B"));
        ListenableGame game = new GameImpl(state);
        Action action1 = createAction(game, "player1");
        Action action2 = createAction(game, "player2");
        CommandAction action3 = createAction(game, "player3");
        Action observerAction = createAction(game, "observer");
        action3.execute();

        assertTrue(action1.isEnabled());
        assertTrue(action2.isEnabled());
        assertFalse(action3.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

}
