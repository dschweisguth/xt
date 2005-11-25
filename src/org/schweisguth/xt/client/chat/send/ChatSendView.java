package org.schweisguth.xt.client.chat.send;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import org.schweisguth.xt.client.util.JBox;

public class ChatSendView extends JBox {
    private final Action mAction;
    private final JTextField mTextField = new JTextField();

    public ChatSendView(Action pAction) {
        super(BoxLayout.X_AXIS);
        mAction = pAction;
        mTextField.addActionListener(pAction);
        add(mTextField);
        add(new JButton(pAction));
    }

    /**
     * Testing only
     */
    public Action getAction() {
        return mAction;
    }

    /**
     * Testing only
     */
    public JTextField getTextField() {
        return mTextField;
    }

    public String getText() {
        return mTextField.getText();
    }

    public void setText(String pText) {
        mTextField.setText(pText);
    }

}
