package org.schweisguth.xt.client.observable;

import java.util.Iterator;
import org.schweisguth.xt.common.util.collection.HashStickySet;
import org.schweisguth.xt.common.util.collection.StickySet;
import org.schweisguth.xt.common.util.contract.Assert;

public class ObserverManager {
    private final StickySet mObservers = new HashStickySet();

    public void addObserver(Observer pObserver) {
        Assert.assertNotNull(pObserver);
        Assert.assertFalse(mObservers.contains(pObserver));
        mObservers.add(pObserver);
    }

    public void removeObserver(Observer pObserver) {
        Assert.assertNotNull(pObserver);
        Assert.assertTrue(mObservers.contains(pObserver));
        mObservers.remove(pObserver);
    }

    public void update() {
        for (Iterator observers = mObservers.iterator(); observers.hasNext();)
        {
            ((Observer) observers.next()).update();
        }
    }

}
