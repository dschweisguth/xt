package org.schweisguth.xt.client.letterdistribution;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class LetterDistributionAction extends AbstractAction implements Action {
    public LetterDistributionAction() {
        super("Letter Distribution");
    }

    public void actionPerformed(ActionEvent event) {
        new LetterDistributionView().show();
    }

}
