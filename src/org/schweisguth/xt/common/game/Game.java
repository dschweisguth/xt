package org.schweisguth.xt.common.game;

import java.util.SortedMap;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.util.collection.SetList;

public interface Game {
    // Constants
    int MAX_PLAYERS = 4;
    String JOINING = "JOINING";
    String DRAWING_FOR_FIRST = "DRAWING_FOR_FIRST";
    String DRAWING_STARTING_TILES = "DRAWING_STARTING_TILES";
    String MOVING = "MOVING";
    String APPROVING = "APPROVING";
    String CHALLENGING = "CHALLENGING";
    String DRAWING_NEW_TILES = "DRAWING_NEW_TILES";
    String ENDED = "ENDED";

    // Methods
    String getState();

    boolean isIn(String pState);

    SetList getPlayers();

    Rack getRack(String pPlayer);

    BoxLid getBoxLid();

    Board getBoard();

    ScoreSheet getScoreSheet();

    boolean hasCurrentPlayer();

    String getCurrentPlayer();

    SortedMap getTilesDrawnForFirst();

    boolean noPlayersHaveDrawnForFirstYetThisRound();

    boolean canExecute(Request pRequest);

    void execute(Request pRequest);

}
