package org.schweisguth.xttest.client.action;

import java.rmi.RemoteException;
import javax.swing.Action;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.DrawNewTilesCommand;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.approving.ApprovingState;
import org.schweisguth.xt.common.gameimpl.drawingnewtiles.DrawingNewTilesState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;

public class DrawNewTilesActionTest extends BaseActionTest {
    protected Command createCommand() {
        return new DrawNewTilesCommand();
    }

    public void testEarly() {
        assertEnabled(new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO),
            false, false, false);
    }

    public void testBefore1() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        assertEnabled(
            new DrawingNewTilesState(TWO_PLAYERS, AAAAAAA_EEEEEEE, scores,
                MOVE_TWO),
            true, false, false);
    }

    public void testAfter() {
        assertEnabled(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            false, false, false);
    }

    public void testExecute() throws RemoteException {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        ListenableGame game = new GameImpl(new DrawingNewTilesState(
            TWO_PLAYERS, AAAAAAA_EEEEEEE, scores, MOVE_TWO));
        CommandAction action1 = createAction(game, "player1");
        Action action2 = createAction(game, "player2");
        Action observerAction = createAction(game, "observer");
        action1.execute();

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

}
