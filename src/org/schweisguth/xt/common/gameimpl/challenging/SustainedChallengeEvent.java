package org.schweisguth.xt.common.gameimpl.challenging;

import org.schweisguth.xt.common.command.SustainChallengeCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.JudgedEvent;

public class SustainedChallengeEvent extends JudgedEvent {
    private static final long serialVersionUID = -859728897741206024L;

    public static final String SUSTAINED =
        "{0} rejected {1}''s move. {1} loses a turn.";

    public SustainedChallengeEvent(Game pGame, Request pRequest,
        String pCurrentPlayer) {
        super(pGame,
            assertCommandClass(SustainChallengeCommand.class, pRequest),
            pCurrentPlayer);
    }

    public String toHTML() {
        return format(SUSTAINED, getPlayer(), getCurrentPlayer()) +
            getItsYourTurnMessage();
    }

}
