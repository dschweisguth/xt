package org.schweisguth.xt.client;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import org.schweisguth.xt.client.letterdistribution.LetterDistributionAction;
import org.schweisguth.xt.client.prefs.Preferences;
import org.schweisguth.xt.client.util.Constants;
import org.schweisguth.xt.client.util.JBox;
import org.schweisguth.xt.client.util.LocationManager;
import org.schweisguth.xt.client.util.MainFrameHolder;
import org.schweisguth.xt.common.util.contract.Assert;

// TODO minimize refreshes by comparing domain objects

public class ClientView extends JFrame {
    // Fields: constants
    private static final int TEXT_FONT_HEIGHT =
        new JEditorPane().getFontMetrics(
            new JEditorPane().getFont()).getHeight();
    private static final String SPLIT_PANE_DIVIDER_LOCATION =
        "splitPaneDividerLocation";

    // Fields: instance
    private final Action mExitAction;
    private final JSplitPane mSplitPane =
        new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    // Constructors

    public ClientView(JComponent pToolBar, Component pLeftPanel,
        Component pBoxLidView, Component pScoreView, Component pChatDisplayView,
        JComponent pChatSendView, Action pExitAction) {
        Assert.assertNotNull(pToolBar);
        Assert.assertNotNull(pLeftPanel);
        Assert.assertNotNull(pBoxLidView);
        Assert.assertNotNull(pScoreView);
        Assert.assertNotNull(pChatDisplayView);
        Assert.assertNotNull(pChatSendView);
        Assert.assertNotNull(pExitAction);

        mExitAction = pExitAction;
        initMenuBar();
        initContentPane(pToolBar, pLeftPanel, pBoxLidView, pScoreView,
            pChatDisplayView, pChatSendView);
        setTitle("CrossTease");
        setResizable(false);
        LocationManager.manage(this);
        loadSplitPaneDividerLocations();
        MainFrameHolder.instance().setMainFrame(this);

    }

    // Methods: initialization

    private void initMenuBar() {
        // File menu exit item
        JMenuItem exitItem = new JMenuItem(mExitAction);
        exitItem.setMnemonic('x');

        // File menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        fileMenu.add(exitItem);

        // Help menu letter distribution item
        JMenuItem numbersOfTilesItem =
            new JMenuItem(new LetterDistributionAction());
        numbersOfTilesItem.setMnemonic('L');

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        helpMenu.add(numbersOfTilesItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

    }

    private void initContentPane(JComponent pToolBar, Component pLeftPanel,
        Component pBoxLidView, Component pScoreView, Component pChatDisplayView,
        JComponent pChatSendView) {
        pToolBar.setAlignmentX(0);

        JSeparator separator = new JSeparator();
        separator.setAlignmentX(0);

        JBox rightPanel = JBox.createVerticalBox();
        rightPanel.add(pBoxLidView);
        JScrollPane scoreScrollPane = new JScrollPane(pScoreView);
        setPreferredYSize(scoreScrollPane, 10 * TEXT_FONT_HEIGHT);
        scoreScrollPane.getViewport().setBackground(pScoreView.getBackground());
        JScrollPane chatScrollPane = new JScrollPane(pChatDisplayView);
        setPreferredYSize(chatScrollPane, 10 * TEXT_FONT_HEIGHT);
        mSplitPane.setAlignmentX(0.5f);
        mSplitPane.setAlignmentY(0.5f);
        mSplitPane.setResizeWeight(0.5);
        mSplitPane.addPropertyChangeListener(
            JSplitPane.DIVIDER_LOCATION_PROPERTY,
            new DividerPositionSaveListener());
        mSplitPane.add(scoreScrollPane);
        mSplitPane.add(chatScrollPane);
        rightPanel.add(mSplitPane);
        setYSize(pChatSendView, (int) (1.5 * TEXT_FONT_HEIGHT));
        rightPanel.add(pChatSendView);
        setXSize(rightPanel, Constants.BOARD_SIZE);

        JBox bottomPanel = JBox.createHorizontalBox();
        bottomPanel.setAlignmentX(0);
        bottomPanel.add(pLeftPanel);
        bottomPanel.add(rightPanel);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(pToolBar);
        contentPane.add(new JSeparator());
        contentPane.add(bottomPanel);

        pack();

    }

    private static void setPreferredYSize(JComponent pComponent,
        int pPreferredYSize) {
        Dimension prefSize = pComponent.getPreferredSize();
        prefSize.height = pPreferredYSize;
        pComponent.setPreferredSize(prefSize);
        Dimension minSize = pComponent.getMinimumSize();
        minSize.height = Math.min(minSize.height, pPreferredYSize);
        pComponent.setMinimumSize(minSize);
        Dimension maxSize = pComponent.getMaximumSize();
        maxSize.height = Math.max(maxSize.height, pPreferredYSize);
        pComponent.setMaximumSize(maxSize);
    }

    private static void setYSize(JComponent pComponent, int pYSize) {
        Dimension prefSize = pComponent.getPreferredSize();
        prefSize.height = pYSize;
        pComponent.setPreferredSize(prefSize);
        Dimension minSize = pComponent.getMinimumSize();
        minSize.height = pYSize;
        pComponent.setMinimumSize(minSize);
        Dimension maxSize = pComponent.getMaximumSize();
        maxSize.height = pYSize;
        pComponent.setMaximumSize(maxSize);
    }

    private static void setXSize(JComponent pComponent, int pXSize) {
        Dimension prefSize = pComponent.getPreferredSize();
        prefSize.width = pXSize;
        pComponent.setPreferredSize(prefSize);
        Dimension minSize = pComponent.getMinimumSize();
        minSize.width = pXSize;
        pComponent.setMinimumSize(minSize);
        Dimension maxSize = pComponent.getMaximumSize();
        maxSize.width = pXSize;
        pComponent.setMaximumSize(maxSize);
    }

    private class DividerPositionSaveListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent pEvent) {
            Preferences prefs = Preferences.userNodeForPackage(getClass());
            prefs.putInt(SPLIT_PANE_DIVIDER_LOCATION,
                mSplitPane.getDividerLocation());
        }
    }

    private void loadSplitPaneDividerLocations() {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        mSplitPane.setDividerLocation(prefs.getInt(SPLIT_PANE_DIVIDER_LOCATION,
            (mSplitPane.getMinimumDividerLocation() +
                mSplitPane.getMaximumDividerLocation()) / 2));
    }

    // Overrides

    protected void processWindowEvent(WindowEvent pEvent) {
        super.processWindowEvent(pEvent);
        if (pEvent.getID() == WindowEvent.WINDOW_CLOSING) {
            mExitAction.actionPerformed(null);
        }
    }

}
