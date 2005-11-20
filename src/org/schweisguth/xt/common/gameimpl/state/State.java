package org.schweisguth.xt.common.gameimpl.state;

import java.io.Serializable;
import java.util.Map;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.util.collection.SetList;

public interface State extends Serializable {
    SetList getPlayers();

    Map getRacks();

    BoxLid getBoxLid();

    Board getBoard();

    ScoreSheet getScoreSheet();
}
