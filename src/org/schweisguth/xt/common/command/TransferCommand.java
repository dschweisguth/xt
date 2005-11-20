package org.schweisguth.xt.common.command;

import java.util.List;
import org.schweisguth.xt.common.domain.Transfer;
import org.schweisguth.xt.common.util.contract.Assert;

public class TransferCommand extends Command {
    private static final long serialVersionUID = -5827469640300019882L;

    private final Transfer mTransfer;

    public TransferCommand(Transfer pTransfer) {
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
