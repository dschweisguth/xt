package org.schweisguth.xttest.client.toolbar;

import java.rmi.RemoteException;
import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.EndGameCommand;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xttest.client.action.BaseActionTest;

public class EndGameActionTest extends BaseActionTest {
    protected Command createCommand() {
        return new EndGameCommand();
    }

    public void testEarly() {
        assertEnabled(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            false, false, false);
    }

    public void testBefore() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        state.setPasses(CollectionUtil.asStickySet("player2"));
        assertEnabled(state, true, false, false);
    }

    public void testAfter() {
        assertEnabled(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            false, false, false);
    }

    public void testExecute() throws RemoteException {
        // Note that we're testing a plain CommandAction, not an EndGameAction,
        // which would raise a confirmation dialog.
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        state.setPasses(CollectionUtil.asStickySet("player2"));
        ListenableGame game = new GameImpl(state);
        CommandAction action1 = createAction(game, "player1");
        Action action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action1.execute();

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

}
