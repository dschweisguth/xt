package org.schweisguth.xt.common.gameimpl.approving;

import org.schweisguth.xt.common.command.ChallengeCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.JudgedEvent;

public class ChallengedEvent extends JudgedEvent {
    private static final long serialVersionUID = -2559432302372709512L;

    public static final String CHALLENGED =
        "{0} challenged {1}''s move. Someone check the dictionary.";

    public ChallengedEvent(Game pGame, Request pRequest, String pCurrentPlayer) {
        super(pGame, assertCommandClass(ChallengeCommand.class, pRequest),
            pCurrentPlayer);
    }

    public String toHTML() {
        return format(CHALLENGED, getPlayer(), getCurrentPlayer());
    }

}
