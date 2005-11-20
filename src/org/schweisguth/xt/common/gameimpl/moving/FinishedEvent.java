package org.schweisguth.xt.common.gameimpl.moving;

import org.schweisguth.xt.common.command.FinishCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class FinishedEvent extends BaseEvent {
    private static final long serialVersionUID = 136791594815464421L;

    public static final String FINISHED =
        "{0} has finished moving. Other players, approve or challenge the move.";

    public FinishedEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(FinishCommand.class, pRequest));
    }

    public String toHTML() {
        return format(FINISHED, getPlayer());
    }

}
