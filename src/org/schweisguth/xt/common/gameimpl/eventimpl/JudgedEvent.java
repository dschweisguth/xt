package org.schweisguth.xt.common.gameimpl.eventimpl;

import java.util.List;
import org.schweisguth.xt.common.domain.Player;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;

public abstract class JudgedEvent extends BaseEvent {
    private static final long serialVersionUID = 1693301375899906974L;

    // Constants
    public static final String APPROVED = "{0} approved {1}''s move.";
    public static final String SCORED =
        " {0} scored {1} points for a total of {2} points.";
    public static final String USED_ALL = " {0} used all tiles.";
    public static final String BONUS = " {0} scored {1} bonus points from " +
        "the other players'' tiles for a final total of {2} points, and " +
        "each of the other players lost the sum of his or her tiles.";
    public static final String DRAW_YOUR_NEW_TILES =
        " {0}, draw your new tiles.";


    // Fields
    private final String mCurrentPlayer;

    // Constructors

    protected JudgedEvent(Game pGame, Request pRequest,
        String pCurrentPlayer) {
        super(pGame, pRequest);
        Player.assertIsValid(pCurrentPlayer);
        mCurrentPlayer = pCurrentPlayer;
    }

    // Methods: overrides

    protected List getFields() {
        return append(super.getFields(), mCurrentPlayer);
    }

    // Methods: for subclasses

    protected String getCurrentPlayer() {
        return mCurrentPlayer;
    }

    protected String getSuccessMessage() {
        String message = format(APPROVED, getPlayer(), getCurrentPlayer());
        if (! getGame().isIn(Game.APPROVING)) {
            ScoreSheet scores = getGame().getScoreSheet();
            int rowCount = scores.getRowCount();
            int bonusRowOffset = getGame().isIn(Game.ENDED) ? 1 : 0;
            int total =
                scores.getInt(rowCount - 1 - bonusRowOffset, getCurrentPlayer());
            int previousTotal = rowCount >= 2 + bonusRowOffset
                ? scores.getInt(rowCount - 2 - bonusRowOffset, getCurrentPlayer())
                : 0;
            message +=
                format(SCORED, getCurrentPlayer(), total - previousTotal, total);
            if (getGame().isIn(Game.DRAWING_NEW_TILES)) {
                message += format(DRAW_YOUR_NEW_TILES, getCurrentPlayer());
            } else if (getGame().isIn(Game.MOVING)) {
                message += NO_MORE_TILES + getItsYourTurnMessage();
            } else if (getGame().isIn(Game.ENDED)) {
                message +=
                    format(USED_ALL, getCurrentPlayer()) + getStillHasMessage();
                int finalTotal = scores.getInt(rowCount - 1, getCurrentPlayer());
                int bonus = finalTotal - total;
                message += format(BONUS, getCurrentPlayer(), bonus, finalTotal) +
                    getWinnerMessage();
            }
        }
        return message;
    }

}
