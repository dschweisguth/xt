package org.schweisguth.xt.common.gameimpl.hastransferset;

import org.schweisguth.xt.common.command.TransferCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class TransferredEvent extends BaseEvent {
    private static final long serialVersionUID = 664521810802380555L;

    public TransferredEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(TransferCommand.class, pRequest));
    }

    public String toHTML() {
        return "";
    }

}
