package org.schweisguth.xt.client.action;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.SwingUtilities;
import org.schweisguth.xt.client.error.ErrorDialog;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Listener;

public abstract class BaseDisableableAction extends BaseClientAction
    implements Listener {
    // Constructors

    protected BaseDisableableAction(String pName, Client pClient) {
        super(pName, pClient);
        getClient().addListener(this);
    }

    // Methods: implements ActionListener

    public void actionPerformed(ActionEvent pEvent) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    execute();
                }
                catch (RemoteException e) {
                    new ErrorDialog("Couldn't contact game server", e).show();
                }
            }
        });
    }

    protected abstract void execute() throws RemoteException;

    // Methods: implements Listener

    public void send(Event pEvent) {
        updateEnabled();
    }

    // Methods: helper

    protected void updateEnabled() {
        try {
            setEnabled(shouldBeEnabled());
        }
        catch (RemoteException e) {
            new ErrorDialog("Couldn't contact game server", e).show();
        }
    }

    protected abstract boolean shouldBeEnabled() throws RemoteException;

}
