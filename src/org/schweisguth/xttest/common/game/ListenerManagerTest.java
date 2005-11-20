package org.schweisguth.xttest.common.game;

import junit.framework.TestCase;
import org.schweisguth.xt.client.server.RefreshEvent;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Listener;
import org.schweisguth.xt.common.game.ListenerManager;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xttest.testutil.TestListener;

public class ListenerManagerTest extends TestCase {
    public void testAddReentrancy() {
        ListenerManager manager = new ListenerManager();
        AddingListener addingListener = new AddingListener(manager);
        manager.addListener(addingListener);
        manager.send(new RefreshEvent(
            new GameImpl(new JoiningState(new String[] { "player1" }))));
        Event expectedEvent = new RefreshEvent(
            new GameImpl(new JoiningState(new String[] { "player2" })));
        manager.send(expectedEvent);
        assertEquals(CollectionUtil.asList(expectedEvent),
            addingListener.getTestListener().getEvents());
    }

    private static class AddingListener implements Listener {
        private final ListenerManager mListenable;
        private final TestListener mTestListener = new TestListener();
        private boolean mAddedTestListener = false;

        private AddingListener(ListenerManager pListenable) {
            mListenable = pListenable;
        }

        public void send(Event pEvent) {
            if (! mAddedTestListener) {
                mListenable.addListener(mTestListener);
                mAddedTestListener = true;
            }
        }

        private TestListener getTestListener() {
            return mTestListener;
        }

    }

    public void testRemoveReentrancy() {
        ListenerManager manager = new ListenerManager();
        SelfRemovingListener listener = new SelfRemovingListener(manager);
        manager.addListener(listener);
        Event expectedEvent = new RefreshEvent(
            new GameImpl(new JoiningState(new String[] { "player1" })));
        manager.send(expectedEvent);
        manager.send(new RefreshEvent(
            new GameImpl(new JoiningState(new String[] { "player2" }))));
        assertEquals(CollectionUtil.asList(expectedEvent), listener.getEvents());
    }

    private static class SelfRemovingListener extends TestListener {
        private final ListenerManager mListenable;

        private SelfRemovingListener(ListenerManager pListenable) {
            mListenable = pListenable;
        }

        public void send(Event pEvent) {
            super.send(pEvent);
            mListenable.removeListener(this);
        }

    }

}
