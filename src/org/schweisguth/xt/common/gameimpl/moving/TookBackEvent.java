package org.schweisguth.xt.common.gameimpl.moving;

import org.schweisguth.xt.common.command.TakeBackAllCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class TookBackEvent extends BaseEvent {
    private static final long serialVersionUID = -7585344603572947197L;

    public TookBackEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(TakeBackAllCommand.class, pRequest));
    }

    public String toHTML() {
        return "";
    }

}
