package org.schweisguth.xt.client.boxlid;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import org.schweisguth.xt.client.observable.Observer;
import org.schweisguth.xt.client.util.CardView;
import org.schweisguth.xt.client.util.CenteredLabel;
import org.schweisguth.xt.client.util.Constants;
import org.schweisguth.xt.client.util.JBox;
import org.schweisguth.xt.common.util.contract.Assert;

public class BoxLidView extends JPanel implements Observer, CardView {
    // Constants
    public static final String NO_BUTTON = "noButton";
    public static final String DRAW_FOR_FIRST = "drawForFirst";
    public static final String DRAW_STARTING_TILES = "drawStartingTiles";
    public static final String DRAW_NEW_TILES = "drawNewTiles";
    private static final Color BOX_LID_RIM_COLOR = new Color(193, 43, 43);

    // Fields
    private final BoxLidModel mModel;
    private final CenteredLabel mLabel = new CenteredLabel();
    private final JPanel mButtonPanel = new JPanel(new CardLayout());
    private String mCard;

    // Constructors

    public BoxLidView(BoxLidModel pModel, Action pDrawForFirstAction,
        Action pDrawStartingTilesAction, Action pDrawNewTilesAction) {
        Assert.assertNotNull(pModel);
        Assert.assertNotNull(pDrawForFirstAction);
        Assert.assertNotNull(pDrawStartingTilesAction);
        Assert.assertNotNull(pDrawNewTilesAction);

        mModel = pModel;
        mModel.addObserver(this);
        update();

        setLayout(new OverlayLayout(this));
        add(createControlPanel(pDrawForFirstAction, pDrawStartingTilesAction,
            pDrawNewTilesAction));
        add(createBackgroundPanel());
        Dimension size =
            new Dimension(Constants.BOARD_SIZE, Constants.BOARD_SIZE * 2 / 5);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
        setSize(size);
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        show(NO_BUTTON);

    }

    private JBox createControlPanel(Action pDrawForFirstAction,
        Action pDrawStartingTilesAction, Action pDrawNewTilesAction) {
        JBox panel = new JBox(BoxLayout.Y_AXIS);

        JPanel labelPanel = JBox.createHorizontalBox();
        mLabel.setOpaque(true);
        mLabel.setBackground(Constants.TILE_BACKGROUND_COLOR);
        labelPanel.add(mLabel);
        labelPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(4, 4, 4, 4),
            BorderFactory.createLineBorder(Constants.TILE_BORDER_COLOR)));
        labelPanel.setOpaque(false);
        panel.add(labelPanel);

        JPanel noButtonCard = new JPanel();
        noButtonCard.setOpaque(false);
        mButtonPanel.add(noButtonCard, NO_BUTTON);

        JButton drawForFirstButton = new JButton(pDrawForFirstAction);
        Dimension maxButtonPreferredSize = drawForFirstButton.getPreferredSize();
        mButtonPanel.add(drawForFirstButton, DRAW_FOR_FIRST);

        JButton drawStartingTilesButton = new JButton(pDrawStartingTilesAction);
        maxButtonPreferredSize = envelope(maxButtonPreferredSize,
            drawStartingTilesButton.getPreferredSize());
        mButtonPanel.add(drawStartingTilesButton, DRAW_STARTING_TILES);

        JButton drawNewTilesButton = new JButton(pDrawNewTilesAction);
        maxButtonPreferredSize = envelope(maxButtonPreferredSize,
            drawNewTilesButton.getPreferredSize());
        mButtonPanel.add(drawNewTilesButton, DRAW_NEW_TILES);

        mButtonPanel.setMaximumSize(maxButtonPreferredSize);
        mButtonPanel.setOpaque(false);
        panel.add(mButtonPanel);

        panel.setOpaque(false);
        return panel;

    }

    private static Dimension envelope(Dimension pOne, Dimension pOther) {
        return new Dimension(
            Math.max(pOne.width, pOther.width),
            Math.max(pOne.height, pOther.height));
    }

    private static JBox createBackgroundPanel() {
        JBox panel = JBox.createHorizontalBox();
        panel.add(Box.createHorizontalGlue());
        panel.add(new GameRulesPanel());
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BOX_LID_RIM_COLOR, 4),
            BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(4, 4, 4, 4),
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.black),
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.white),
                        BorderFactory.createLineBorder(Color.black))))));
        return panel;
    }

    private static class GameRulesPanel extends JBox {
        private GameRulesPanel() {
            super(BoxLayout.Y_AXIS);
            add(Box.createVerticalGlue());
            add(new GameRulesText());
            add(Box.createVerticalGlue());
            setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            setOpaque(false);
        }
    }

    private static class GameRulesText extends JPanel {
        private static final String TEXT = "GAME RULES";

        public GameRulesText() {
            FontMetrics fontMetrics =
                new JPanel().getFontMetrics(Constants.GAME_RULES_FONT);
            Rectangle2D bounds = fontMetrics.getStringBounds(TEXT, getGraphics());
            Dimension size = new Dimension(
                (int) bounds.getHeight(),
                (int) bounds.getWidth() + Constants.GAME_RULES_FONT.getSize() / 2);
            setMaximumSize(size);
            setMinimumSize(size);
            setPreferredSize(size);
            setSize(size);
            setOpaque(false);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.rotate(3.1416 / 2);
            g2.setFont(Constants.GAME_RULES_FONT);
            g2.drawString(TEXT, 0, 0);
            g2.dispose();
        }

    }

    // Methods: implements Observer

    public final void update() {
        int tileCount = mModel.getTileCount();
        mLabel.setText(
            tileCount + " tile" + (tileCount == 1 ? "" : "s") + " left");
    }

    // Methods: other

    public String getCard() {
        return mCard;
    }

    public BoxLidModel getModel() {
        return mModel;
    }

    public final void show(String pCard) {
        mCard = pCard;
        ((CardLayout) mButtonPanel.getLayout()).show(mButtonPanel, pCard);
    }

}
