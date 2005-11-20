package org.schweisguth.xt.common.game;

public interface ListenableGame extends Game {
    void addListener(String pPlayer, ListenerOperations pListener);

    void removeListener(ListenerOperations mListener);
}
