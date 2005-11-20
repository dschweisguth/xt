package org.schweisguth.xt.common.gameimpl.drawingstartingtiles;

import org.schweisguth.xt.common.command.DrawStartingTilesCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class DrewStartingTilesEvent extends BaseEvent {
    private static final long serialVersionUID = -8685092339138804054L;

    public static final String DREW_STARTING_TILES = "{0} drew starting tiles.";

    public DrewStartingTilesEvent(Game pGame, Request pRequest) {
        super(pGame,
            assertCommandClass(DrawStartingTilesCommand.class, pRequest));
    }

    public String toHTML() {
        String message = format(DREW_STARTING_TILES, getPlayer());
        if (getGame().isIn(Game.DRAWING_STARTING_TILES)) {
            message += getDrawYourStartingTilesMessage();
        } else {
            message += getItsYourTurnMessage();
        }
        return message;
    }

}
