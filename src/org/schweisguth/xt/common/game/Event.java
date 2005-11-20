package org.schweisguth.xt.common.game;

import java.io.Serializable;

public interface Event extends Serializable {
    Game getGame();

    String toHTML();
}
