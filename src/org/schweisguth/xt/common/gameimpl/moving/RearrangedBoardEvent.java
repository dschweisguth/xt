package org.schweisguth.xt.common.gameimpl.moving;

import org.schweisguth.xt.common.command.RearrangeBoardCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class RearrangedBoardEvent extends BaseEvent {
    private static final long serialVersionUID = 8527856253348132125L;

    public RearrangedBoardEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(RearrangeBoardCommand.class, pRequest));
    }

    public String toHTML() {
        return "";
    }

}
