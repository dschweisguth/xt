package org.schweisguth.xt.common.gameimpl.state;

import java.io.Serializable;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;

public interface StateContext extends Serializable {
    Game getGame();

    void goToJoiningState();

    void goToDrawingForFirstState(State pFrom);

    void goToDrawingStartingTilesState(
        State pFrom, int pCurrentPlayerIndex);

    void goToMovingState(HasCurrentPlayerState pFrom);

    void goToApprovingState(HasTransferSetState pFrom);

    void goToChallengingState(HasTransferSetState pFrom);

    void goToDrawingNewTilesState(HasCurrentPlayerState pFrom);

    void goToEndedState(State pFrom);

    void send(Event pEvent);
}
