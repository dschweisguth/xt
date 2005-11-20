package org.schweisguth.xt.client;

import org.schweisguth.xt.client.prefs.Preferences;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class LogInDialog extends JDialog {
    // Constants
    private static final String HOST = "host";
    private static final String PLAYER = "player";

    // Fields
    private final JTextField mHostNameField = new JTextField();
    private final JTextField mUserNameField = new JTextField();
    private final JButton mLogInButton = new JButton("Connect");
    private boolean mWasCancelled = true;

    // Constructors

    public LogInDialog(Frame pOwner) {
        super(pOwner, "Connect to the Game", true);

        JLabel hostLabel = new JLabel("Server: ");
        Dimension hostLabelSize = hostLabel.getPreferredSize();
        JLabel userLabel = new JLabel("Player: ");
        Dimension userLabelSize = userLabel.getPreferredSize();
        hostLabelSize.width = Math.max(hostLabelSize.width, userLabelSize.width);

        hostLabel.setPreferredSize(hostLabelSize);
        hostLabel.setMaximumSize(hostLabelSize);
        hostLabel.setMinimumSize(hostLabelSize);
        userLabel.setPreferredSize(hostLabelSize);
        userLabel.setMaximumSize(hostLabelSize);
        userLabel.setMinimumSize(hostLabelSize);

        clear();
        Dimension fieldSize = mHostNameField.getPreferredSize();
        fieldSize.width = 150;
        DocumentListener updatingDocumentListener = new DocumentListener() {
            public synchronized void insertUpdate(DocumentEvent e) {
                updateControls();
            }

            public synchronized void removeUpdate(DocumentEvent e) {
                updateControls();
            }

            public void changedUpdate(DocumentEvent e) {
            }

        };
        KeyListener buttonPressingKeyListener = new KeyAdapter() {
            public synchronized void keyReleased(KeyEvent pEvent) {
                if (pEvent.getKeyChar() == KeyEvent.VK_ENTER &&
                    mLogInButton.isEnabled()) {
                    logIn();
                } else if (pEvent.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }
        };

        mHostNameField.setName("hostNameField");
        mHostNameField.getDocument().addDocumentListener(
            updatingDocumentListener);
        mHostNameField.addKeyListener(buttonPressingKeyListener);
        mHostNameField.setPreferredSize(fieldSize);
        mHostNameField.setMaximumSize(fieldSize);
        mHostNameField.setMinimumSize(fieldSize);

        mUserNameField.setName("userNameField");
        mUserNameField.setSelectionStart(0);
        mUserNameField.setSelectionEnd(mUserNameField.getText().length());
        mUserNameField.getDocument().addDocumentListener(
            updatingDocumentListener);
        mUserNameField.addKeyListener(buttonPressingKeyListener);
        mUserNameField.setPreferredSize(fieldSize);
        mUserNameField.setMaximumSize(fieldSize);
        mUserNameField.setMinimumSize(fieldSize);
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent pEvent) {
                mUserNameField.requestFocus();
            }
        });

        JPanel hostPanel = new JPanel();
        hostPanel.add(hostLabel);
        hostPanel.add(mHostNameField);

        JPanel userPanel = new JPanel();
        userPanel.add(userLabel);
        userPanel.add(mUserNameField);

        Box topPanel = Box.createVerticalBox();
        topPanel.add(hostPanel);
        topPanel.add(userPanel);

        mLogInButton.setName("logInButton");
        mLogInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logIn();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setName("cancelButton");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent pEvent) {
                dispose();
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(mLogInButton);
        buttonsPanel.add(cancelButton);

        Box mainPanel = Box.createVerticalBox();
        mainPanel.add(topPanel);
        mainPanel.add(buttonsPanel);
        getContentPane().add(mainPanel);
        pack();

        // Center on parent frame or, if there is none, screen
        Point parentLocation;
        Dimension parentSize;
        if (pOwner == null) {
            parentLocation = new Point(0, 0);
            parentSize = Toolkit.getDefaultToolkit().getScreenSize();
        } else {
            parentLocation = pOwner.getLocation();
            parentSize = pOwner.getSize();
        }
        Dimension dialogSize = getSize();
        setLocation(
            parentLocation.x + (parentSize.width - dialogSize.width) / 2,
            parentLocation.y + (parentSize.height - dialogSize.height) / 2
        );

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        updateControls();
    }

    // Methods: controller

    private void logIn() {
        mWasCancelled = false;
        dispose();
    }

    private void updateControls() {
        mLogInButton.setEnabled(
            getHostName().length() > 0 && getUserName().length() > 0);
    }

    // Methods: overrides

    public void show() {
        mWasCancelled = true;
        super.show();
    }

    // Methods: getters

    public boolean wasCancelled() {
        return mWasCancelled;
    }

    public String getHostName() {
        return mHostNameField.getText();
    }

    public String getUserName() {
        return mUserNameField.getText();
    }

    // Methods: other

    public final void clear() {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        // TODO why can't Grandma see crosstease.com?
        mHostNameField.setText(prefs.get(HOST, "schweisguth.org"));
        mUserNameField.setText(prefs.get(PLAYER, ""));
    }

    public void setPrefs() {
        Preferences prefs = Preferences.userNodeForPackage(LogInDialog.class);
        prefs.put(HOST, getHostName());
        prefs.put(PLAYER, getUserName());
    }

}
