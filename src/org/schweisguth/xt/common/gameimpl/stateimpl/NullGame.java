package org.schweisguth.xt.common.gameimpl.stateimpl;

import java.util.SortedMap;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.util.collection.SetList;

class NullGame implements Game {
    public String getState() {
        throw new UnsupportedOperationException();
    }

    public boolean isIn(String pState) {
        throw new UnsupportedOperationException();
    }

    public SetList getPlayers() {
        throw new UnsupportedOperationException();
    }

    public Rack getRack(String pPlayer) {
        throw new UnsupportedOperationException();
    }

    public BoxLid getBoxLid() {
        throw new UnsupportedOperationException();
    }

    public Board getBoard() {
        throw new UnsupportedOperationException();
    }

    public ScoreSheet getScoreSheet() {
        throw new UnsupportedOperationException();
    }

    public boolean hasCurrentPlayer() {
        throw new UnsupportedOperationException();
    }

    public String getCurrentPlayer() {
        throw new UnsupportedOperationException();
    }

    public SortedMap getTilesDrawnForFirst() {
        throw new UnsupportedOperationException();
    }

    public boolean noPlayersHaveDrawnForFirstYetThisRound() {
        throw new UnsupportedOperationException();
    }

    public boolean canExecute(Request pRequest) {
        throw new UnsupportedOperationException();
    }

    public void execute(Request pRequest) {
        throw new UnsupportedOperationException();
    }

}
