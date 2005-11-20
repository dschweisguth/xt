package org.schweisguth.xt.common.gameimpl.stateimpl;

import org.schweisguth.xt.common.command.ChatCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class ChatEvent extends BaseEvent {
    private static final long serialVersionUID = -8618826695153925327L;

    public static final String MESSAGE = "<font color=\"blue\">{0}: {1}</font>";

    public ChatEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(ChatCommand.class, pRequest));
    }

    public String toHTML() {
        return format(MESSAGE, getPlayer(),
            ((ChatCommand) getCommand()).getText());
    }

}
