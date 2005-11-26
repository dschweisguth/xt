package org.schweisguth.xt.client.toolbar;

import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.client.util.MainFrameHolder;
import org.schweisguth.xt.common.command.EndGameCommand;

public class EndGameAction extends CommandAction {
    public EndGameAction(Client pClient) {
        super(pClient, new EndGameCommand());
    }

    protected void execute() throws RemoteException {
        int choice = JOptionPane.showConfirmDialog(
            MainFrameHolder.instance().getMainFrame(),
            "Are you sure you want to end the game?", "End Game?",
            JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            super.execute();
        }
    }

}
