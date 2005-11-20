package org.schweisguth.xt.common.gameimpl;

import org.schweisguth.xt.common.command.LogOutCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class LoggedOutEvent extends BaseEvent {
    private static final long serialVersionUID = 6338577695580519056L;

    public static final String LOGGED_OUT = "{0} logged out.";

    public LoggedOutEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(LogOutCommand.class, pRequest));
    }

    public String toHTML() {
        return format(LOGGED_OUT, getPlayer());
    }

}
