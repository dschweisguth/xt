package org.schweisguth.xt.client.error;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

/**
 * A dialog with a detailed description of a Throwable.
 */
class DetailsDialog extends JDialog {
    // Fields

    /**
     * The dialog's single text pane
     */
    private final JTextPane mTextPane = new JTextPane();
    // From 1.4
    //{
    //   /**
    //    * Prevent the text pane from getting the initial focus so that the
    //    * button does. This works for the first use of the dialog, but not
    //    * subsequent uses!?!
    //    */
    //   public boolean isFocusable()
    //   {
    //      return false;
    //   }
    //};

    // Constructors

    /**
     * Constructs a dialog with the specified owner.
     */
    public DetailsDialog(Dialog pOwner) {
        super(pOwner);
        initGUI();
    }

    /**
     * Constructs a dialog with the specified owner.
     */
    public DetailsDialog(Frame pOwner) {
        super(pOwner);
        initGUI();
    }

    // Methods: initialization

    /**
     * Initializes the GUI.
     */
    private void initGUI() {
        // Text pane
        mTextPane.setAutoscrolls(false);
        mTextPane.setBackground(null);
        mTextPane.setEditable(false);
        mTextPane.setMinimumSize(new Dimension(770, 270));
        mTextPane.setPreferredSize(mTextPane.getMinimumSize());

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setMinimumSize(new Dimension(774, 272));
        scrollPane.setPreferredSize(scrollPane.getMinimumSize());
        scrollPane.getViewport().add(mTextPane, null);

        // Scroll pane panel
        JPanel scrollPanePanel = new JPanel(new BorderLayout());
        scrollPanePanel.setBorder(BorderFactory.createRaisedBevelBorder());
        scrollPanePanel.add(scrollPane, BorderLayout.CENTER);

        // Button
        final JButton closeButton =
            new JButton("Close");
        // Set up the dialog to give the button focus when the dialog is shown.
        // This doesn't work for the first use of the dialog, but does for
        // subsequent uses!?!
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent pEvent) {
                closeButton.requestFocus();
            }
        });
        // Set up the button to dispose the dialog when clicked
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent pEvent) {
                dispose();
            }
        });

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.add(scrollPanePanel,
            new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        mainPanel.add(closeButton,
            new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 8), 0, 0));

        // Dialog
        setTitle("Error Details");
        getContentPane().add(mainPanel);
        pack();
        PositionManager.manage(this);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

    // -------------------------------------------------------------------------
    // Methods: other
    // -------------------------------------------------------------------------

    /**
     * Sets the text pane's text.
     */
    public void setText(String pText) {
        mTextPane.setText(pText);
    }

}
