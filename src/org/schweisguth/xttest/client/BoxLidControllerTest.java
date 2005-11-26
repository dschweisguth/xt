package org.schweisguth.xttest.client;

import org.schweisguth.xt.client.boxlid.BoxLidController;
import org.schweisguth.xt.client.boxlid.BoxLidView;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.ScoreSheet;
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

public class BoxLidControllerTest extends BaseTest {
    public void testCreate() {
        BoxLidController controller =
            new BoxLidController(new LocalClient(new GameImpl(), "player1"));

        BoxLidView view = controller.getBoxLidView();
        assertEquals(BoxLidView.NO_BUTTON, view.getCard());
        assertEquals(BoxLid.getMaxTileCount(), view.getModel().getTileCount());

    }

    public void testJoining() {
        testRefresh(new JoiningState(), BoxLidView.NO_BUTTON,
            BoxLid.getMaxTileCount());
    }

    public void testDrawingForFirst() {
        testRefresh(new DrawingForFirstState(TWO_PLAYERS),
            BoxLidView.DRAW_FOR_FIRST, BoxLid.getMaxTileCount());
    }

    public void testDrawingStartingTiles() {
        testRefresh(new DrawingStartingTilesState(TWO_PLAYERS),
            BoxLidView.DRAW_STARTING_TILES, BoxLid.getMaxTileCount());
    }

    public void testMoving() {
        testRefresh(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            BoxLidView.DRAW_NEW_TILES, BoxLid.getMaxTileCount() - 14);
    }

    public void testApproving() {
        testRefresh(new ApprovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO),
            BoxLidView.DRAW_NEW_TILES, BoxLid.getMaxTileCount() - 14);
    }

    public void testChallenging() {
        testRefresh(new ChallengingState(TWO_PLAYERS, AAAAAAA_EEEEEEE, MOVE_TWO),
            BoxLidView.DRAW_NEW_TILES, BoxLid.getMaxTileCount() - 14);
    }

    public void testDrawingNewTiles() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(4);
        DrawingNewTilesState state = new DrawingNewTilesState(
            TWO_PLAYERS, AAAAAAA_EEEEEEE, scores, MOVE_TWO);
        testRefresh(state, BoxLidView.DRAW_NEW_TILES,
            BoxLid.getMaxTileCount() - 14);
    }

    public void testEnded() {
        testRefresh(new EndedState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            BoxLidView.NO_BUTTON, BoxLid.getMaxTileCount() - 14);
    }

    private static void testRefresh(StateImpl pState, String pCard,
        int pTileCount) {
        LocalClient client = new LocalClient(new GameImpl(pState), "player1");
        BoxLidController controller = new BoxLidController(client);
        client.sendRefreshEvent();

        BoxLidView boxLidView = controller.getBoxLidView();
        assertEquals(pCard, boxLidView.getCard());
        assertEquals(pTileCount, boxLidView.getModel().getTileCount());

    }

}
