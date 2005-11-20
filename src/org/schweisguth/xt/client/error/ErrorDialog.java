package org.schweisguth.xt.client.error;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 * A dialog which displays an error message and, optionally, a details button
 * which brings up a second dialog with details of the error.
 */
public class ErrorDialog extends JDialog {
    // Fields: static final
    private static final String ERROR_DIALOG_CLOSE_BUTTON_NAME = "Close";
    private static final String ERROR_DIALOG_DETAILS_BUTTON_NAME = "Details";

    // Fields: instance

    /**
     * The error message. Never null.
     */
    private String mMessage;

    /**
     * The cause of the error message. Null if the error message has no cause.
     */
    private Throwable mCause;

    /**
     * This dialog's contents
     */
    private JOptionPane mOptionPane = null;

    /**
     * The details dialog. Used only by displayDetailsDialog().
     */
    private DetailsDialog mDetailsDialog = null;

    // Constructors

    /**
     * Constructs a modal dialog with the default owner.
     * @param pMessage The error message to display. If null, the dialog will
     * display a default message.
     * @param pCause The error to display on the details dialog. If null, the
     * details dialog will display a NullCauseException.
     */
    public ErrorDialog(String pMessage, Throwable pCause) {
        super((Frame) null);
        init(pMessage, pCause);
    }

    /**
     * Constructs a modal dialog with the default owner and no details button.
     * @param pMessage The error message to display. If null, the dialog will
     * display a default message.
     */
    public ErrorDialog(String pMessage) {
        super((Frame) null);
        init(pMessage);
    }

    /**
     * Constructs a modal dialog with the given owner and a message taken from
     * the given error.
     * @param pCause The error to display on the details dialog. If null, the
     * details dialog will display a NullCauseException.
     */
    public ErrorDialog(Throwable pCause) {
        super((Frame) null);
        init(pCause);
    }

    /**
     * Constructs a modal dialog with the given owner.
     * @param pOwner The owner to which to attach this dialog.
     * @param pMessage The error message to display. If null, the dialog will
     * display a default message.
     * @param pCause The error to display on the details dialog. If null, the
     * details dialog will display a NullCauseException.
     */
    public ErrorDialog(Dialog pOwner, String pMessage, Throwable pCause) {
        super(pOwner);
        init(pMessage, pCause);
    }

    /**
     * Constructs a modal dialog with the default owner and no details button.
     * @param pOwner The owner to which to attach this dialog.
     * @param pMessage The error message to display. If null, the dialog will
     * display a default message.
     */
    public ErrorDialog(Dialog pOwner, String pMessage) {
        super(pOwner);
        init(pMessage);
    }

    /**
     * Constructs a modal dialog with the given owner and a message taken from
     * the given error.
     * @param pOwner The owner to which to attach this dialog.
     * @param pCause The error to display on the details dialog. If null, the
     * details dialog will display a NullCauseException.
     */
    public ErrorDialog(Dialog pOwner, Throwable pCause) {
        super(pOwner);
        init(pCause);
    }

    /**
     * Constructs a modal dialog with the given owner.
     * @param pOwner The owner to which to attach this dialog.
     * @param pMessage The error message to display. If null, the dialog will
     * display a default message.
     * @param pCause The error to display on the details dialog. If null, the
     * details dialog will display a NullCauseException.
     */
    public ErrorDialog(Frame pOwner, String pMessage, Throwable pCause) {
        super(pOwner);
        init(pMessage, pCause);
    }

    /**
     * Constructs a modal dialog with the default owner and no details button.
     * @param pOwner The owner to which to attach this dialog.
     * @param pMessage The error message to display. If null, the dialog will
     * display a default message.
     */
    public ErrorDialog(Frame pOwner, String pMessage) {
        super(pOwner);
        init(pMessage);
    }

    /**
     * Constructs a modal dialog with the given owner and a message taken from
     * the given error.
     * @param pOwner The owner to which to attach this dialog.
     * @param pCause The error to display on the details dialog. If null, the
     * details dialog will display a NullCauseException.
     */
    public ErrorDialog(Frame pOwner, Throwable pCause) {
        super(pOwner);
        init(pCause);
    }

    // Methods: initialization

    /**
     * Initializes the dialog with a message and cause.
     */
    private void init(String pMessage, Throwable pCause) {
        initGUI();
        setInfo(pMessage, pCause);
    }

    /**
     * Initializes the dialog with a message but no cause.
     */
    private void init(String pMessage) {
        initGUI();
        setInfo(pMessage);
    }

    /**
     * Initializes the dialog with a cause but no message.
     */
    private void init(Throwable pCause) {
        initGUI();
        setInfo(pCause);
    }

