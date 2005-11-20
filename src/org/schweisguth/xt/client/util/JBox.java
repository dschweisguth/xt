package org.schweisguth.xt.client.util;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class JBox extends JPanel {
    public static JBox createHorizontalBox() {
        return new JBox(BoxLayout.X_AXIS);
    }

    public static JBox createVerticalBox() {
        return new JBox(BoxLayout.Y_AXIS);
    }

    public JBox(int pOrientation) {
        setLayout(new BoxLayout(this, pOrientation));
    }

}
