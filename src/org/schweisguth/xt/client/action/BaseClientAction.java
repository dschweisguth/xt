package org.schweisguth.xt.client.action;

import javax.swing.AbstractAction;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.util.contract.Assert;

public abstract class BaseClientAction extends AbstractAction {
    // Fields
    private final Client mClient;

    // Constructors

    protected BaseClientAction(String pName, Client pClient) {
        super(pName);
        Assert.assertNotNull(pClient);
        mClient = pClient;
    }

    // Methods: queries

    protected Client getClient() {
        return mClient;
    }

}
