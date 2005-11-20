package org.schweisguth.xt.common.gameimpl.moving;

import org.schweisguth.xt.common.command.ExchangeCommand;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.eventimpl.BaseEvent;
import org.schweisguth.xt.common.util.contract.Assert;

public class ExchangedEvent extends BaseEvent {
    private static final long serialVersionUID = -6934685795888262653L;

    public static final String EXCHANGED = "{0} exchanged {1} {2}.";
    public static final String TILE = "tile";
    public static final String TILES = "tiles";

    public static String exchanged(String pPlayer, int pTileCount) {
        Assert.assertTrue(pTileCount > 0);
        return format(EXCHANGED,
            pPlayer, pTileCount, pTileCount == 1 ? TILE : TILES);
    }

    public ExchangedEvent(Game pGame, Request pRequest) {
        super(pGame, assertCommandClass(ExchangeCommand.class, pRequest));
    }

    public String toHTML() {
        int exchangedCount =
            ((ExchangeCommand) getCommand()).getRackPositions().length;
        return exchanged(getPlayer(), exchangedCount) + getItsYourTurnMessage();
    }

}
