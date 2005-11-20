package org.schweisguth.xt.common.gameimpl.hascurrentplayer;

import org.schweisguth.xt.common.command.RearrangeRackCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class RearrangedRackEvent extends BaseEvent {
    private static final long serialVersionUID = -3780007378714231628L;

    public RearrangedRackEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(RearrangeRackCommand.class, pRequest));
    }

    public String toHTML() {
        return "";
    }

}
