package org.schweisguth.xt.common.gameimpl;

import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import org.schweisguth.xt.common.command.GoOffLineCommand;
import org.schweisguth.xt.common.command.LogInCommand;
import org.schweisguth.xt.common.command.LogOutCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.ListenerManager;
import org.schweisguth.xt.common.game.ListenerOperations;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.approving.ApprovingState;
import org.schweisguth.xt.common.gameimpl.challenging.ChallengingState;
import org.schweisguth.xt.common.gameimpl.drawingforfirst.DrawingForFirstState;
import org.schweisguth.xt.common.gameimpl.drawingnewtiles.DrawingNewTilesState;
import org.schweisguth.xt.common.gameimpl.drawingstartingtiles.DrawingStartingTilesState;
import org.schweisguth.xt.common.gameimpl.ended.EndedState;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.gameimpl.moving.MovingState;
import org.schweisguth.xt.common.gameimpl.state.HasCurrentPlayerState;
import org.schweisguth.xt.common.gameimpl.state.HasTransferSetState;
import org.schweisguth.xt.common.gameimpl.state.State;
import org.schweisguth.xt.common.gameimpl.state.StateContext;
import org.schweisguth.xt.common.gameimpl.stateimpl.StateImpl;
import org.schweisguth.xt.common.util.collection.HashStickyMap;
import org.schweisguth.xt.common.util.collection.SetList;
import org.schweisguth.xt.common.util.collection.StickyMap;
import org.schweisguth.xt.common.util.contract.Assert;
import org.schweisguth.xt.common.util.logging.Logger;

public class GameImpl implements ListenableGame, StateContext {
    private static final long serialVersionUID = 281342796260416153L;

    // Fields
    private StateImpl mState;
    private transient ListenerManager mListenerManager = null;
    private transient StickyMap mListenersToPlayersMap = null;

    // Constructors

    public GameImpl() {
        setState(new JoiningState());
    }

    public GameImpl(StateImpl pState) {
        Assert.assertNotNull(pState);
        setState(pState);
    }

    // Methods: implements ListenableGame

    public String getState() {
        return mState.getName();
    }

    public boolean isIn(String pStateName) {
        Assert.assertNotNull(pStateName);
        return getState().equals(pStateName);
    }

    public SetList getPlayers() {
        return mState.getPlayers();
    }

    public Rack getRack(String pPlayer) {
        return mState.getRack(pPlayer);
    }

    public BoxLid getBoxLid() {
        return mState.getBoxLid();
    }

    public Board getBoard() {
        return mState.getBoard();
    }

    public ScoreSheet getScoreSheet() {
        return mState.getScoreSheet();
    }

    public boolean hasCurrentPlayer() {
        return mState instanceof HasCurrentPlayerState;
    }

    public String getCurrentPlayer() {
        return ((HasCurrentPlayerState) mState).getCurrentPlayer();
    }

    public SortedMap getTilesDrawnForFirst() {
        Assert.assertTrue(mState instanceof DrawingForFirstState);
        return ((DrawingForFirstState) mState).getTilesDrawnForFirst();
    }

    public boolean noPlayersHaveDrawnForFirstYetThisRound() {
        Assert.assertTrue(mState instanceof DrawingForFirstState);
        return ((DrawingForFirstState) mState).
            noPlayersHaveDrawnForFirstYetThisRound();
    }

    public boolean canExecute(Request pRequest) {
        return mState.canExecute(pRequest);
    }

    public void execute(Request pCommand) {
        mState.execute(pCommand);
    }

    public void addListener(String pPlayer, ListenerOperations pListener) {
        send(new LoggedInEvent(this,
            new Request(pPlayer, new LogInCommand())));
        getListenerManager().addListener(pListener);
        getListenersToPlayerMap().put(pListener, pPlayer);
        logCurrentListeners();
    }

    public void removeListener(ListenerOperations pListener) {
        getListenerManager().removeListener(pListener);
        String player = (String) getListenersToPlayerMap().remove(pListener);
        logCurrentListeners();
        send(new LoggedOutEvent(this,
            new Request(player, new LogOutCommand())));
    }

    private void logCurrentListeners() {
        String message = "Current listeners: ";
        for (Iterator listeners = getListenerManager().iterator();
            listeners.hasNext();) {
            Object listener = listeners.next();
            message += getListenersToPlayerMap().get(listener) + " " +
                listener + (listeners.hasNext() ? ", " : "");
        }
        Logger.global.fine(message);
    }

    // Methods: implements StateContext

    public Game getGame() {
        return this;
    }

    public void goToJoiningState() {
        setState(new JoiningState());
    }

    public void goToDrawingForFirstState(State pFrom) {
        setState(new DrawingForFirstState(pFrom));
    }

    public void goToDrawingStartingTilesState(State pFrom,
        int pCurrentPlayerIndex) {
        setState(new DrawingStartingTilesState(pFrom, pCurrentPlayerIndex));
    }

    public void goToMovingState(HasCurrentPlayerState pFrom) {
        setState(new MovingState(pFrom));
    }

    public void goToApprovingState(HasTransferSetState pFrom) {
        setState(new ApprovingState(pFrom));
    }

    public void goToChallengingState(HasTransferSetState pFrom) {
        setState(new ChallengingState(pFrom));
    }

    public void goToDrawingNewTilesState(HasCurrentPlayerState pFrom) {
        setState(new DrawingNewTilesState(pFrom));
    }

    public void goToEndedState(State pFrom) {
        setState(new EndedState(pFrom));
    }

    public void send(Event pEvent) {
        Assert.assertNotNull(pEvent);

        // Log the message
        String message = pEvent.toHTML();
        if (! message.equals("")) {
            Logger.global.info(message);
        }

        // Send the event
        long startTime = System.currentTimeMillis();
        Logger.global.fine("Sending event " + pEvent + " ...");
        List removedListeners = getListenerManager().send(pEvent);
        Logger.global.fine("Sent event " + pEvent + " in " +
            (System.currentTimeMillis() - startTime) + " ms.");

        // Remove removed listeners and send another event for each
        for (Iterator i = removedListeners.iterator(); i.hasNext();) {
            Object listener = i.next();
            String player =
                (String) getListenersToPlayerMap().remove(listener);
            Logger.global.fine("ListenerManager removed " + player + " " +
                listener + "; removed it from player-to-listener map.");
            send(new WentOffLineEvent(this,
                new Request(player, new GoOffLineCommand())));
        }

    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        return pOther != null && pOther instanceof GameImpl &&
            mState.equals(((GameImpl) pOther).mState);
    }

    public int hashCode() {
        return mState.hashCode();
    }

    // Methods: helper

    private void setState(StateImpl pState) {
        Assert.assertNotNull(pState);
        mState = pState;
        mState.setContext(this);
    }

    private synchronized ListenerManager getListenerManager() {
        if (mListenerManager == null) {
            mListenerManager = new ListenerManager();
        }
        return mListenerManager;
    }

    private synchronized StickyMap getListenersToPlayerMap() {
        if (mListenersToPlayersMap == null) {
            mListenersToPlayersMap = new HashStickyMap();
        }
        return mListenersToPlayersMap;
    }

}
