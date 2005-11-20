package org.schweisguth.xt.common.gameimpl.drawingnewtiles;

import org.schweisguth.xt.common.command.DrawNewTilesCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class DrewNewTilesEvent extends BaseEvent {
    private static final long serialVersionUID = 9037654129723215702L;

    public static final String DREW_NEW_TILES = "{0} drew new tiles.";

    public DrewNewTilesEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(DrawNewTilesCommand.class, pRequest));
    }

    public String toHTML() {
        String message = format(DREW_NEW_TILES, getPlayer());
        if (getGame().getBoxLid().isEmpty()) {
            message += NO_MORE_TILES;
        }
        message += getItsYourTurnMessage();
        return message;
    }

}
