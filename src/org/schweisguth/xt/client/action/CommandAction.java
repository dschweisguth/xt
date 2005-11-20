package org.schweisguth.xt.client.action;

import java.rmi.RemoteException;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.util.contract.Assert;

public class CommandAction extends BaseDisableableAction {
    // Fields
    private final Command mCommand;

    // Constructors

    public CommandAction(Client pClient, Command pCommand) {
        super(assertNotNull(pCommand).getName(), pClient);
        mCommand = pCommand;
    }

    private static Command assertNotNull(Command pCommand) {
        Assert.assertNotNull(pCommand);
        return pCommand;
    }

    // Methods

    protected boolean shouldBeEnabled() throws RemoteException {
        return getClient().canExecute(mCommand);
    }

    public void execute() throws RemoteException {
        getClient().execute(mCommand);
    }

}
