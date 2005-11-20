package org.schweisguth.xttest.testutil;

import java.util.ArrayList;
import java.util.List;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Listener;
import org.schweisguth.xt.common.util.contract.Assert;

public class TestListener implements Listener {
    private final List mEvents = new ArrayList();

    public void send(Event pEvent) {
        Assert.assertNotNull(pEvent);
        mEvents.add(pEvent);
    }

    public List getEvents() {
        return mEvents;
    }

    public void clear() {
        mEvents.clear();
    }

}
