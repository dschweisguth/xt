package org.schweisguth.xt.client.toolbar;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import org.schweisguth.xt.client.util.CardView;

public class ToolBarView extends JPanel implements CardView {
    // Constants
    public static final String NO_BUTTONS = "NO_BUTTONS";
    public static final String JOINING = "JOINING";
    public static final String PLAYING = "PLAYING";
    public static final String ENDED = "ENDED";

    // Fields
    private String mCard;

    // Constructors

    public ToolBarView(Action pJoinAction, Action pStartAction,
        Action pFinishAction, Action pApproveAction, Action pChallengeAction,
        Action pOverruleChallengeAction, Action pSustainChallengeAction,
        Action pExchangeAction, Action pPassAction, Action pEndGameAction,
        Action pStartNewGameAction) {
        setLayout(new CardLayout());
        Dimension size = new JButton("Sample").getPreferredSize();
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, size.height));

        add(new FixedToolBar(), NO_BUTTONS);

        JToolBar joiningToolBar = new FixedToolBar();
        joiningToolBar.add(pJoinAction);
        joiningToolBar.add(pStartAction);
        add(joiningToolBar, JOINING);

        JToolBar playingToolBar = new FixedToolBar();
        playingToolBar.add(pFinishAction);
        playingToolBar.add(pApproveAction);
        playingToolBar.add(pChallengeAction);
        playingToolBar.add(pOverruleChallengeAction);
        playingToolBar.add(pSustainChallengeAction);
        playingToolBar.add(pExchangeAction);
        playingToolBar.add(pPassAction);
        playingToolBar.add(pEndGameAction);
        add(playingToolBar, PLAYING);

        JToolBar endedToolBar = new FixedToolBar();
        endedToolBar.add(pStartNewGameAction);
        add(endedToolBar, ENDED);

        show(NO_BUTTONS);

    }

    private static class FixedToolBar extends JToolBar {
        private FixedToolBar() {
            setBorder(BorderFactory.createEmptyBorder());
            setFloatable(false);
        }
    }

    // Methods

    public String getCard() {
        return mCard;
    }

    public final void show(String pCard) {
        mCard = pCard;
        ((CardLayout) getLayout()).show(this, pCard);
    }

}
