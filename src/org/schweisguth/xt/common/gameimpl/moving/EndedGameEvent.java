package org.schweisguth.xt.common.gameimpl.moving;

import org.schweisguth.xt.common.command.EndGameCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class EndedGameEvent extends BaseEvent {
    private static final long serialVersionUID = -7466117084744406438L;

    public static final String ENDED_GAME = "{0} ended the game.";

    public EndedGameEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(EndGameCommand.class, pRequest));
    }

    public String toHTML() {
        return format(ENDED_GAME, getPlayer()) + getStillHasMessage() +
            getWinnerMessage();
    }

}
