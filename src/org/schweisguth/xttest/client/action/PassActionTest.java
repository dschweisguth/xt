package org.schweisguth.xttest.client.action;

import java.rmi.RemoteException;
import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.PassCommand;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingstartingtiles.DrawingStartingTilesState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.util.collection.CollectionUtil;

public class PassActionTest extends BaseActionTest {
    protected Command createCommand() {
        return new PassCommand();
    }

    public void testEarly() {
        assertEnabled(
            new DrawingStartingTilesState(TWO_PLAYERS,
                new String[] { "", EEEEEEE }),
            false, false, false);
    }

    public void testBefore() {
        assertEnabled(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            true, false, false);
    }

    public void testAfter() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        state.setPasses(CollectionUtil.asStickySet("player2"));
        assertEnabled(state, true, false, false);
    }

    public void testExecute() throws RemoteException {
        ListenableGame game =
            new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        CommandAction action1 = createAction(game, "player1");
        Action action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action1.execute();

        assertFalse(action1.isEnabled());
        assertTrue(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

}
