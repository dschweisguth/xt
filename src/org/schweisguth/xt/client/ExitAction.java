package org.schweisguth.xt.client;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import org.schweisguth.xt.client.action.BaseClientAction;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.util.logging.Level;
import org.schweisguth.xt.common.util.logging.Logger;

// TODO On MacOS, quitting with apple-Q doesn't trigger this

class ExitAction extends BaseClientAction {
    // Constructors

    public ExitAction(Client pClient) {
        super("Exit", pClient);
    }

    // Methods: implements ActionListener

    public void actionPerformed(ActionEvent pEvent) {
        try {
            getClient().disconnect();
        } catch (RemoteException e) {
            Logger.global.log(Level.WARNING,
                "Couldn't remove remote listeners", e);
        }
        System.exit(0);
    }

}
