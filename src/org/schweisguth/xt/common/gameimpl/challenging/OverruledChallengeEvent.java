package org.schweisguth.xt.common.gameimpl.challenging;

import org.schweisguth.xt.common.command.OverruleChallengeCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.JudgedEvent;

public class OverruledChallengeEvent extends JudgedEvent {
    private static final long serialVersionUID = -3287780221263915893L;

    public OverruledChallengeEvent(Game pGame, Request pRequest,
        String pCurrentPlayer) {
        super(pGame,
            assertCommandClass(OverruleChallengeCommand.class, pRequest),
            pCurrentPlayer);
    }

    public String toHTML() {
        return getSuccessMessage();
    }

}
