package org.schweisguth.xt.common.gameimpl.drawingforfirst;

import java.util.List;
import org.schweisguth.xt.common.command.DrawForFirstCommand;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.contract.Assert;

public class DrewForFirstEvent extends BaseEvent {
    private static final long serialVersionUID = -6745478077240810709L;

    // Constants
    public static final String DREW = "{0} drew \"{1}\".";
    public static final String GOES_FIRST = " {0} goes first.";
    public static final String TIED_DRAWING_FOR_FIRST =
        " {0} tied and must draw again.";

    // Fields
    private final Tile mTile;

    // Constructors

    public DrewForFirstEvent(Game pGame, Request pRequest, Tile pTile) {
        super(pGame, assertCommandClass(DrawForFirstCommand.class, pRequest));
        Assert.assertNotNull(pTile);
        mTile = pTile;
    }

    // Methods

    protected List getFields() {
        return append(super.getFields(), mTile);
    }

    public String toHTML() {
        String message = format(DREW, getPlayer(), mTile);
        if (getGame().isIn(Game.DRAWING_STARTING_TILES)) {
            message += format(GOES_FIRST, getGame().getCurrentPlayer()) +
                getDrawYourStartingTilesMessage();
        } else if (getGame().noPlayersHaveDrawnForFirstYetThisRound()) {
            message += format(TIED_DRAWING_FOR_FIRST, CollectionUtil.enumerate(
                getGame().getTilesDrawnForFirst().keySet()));
        }
        return message;
    }

}
