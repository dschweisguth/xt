package org.schweisguth.xt.client.server;

import java.util.List;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.util.contract.Assert;
import org.schweisguth.xt.common.util.object.ValueObject;

public class RefreshEvent extends ValueObject implements Event {
    private final Game mGame;

    public RefreshEvent(Game pGame) {
        Assert.assertNotNull(pGame);
        mGame = pGame;
    }

    protected List getFields() {
        return append(super.getFields(), mGame);
    }

    public Game getGame() {
        return mGame;
    }

    public String toHTML() {
        return "";
    }

}
