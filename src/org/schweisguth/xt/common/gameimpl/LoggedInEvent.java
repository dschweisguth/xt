package org.schweisguth.xt.common.gameimpl;

import org.schweisguth.xt.common.command.LogInCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class LoggedInEvent extends BaseEvent {
    private static final long serialVersionUID = -6374351143365314800L;

    public static final String LOGGED_IN = "{0} logged in.";

    public LoggedInEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(LogInCommand.class, pRequest));
    }

    public String toHTML() {
        return format(LOGGED_IN, getPlayer());
    }

}
