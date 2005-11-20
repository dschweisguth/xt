package org.schweisguth.xt.common.gameimpl.ended;

import org.schweisguth.xt.common.command.StartNewGameCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class StartedNewGameEvent extends BaseEvent {
    private static final long serialVersionUID = -6184201914661705570L;

    public static final String STARTED =
        "{0} started a new game. Anyone may press Join Game to join the game.";

    public StartedNewGameEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(StartNewGameCommand.class, pRequest));
    }

    public String toHTML() {
        return format(STARTED, getPlayer());
    }

}
