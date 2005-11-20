package org.schweisguth.xttest.common.gameimpl.base;

import java.util.List;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xttest.testutil.TestListener;

public class TestClient extends LocalClient {
    private final TestListener mListener = new TestListener();

    public TestClient(ListenableGame pGame, String pPlayer) {
        super(pGame, pPlayer);
        addListener(mListener);
    }

    public List getEvents() {
        return mListener.getEvents();
    }

    public void clear() {
        mListener.clear();
    }

}
