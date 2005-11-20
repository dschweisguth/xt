package org.schweisguth.xt.common.gameimpl.joining;

import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;

public class JoinedEvent extends BaseEvent {
    private static final long serialVersionUID = 6437456762562482826L;

    public static final String JOINED = "{0} joined the game.";
    public static final String START_OR_WAIT =
        " Press Start Game to start the game, or wait for more players to join.";

    public JoinedEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(JoinCommand.class, pRequest));
    }

    public String toHTML() {
        String message = format(JOINED, getPlayer());
        if (getGame().getPlayers().size() > 1) {
            message += START_OR_WAIT;
        }
        return message;
    }

}
