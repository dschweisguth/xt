package org.schweisguth.xt.common.command;

import java.util.List;
import org.schweisguth.xt.common.domain.Transfer;
import org.schweisguth.xt.common.util.contract.Assert;

public class TakeBackCommand extends Command {
    private static final long serialVersionUID = -8507127067055982185L;

    private final Transfer mTransfer;

    public TakeBackCommand(Transfer pTransfer) {
        super("");
        Assert.assertNotNull(pTransfer);
        mTransfer = pTransfer;
    }

    protected List getFields() {
        return append(super.getFields(), mTransfer);
    }

    public Transfer getTransfer() {
        return mTransfer;
    }

}
