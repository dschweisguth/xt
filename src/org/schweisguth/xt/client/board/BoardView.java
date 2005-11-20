package org.schweisguth.xt.client.board;

import org.schweisguth.xt.common.domain.Position;

public interface BoardView {
    Position getSelection();

    boolean selectionIsEmpty();

    void clearSelection();
}
