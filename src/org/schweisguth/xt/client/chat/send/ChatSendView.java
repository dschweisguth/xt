package org.schweisguth.xt.client.chat.send;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import org.schweisguth.xt.client.util.JBox;

class ChatSendView extends JBox {
    private final JTextField mTextField = new JTextField();

    public ChatSendView(Action pAction) {
        super(BoxLayout.X_AXIS);
        mTextField.addActionListener(pAction);
        add(mTextField);
        add(new JButton(pAction));
    }

    public String getText() {
        return mTextField.getText();
    }

    public void setText(String pText) {
        mTextField.setText(pText);
    }

}
