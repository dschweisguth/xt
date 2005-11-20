package org.schweisguth.xt.common.command;

import java.util.List;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.util.contract.Assert;

public class TransferSetCommand extends Command {
    private static final long serialVersionUID = -4965188670927165692L;

    private final TransferSet mTransferSet;

    public TransferSetCommand(TransferSet pTransferSet) {
        super("");
        Assert.assertNotNull(pTransferSet);
        mTransferSet = pTransferSet;
    }

    protected List getFields() {
        return append(super.getFields(), mTransferSet);
    }

    public TransferSet getTransferSet() {
        return mTransferSet;
    }

}
