package org.schweisguth.xt.common.command;

import java.util.List;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.util.contract.Assert;

public class TakeBackCommand extends Command {
    private static final long serialVersionUID = 0L;

    private final TransferSet mTransferSet;

    public TakeBackCommand(TransferSet pTransferSet) {
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
