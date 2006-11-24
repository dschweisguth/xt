package org.schweisguth.xt.common.gameimpl.moving;

import org.schweisguth.xt.common.command.TakeBackAllCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class TookBackAllEvent extends BaseEvent {
    private static final long serialVersionUID = 0L;

    public TookBackAllEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(TakeBackAllCommand.class, pRequest));
    }

    public String toHTML() {
        return "";
    }

}