    /**
     * Initializes GUI elements which don't depend on the message and cause.
     */
    private void initGUI() {
        getContentPane().setLayout(new BorderLayout());
        setTitle("Error");
        // Not only should the dialog not be resizable, but making it not
        // resizable gets rid of the coffee-cup icon per Honebein's standard.
        setResizable(false);
        setModal(true); // Clients are free to set this to false later
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    // Methods: overrides

    /**
     * After the error dialog closes, displays the details dialog if the
     * error dialog's Details button was clicked.
     */
    public void show() {
        super.show();
        if (mOptionPane.getValue() != null &&
            mOptionPane.getValue().equals(ERROR_DIALOG_DETAILS_BUTTON_NAME)) {
            displayDetailsDialog();
        }
    }

    /**
     * Displays the details dialog.
     */
    private void displayDetailsDialog() {
        // Build the dialog if we haven't already
        if (mDetailsDialog == null) {
            // Attach the details dialog to our owner, not to us
            Window owner = getOwner();
            mDetailsDialog = owner instanceof Dialog
                ? new DetailsDialog((Dialog) owner)
                : new DetailsDialog((Frame) owner);
        }

        // Configure and show the dialog
        StringBuffer text = new StringBuffer(mMessage);
        if (mCause != null) {
            text.append(System.getProperty("line.separator"));
            text.append(getStackTrace(mCause));
        }
        mDetailsDialog.setText(text.toString());
        mDetailsDialog.setModal(isModal());
        mDetailsDialog.show();

    }

    /**
     * Copies the stack trace of the given Throwable to a string.
     */
    private static String getStackTrace(Throwable pThrowable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pThrowable.printStackTrace(pw);
        return sw.toString();
    }

    // Methods: other

    /**
     * Sets the message and cause.
     * @param pMessage The error message to display. If null, the dialog will
     * display a default message.
     * @param pCause The error to display on the details dialog. If null, the
     * details dialog will display a NullCauseException.
     */
    private void setInfo(String pMessage, Throwable pCause) {
        mMessage = interceptNull(pMessage);
        mCause = interceptNull(pCause);
        setGUIForInfo();
    }

    /**
     * Sets the message and clears the cause.
     * @param pMessage The error message to display. If null, the dialog will
     * display a default message.
     */
    private void setInfo(String pMessage) {
        mMessage = interceptNull(pMessage);
        mCause = null;
        setGUIForInfo();
    }

    /**
     * Sets the cause and sets the message to a message taken from the cause.
     * @param pCause The error to display on the details dialog. If null, the
     * details dialog will display a NullCauseException.
     */
    private void setInfo(Throwable pCause) {
        mCause = interceptNull(pCause);
        mMessage = interceptNull(mCause.getMessage());
        setGUIForInfo();
    }

    /**
     * Returns the input if it is not null or a default message if the input is
     * null.
     */
    private static String interceptNull(String pMessage) {
        return pMessage == null ? "null" : pMessage;
    }

    /**
     * Returns the input if it is not null or a NullCauseException if the input
     * is null.
     */
    private static Throwable interceptNull(Throwable pThrowable) {
        return pThrowable == null
            ? new NullCauseException("Tried to display a null Throwable")
            : pThrowable;
    }

    /**
     * Sets up GUI elements which depend on the message and cause.
     */
    private void setGUIForInfo() {
        // Build a new ErrorOptionPane every time. One might think that we should
        // construct the ErrorOptionPane once and use setMessage and setOptions
        // to update it whenever the message and cause change, but doing so
        // results in an ErrorOptionPane which ignores the button which was
        // previously clicked.
        Container contentPane = getContentPane();
        if (mOptionPane != null) {
            contentPane.remove(mOptionPane);
        }
        mOptionPane = new ErrorOptionPane();
        contentPane.add(mOptionPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
    }

    /**
     * A JOptionPane which displays an error message and an optional details
     * button, and which wraps its message at a fixed numnber of characters
     * per line
     */
    private class ErrorOptionPane extends JOptionPane {
        /**
         * Constructs a pane with the given owner
         */
        private ErrorOptionPane() {
            super(mMessage, JOptionPane.ERROR_MESSAGE,
                JOptionPane.DEFAULT_OPTION);
            Object[] options = mCause != null
                ? new Object[] { ERROR_DIALOG_CLOSE_BUTTON_NAME,
                ERROR_DIALOG_DETAILS_BUTTON_NAME }
                : new Object[] { ERROR_DIALOG_CLOSE_BUTTON_NAME };
            setOptions(options);
            setInitialValue(ERROR_DIALOG_CLOSE_BUTTON_NAME);

            // Close the enclosing dialog when a button is pressed
            addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent pEvent) {
                    if (isVisible() && pEvent.getSource() == mOptionPane &&
                        (pEvent.getPropertyName()
                            .equals(JOptionPane.VALUE_PROPERTY) ||
                            pEvent.getPropertyName()
                                .equals(JOptionPane.INPUT_VALUE_PROPERTY))) {
                        dispose();
                    }
                }

            });

        }

        /**
         * Tells parent code to wrap message at some number of characters per
         * line
         * @return the number of characters per line
         */
        public int getMaxCharactersPerLineCount() {
            return 75;
        }

    }

}
