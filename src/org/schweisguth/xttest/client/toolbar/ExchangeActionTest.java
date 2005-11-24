package org.schweisguth.xttest.client.toolbar;

import java.rmi.RemoteException;
import javax.swing.Action;
import org.schweisguth.xt.client.seat.SeatController;
import org.schweisguth.xt.client.toolbar.ExchangeAction;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.drawingstartingtiles.DrawingStartingTilesState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.gameimpl.stateimpl.StateImpl;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.testutil.BaseTest;

public class ExchangeActionTest extends BaseTest {
    // Methods: test

    public void testEarlyNoSelection() {
        assertEnabled(new DrawingStartingTilesState(TWO_PLAYERS,
            new String[] { "", EEEEEEE }), new MockSeatController(),
            false, false, false);
    }

    public void testEarlyNoSelectionThenSelect() {
        ListenableGame game = new GameImpl(new DrawingStartingTilesState(
            TWO_PLAYERS, new String[] { "", EEEEEEE }));
        MockSeatController seatController = new MockSeatController();
        Action action1 = getAction(game, "player1", seatController);
        Action action2 = getAction(game, "player2", seatController);
        Action observerAction = getAction(game, "observer", seatController);
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { 0 });

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

    public void testEarlyHasSelection() {
        MockSeatController seatController = new MockSeatController();
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { 0 });
        assertEnabled(new DrawingStartingTilesState(TWO_PLAYERS,
            new String[] { "", EEEEEEE }), seatController, false, false,
            false);
    }

    public void testEarlyHasSelectionThenDeselect() {
        ListenableGame game = new GameImpl(new DrawingStartingTilesState(
            TWO_PLAYERS, new String[] { "", EEEEEEE }));
        MockSeatController seatController = new MockSeatController();
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { 0 });
        Action action1 = getAction(game, "player1", seatController);
        Action action2 = getAction(game, "player2", seatController);
        Action observerAction = getAction(game, "observer", seatController);
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { });

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

    public void testBeforeNoSelection() {
        assertEnabled(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            new MockSeatController(), false, false, false);
    }

    public void testBeforeNoSelectionThenSelect() {
        ListenableGame game =
            new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        MockSeatController seatController = new MockSeatController();
        Action action1 = getAction(game, "player1", seatController);
        Action action2 = getAction(game, "player2", seatController);
        Action observerAction = getAction(game, "observer", seatController);
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { 0 });

        assertTrue(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

    public void testBeforeHasSelection() {
        MockSeatController seatController = new MockSeatController();
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { 0 });
        assertEnabled(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE),
            seatController, true, false, false);
    }

    public void testBeforeHasSelectionThenDeselect() {
        ListenableGame game =
            new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        MockSeatController seatController = new MockSeatController();
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { 0 });
        Action action1 = getAction(game, "player1", seatController);
        Action action2 = getAction(game, "player2", seatController);
        Action observerAction = getAction(game, "observer", seatController);
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { });

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

    public void testBeforeHasSelectionEmptyBoxLid() {
        MockSeatController seatController = new MockSeatController();
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { 0 });
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        state.setBoxLid(new BoxLid(""));
        assertEnabled(state, seatController, false, false, false);
    }

    public void testBeforeHasSelectionTooFewTilesInBoxLid() {
        MockSeatController seatController = new MockSeatController();
        seatController.setClientPlayerRackViewSelectedColumns(
            new int[] { 0, 1 });
        MovingState state = new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE);
        state.setBoxLid(new BoxLid("A"));
        assertEnabled(state, seatController, false, false, false);
    }

    public void testAfter() {
        testBeforeNoSelection();
    }

    public void testAfterThenSelect() {
        testBeforeNoSelectionThenSelect();
    }

    public void testExecute() throws RemoteException {
        ListenableGame game =
            new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        MockSeatController seatController = new MockSeatController();
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { 0 });
        ExchangeAction action1 = getAction(game, "player1", seatController);
        Action action2 = getAction(game, "player2", seatController);
        Action observerAction = getAction(game, "observer", seatController);
        action1.execute();
        // In the real code, exchanging updates the UI and clears the selection
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { });

        assertFalse(action1.isEnabled());
        assertFalse(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

    public void testExecuteThenSelect() throws RemoteException {
        ListenableGame game =
            new GameImpl(new MovingState(TWO_PLAYERS, AAAAAAA_EEEEEEE));
        MockSeatController seatController = new MockSeatController();
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { 0 });
        ExchangeAction action1 = getAction(game, "player1", seatController);
        Action action2 = getAction(game, "player2", seatController);
        Action observerAction = getAction(game, "observer", seatController);
        action1.execute();
        // In the real code, exchanging updates the UI and clears the selection
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { });
        seatController.setClientPlayerRackViewSelectedColumns(new int[] { 0 });

        assertFalse(action1.isEnabled());
        assertTrue(action2.isEnabled());
        assertFalse(observerAction.isEnabled());

    }

    // Methods: helper

    private static void assertEnabled(StateImpl pState,
        MockSeatController pSeatController, boolean pPlayer1Enabled,
        boolean pPlayer2Enabled, boolean pObserverEnabled) {
        ListenableGame game = new GameImpl(pState);
        assertEnabled(game, "player1", pSeatController, pPlayer1Enabled);
        assertEnabled(game, "player2", pSeatController, pPlayer2Enabled);
        assertEnabled(game, "observer", pSeatController, pObserverEnabled);
    }

    private static void assertEnabled(ListenableGame pGame, String pPlayer,
        MockSeatController pSeatController, boolean pEnabled)
    {
        LocalClient client = new LocalClient(pGame, pPlayer);
        Action action = new ExchangeAction(client, pSeatController);
        client.sendRefreshEvent();
        assertEquals(pEnabled, action.isEnabled());
    }

    private static ExchangeAction getAction(ListenableGame pGame, String pPlayer,
        SeatController pSeatController) {
        return new ExchangeAction(new LocalClient(pGame, pPlayer),
            pSeatController);
    }

}
