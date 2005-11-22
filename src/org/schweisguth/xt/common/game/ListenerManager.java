package org.schweisguth.xt.common.game;

import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.HashStickySet;
import org.schweisguth.xt.common.util.collection.SetList;
import org.schweisguth.xt.common.util.collection.StickySet;
import org.schweisguth.xt.common.util.contract.Assert;
import org.schweisguth.xt.common.util.logging.Level;
import org.schweisguth.xt.common.util.logging.Logger;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;
import java.util.ArrayList;

public class ListenerManager implements Serializable {
    private static final long serialVersionUID = 6185529658847718522L;

    // Fields
    private final SetList mListeners = new ArraySetList();
    private final StickySet mListenerAddQueue = new HashStickySet();
    private final StickySet mListenerRemoveQueue = new HashStickySet();
    private boolean mIsForwarding = false;

    // Methods

    public Iterator iterator() {
        return mListeners.iterator();
    }

    public void addListener(ListenerOperations pListener) {
        Assert.assertNotNull(pListener);
        if (mIsForwarding) {
            mListenerAddQueue.add(pListener);
        } else {
            mListeners.add(pListener);
        }
    }

    public void removeListener(ListenerOperations pListener) {
        Assert.assertNotNull(pListener);
        Assert.assertTrue(mListeners.contains(pListener));
        if (mIsForwarding) {
            mListenerRemoveQueue.add(pListener);
        } else {
            mListeners.remove(pListener);
        }
    }

    public List send(Event pEvent) {
        mIsForwarding = true;
        ListIterator listeners = mListeners.listIterator();
        List removedListeners = new ArrayList();
        while (listeners.hasNext()) {
            ListenerOperations listener =
                (ListenerOperations) listeners.next();
            try {
                long startTime = System.currentTimeMillis();
                Logger.global.fine(
                    "Sending event " + pEvent + " to " + listener + " ...");
                listener.send(pEvent);
                Logger.global.fine("Sent event " + pEvent + " to " + listener +
                    " in " + (System.currentTimeMillis() - startTime) +
                    " ms.");
            } catch (RemoteException e) {
                try {
                    listeners.remove();
                    removedListeners.add(listener);
                } catch (Exception removalException) {
                    Logger.global.log(Level.WARNING,
                        "Couldn't remove listener", removalException);
                }
                Logger.global.log(Level.WARNING,
                    "Couldn't contact listener; removed it.", e);
            }
        }
        mIsForwarding = false;
        if (! mListenerRemoveQueue.isEmpty()) {
            mListeners.removeAll(mListenerRemoveQueue);
            mListenerRemoveQueue.clear();
        }
        if (! mListenerAddQueue.isEmpty()) {
            mListeners.addAll(mListenerAddQueue);
            mListenerAddQueue.clear();
        }
        return removedListeners;
    }

}
