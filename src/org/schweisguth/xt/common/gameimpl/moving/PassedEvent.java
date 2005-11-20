package org.schweisguth.xt.common.gameimpl.moving;

import org.schweisguth.xt.common.command.PassCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class PassedEvent extends BaseEvent {
    private static final long serialVersionUID = -3911549044443306972L;

    public static final String PASSED = "{0} passed.";

    public PassedEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(PassCommand.class, pRequest));
    }

    public String toHTML() {
        return format(PASSED, getPlayer()) + getItsYourTurnMessage();
    }

}
