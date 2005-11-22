package org.schweisguth.xt.common.gameimpl;

import org.schweisguth.xt.common.command.GoOffLineCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class WentOffLineEvent extends BaseEvent {
    private static final long serialVersionUID = 0L;

    public static final String WENT_OFF_LINE = "{0} went off line.";

    public WentOffLineEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(GoOffLineCommand.class, pRequest));
    }

    public String toHTML() {
        return format(WENT_OFF_LINE, getPlayer());
    }

}
