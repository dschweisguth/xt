package org.schweisguth.xt.client;

import java.awt.event.ActionEvent;
import org.schweisguth.xt.client.action.BaseClientAction;
import org.schweisguth.xt.client.server.Client;

class ExitAction extends BaseClientAction {
    // Constructors

    public ExitAction(Client pClient) {
        super("Exit", pClient);
    }

    // Methods: implements ActionListener

    public void actionPerformed(ActionEvent pEvent) {
        Client client = getClient();
        QuitUtil.quit(client);
    }

}
