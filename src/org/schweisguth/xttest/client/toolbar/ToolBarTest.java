package org.schweisguth.xttest.client.toolbar;

import org.schweisguth.xt.client.toolbar.ToolBarController;
import org.schweisguth.xt.client.toolbar.ToolBarView;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.approving.ApprovingState;
import org.schweisguth.xt.common.gameimpl.challenging.ChallengingState;
import org.schweisguth.xt.common.gameimpl.drawingforfirst.DrawingForFirstState;
import org.schweisguth.xt.common.gameimpl.drawingnewtiles.DrawingNewTilesState;
import org.schweisguth.xt.common.gameimpl.drawingstartingtiles.DrawingStartingTilesState;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.gameimpl.stateimpl.StateImpl;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.testutil.BaseTest;

public class ToolBarTest extends BaseTest {
    public void testCreate() {
        ToolBarController controller = new ToolBarController(
            new LocalClient(new GameImpl(), "player1"), new MockSeatController());
        assertEquals(ToolBarView.NO_BUTTONS,
            controller.getToolBarView().getCard());
    }

    public void testJoining() {
        testRefresh(new JoiningState(), ToolBarView.JOINING);
    }

    public void testDrawingForFirst() {
        testRefresh(new DrawingForFirstState(TWO_PLAYERS),
            ToolBarView.NO_BUTTONS);
    }

    public void testDrawingStartingTiles() {
        testRefresh(new DrawingStartingTilesState(TWO_PLAYERS),
            ToolBarView.NO_BUTTONS);
    }

    public void testMoving() {
        testRefresh(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            ToolBarView.PLAYING);
    }

    public void testApproving() {
        testRefresh(new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO),
            ToolBarView.PLAYING);
    }

    public void testChallenging() {
        testRefresh(new ChallengingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO),
            ToolBarView.PLAYING);
    }

    public void testDrawingNewTiles() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(4);
        DrawingNewTilesState state = new DrawingNewTilesState(
            TWO_PLAYERS, AAAAAAA_EEEEEEE, scores, MOVE_TWO);
        testRefresh(state, ToolBarView.PLAYING);
    }

    public void testEnded() {
        testRefresh(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            ToolBarView.ENDED);
    }

    private static void testRefresh(StateImpl pState, String pCard) {
        ListenableGame game = new GameImpl(pState);
        LocalClient client = new LocalClient(game, "player1");
        ToolBarController controller =
            new ToolBarController(client, new MockSeatController());
        client.sendRefreshEvent();

        assertEquals(pCard, controller.getToolBarView().getCard());

    }

}
