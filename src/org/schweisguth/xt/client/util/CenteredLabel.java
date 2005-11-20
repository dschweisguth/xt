package org.schweisguth.xt.client.util;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class CenteredLabel extends JLabel {
    public CenteredLabel() {
        setHorizontalAlignment(CENTER);
        setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentY(CENTER_ALIGNMENT);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
}
