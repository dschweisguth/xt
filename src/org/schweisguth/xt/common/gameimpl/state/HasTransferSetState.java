package org.schweisguth.xt.common.gameimpl.state;

import org.schweisguth.xt.common.domain.TransferSet;

public interface HasTransferSetState extends HasCurrentPlayerState {
    TransferSet getTransferSet();
}
