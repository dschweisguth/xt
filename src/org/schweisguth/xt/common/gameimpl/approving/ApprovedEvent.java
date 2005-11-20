package org.schweisguth.xt.common.gameimpl.approving;

import org.schweisguth.xt.common.command.ApproveCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.JudgedEvent;

public class ApprovedEvent extends JudgedEvent {
    private static final long serialVersionUID = 4683986518921094309L;

    public ApprovedEvent(Game pGame, Request pRequest, String pCurrentPlayer) {
        super(pGame, assertCommandClass(ApproveCommand.class, pRequest),
            pCurrentPlayer);
    }

    public String toHTML() {
        return getSuccessMessage();
    }

}
