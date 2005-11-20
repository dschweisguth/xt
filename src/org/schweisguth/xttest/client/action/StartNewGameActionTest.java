package org.schweisguth.xttest.client.action;

import java.rmi.RemoteException;
import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.StartNewGameCommand;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.util.collection.CollectionUtil;

public class StartNewGameActionTest extends BaseActionTest {
    protected Command createCommand() {
        return new StartNewGameCommand();
    }

    public void testEarly() {
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        state.setPasses(CollectionUtil.asStickySet("player1"));
        assertEnabled(state, false, false, false);
    }

    public void testBefore() {
        assertEnabled(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            true, true, true);
    }

    public void testAfter() {
        assertEnabled(new JoiningState(), false);
    }

    public void testExecute() throws RemoteException {
        ListenableGame game =
            new GameImpl(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        CommandAction action1 = createAction(game, "player1");
        Action action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action1.execute();

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

}
