package org.schweisguth.xt.common.gameimpl.stateimpl;

import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.gameimpl.state.HasCurrentPlayerState;
import org.schweisguth.xt.common.gameimpl.state.HasTransferSetState;
import org.schweisguth.xt.common.gameimpl.state.State;
import org.schweisguth.xt.common.gameimpl.state.StateContext;

class NullStateContext implements StateContext {
    private static final long serialVersionUID = 7337864046295629376L;

    public Game getGame() {
        return new NullGame();
    }

    public void goToJoiningState() {
        throw new UnsupportedOperationException();
    }

    public void goToDrawingForFirstState(State pFrom) {
        throw new UnsupportedOperationException();
    }

    public void goToDrawingStartingTilesState(State pFrom,
        int pCurrentPlayerIndex) {
        throw new UnsupportedOperationException();
    }

    public void goToMovingState(HasCurrentPlayerState pFrom) {
        throw new UnsupportedOperationException();
    }

    public void goToApprovingState(HasTransferSetState pFrom) {
        throw new UnsupportedOperationException();
    }

    public void goToChallengingState(HasTransferSetState pFrom) {
        throw new UnsupportedOperationException();
    }

    public void goToDrawingNewTilesState(HasCurrentPlayerState pFrom) {
        throw new UnsupportedOperationException();
    }

    public void goToEndedState(State pFrom) {
        throw new UnsupportedOperationException();
    }

    public void send(Event pEvent) {
        throw new UnsupportedOperationException();
    }

}
