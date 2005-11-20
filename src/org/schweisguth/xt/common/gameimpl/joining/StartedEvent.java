package org.schweisguth.xt.common.gameimpl.joining;

import org.schweisguth.xt.common.command.StartCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class StartedEvent extends BaseEvent {
    private static final long serialVersionUID = -8006499752272845941L;

    public static final String STARTED =
        "{0} started the game. Everyone, draw a tile to see who goes first.";

    public StartedEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(StartCommand.class, pRequest));
    }

    public String toHTML() {
        return format(STARTED, getPlayer());
    }

}
