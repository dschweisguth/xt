package org.schweisguth.xt.client.chat.send;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import org.schweisguth.xt.client.error.ErrorDialog;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.command.ChatCommand;

public class ChatSendController {
    private final ChatSendView mView;

    public ChatSendController(Client pClient) {
        mView = new ChatSendView(new ChatSendAction(pClient));
    }

    private class ChatSendAction extends AbstractAction {
        private final Client mClient;

        private ChatSendAction(Client pClient) {
            super("Send");
            mClient = pClient;
        }

        public void actionPerformed(ActionEvent pEvent) {
            try {
                String text = mView.getText();
                if (! text.equals("")) {
                    mClient.execute(new ChatCommand(text));
                }
            } catch (RemoteException e) {
                new ErrorDialog("Couldn't send message", e).show();
            }
            mView.setText("");
        }

    }

    public JComponent getView() {
        return mView;
    }

}
