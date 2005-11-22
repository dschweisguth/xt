package org.schweisguth.xttest.client.error;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.finder.Finder;
import junit.framework.Protectable;
import org.schweisguth.xt.client.error.ErrorDialog;
import org.schweisguth.xt.common.util.exception.ChainedRuntimeException;

public class ErrorDialogTest extends JFCTestCase {
    // Fields: static final

    /**
     * String used for message arguments
     */
    private static final String MESSAGE = "message";

    /**
     * String used for (non-nested) exception messages
     */
    private static final String EXCEPTION_MESSAGE = "exception message";

    /**
     * (Non-nested) exception used for cause arguments
     */
    private static final Throwable EXCEPTION =
        new Exception(EXCEPTION_MESSAGE);

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new ChainedRuntimeException(e);
        }
    }

    // Fields: instance

    /**
     * A JFCTestHelper, initialized in setUp, that can be used in any test.
     */
    private JFCTestHelper mJFCTestHelper;

    // Methods: overrides

    protected void setUp() {
        mJFCTestHelper = new JFCTestHelper();
    }

    // Methods: test

    /**
     * Tests the dialog and clicks Close.
     */
    public void testCreateStringClose() throws Throwable {
        testWithClose(
            new Protectable() {
                public void protect() {
                    new ErrorDialog(MESSAGE).show();
                }
            },
            MESSAGE
        );
    }

    /**
     * Like testCreateStringClose, but uses a null String
     */
    public void testCreateStringNullClose() throws Throwable {
        testWithClose(
            new Protectable() {
                public void protect() {
                    new ErrorDialog((String) null).show();
                }
            },
            "null"
        );
    }

    /**
     * Tests compilation of the corresponding API method
     */
    public void testCreateStringDialogClose() {
        new ErrorDialog(new JDialog(), MESSAGE);
    }

    /**
     * Tests compilation of the corresponding API method
     */
    public void testCreateStringFrameClose() {
        new ErrorDialog(new JFrame(), MESSAGE);
    }

    /**
     * Tests the dialog and clicks Close.
     */
    public void testCreateThrowableClose() throws Throwable {
        testWithClose(
            new Protectable() {
                public void protect() {
                    new ErrorDialog(EXCEPTION).show();
                }
            },
            EXCEPTION_MESSAGE
        );
    }

    /**
     * Tests the dialog, clicks Details, and tests the details dialog.
     */
    public void testCreateThrowableDetails() throws Throwable {
        testWithDetails(
            new Protectable() {
                public void protect() {
                    new ErrorDialog(EXCEPTION).show();
                }
            },
            EXCEPTION_MESSAGE,
            detailsMessage(EXCEPTION)
        );
    }

    /**
     * Like testCreateThrowableClose, but uses a null exception
     */
    public void testCreateThrowableNullClose() throws Throwable {
        testWithClose(
            new Protectable() {
                public void protect() {
                    new ErrorDialog((Throwable) null).show();
                }
            },
            "Tried to display a null Throwable"
        );
    }

    // No testCreateThrowableNullDetails, since I can't think of a way to
    // predict the details message in test code

    /**
     * Tests compilation of the corresponding API method
     */
    public void testCreateDialogThrowable() {
        new ErrorDialog(new JDialog(), EXCEPTION);
    }

    /**
     * Tests compilation of the corresponding API method
     */
    public void testCreateFrameThrowable() {
        new ErrorDialog(new JFrame(), EXCEPTION);
    }

    /**
     * Like testCreateThrowableClose, but adds a message String.
     */
    public void testCreateStringThrowableClose() throws Throwable {
        testWithClose(
            new Protectable() {
                public void protect() {
                    new ErrorDialog(MESSAGE, EXCEPTION).show();
                }
            },
            MESSAGE
        );
    }

    /**
     * Like testCreateThrowableDetails, but adds a message String
     */
    public void testCreateStringThrowableDetails() throws Throwable {
        testWithDetails(
            new Protectable() {
                public void protect() {
                    new ErrorDialog(MESSAGE, EXCEPTION).show();
                }
            },
            MESSAGE,
            detailsMessage(MESSAGE, EXCEPTION)
        );
    }

    /**
     * Like testCreateStringThrowableClose, but uses a null string
     */
    public void testCreateStringNullThrowableClose() throws Throwable {
        testWithClose(
            new Protectable() {
                public void protect() {
                    new ErrorDialog((String) null, EXCEPTION).show();
                }
            },
            "null"
        );
    }

    /**
     * Like testCreateStringThrowableDetails, but uses a null string
     */
    public void testCreateStringNullThrowableDetails() throws Throwable {
        testWithDetails(
            new Protectable() {
                public void protect() {
                    new ErrorDialog((String) null, EXCEPTION).show();
                }
            },
            "null",
            detailsMessage("null", EXCEPTION)
        );
    }

    /**
     * Like testCreateStringThrowableClose, but uses a null exception
     */
    public void testCreateStringThrowableNullClose() throws Throwable {
        testWithClose(
            new Protectable() {
                public void protect() {
                    new ErrorDialog(MESSAGE, null).show();
                }
            },
            MESSAGE
        );
    }

    // No testCreateStringThrowableNullDetails, since I can't think of a way to
    // predict the details message in test code

    /**
     * Tests compilation of the corresponding API method
     */
    public void testCreateDialogStringThrowable() {
        new ErrorDialog(new JDialog(), MESSAGE, EXCEPTION);
    }

    /**
     * Tests compilation of the corresponding API method
     */
    public void testCreateFrameStringThrowable() {
        new ErrorDialog(new JFrame(), MESSAGE, EXCEPTION);
    }

    // Methods: helper

    /**
     * Returns the expected details message for the given cause.
     */
    private static String detailsMessage(Throwable pCause) {
        return detailsMessage(pCause.getMessage(), pCause);
    }

    /**
     * Returns the expected details message for the given message and cause.
     */
    private static String detailsMessage(String pMessage, Throwable pCause) {
        return pMessage + System.getProperty("line.separator") +
            getStackTrace(pCause);
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

    /**
     * Runs the Protectable in a separate thread, asserts that it opened a
     * dialog with the given message, and clicks Close on that dialog.
     */
    private void testWithClose(Protectable pAction, final String pMessage)
        throws Throwable {
        forkAndTest(
            pAction,
            new Protectable() {
                public void protect() throws Exception {
                    testErrorDialogAndClick(pMessage, "Close");
                }
            }
        );
    }

    /**
     * Runs the Protectable in a separate thread, asserts that it opened a
     * dialog with the given message, clicks Details on that dialog, asserts
     * that doing so opened a dialog with the given details message, and clicks
     * Close on that dialog.
     */
    private void testWithDetails(Protectable pAction, final String pMessage,
        final String pDetailsMessage) throws Throwable {
        forkAndTest(
            pAction,
            new Protectable() {
                public void protect() throws Exception {
                    // Test first dialog and click Details
                    testErrorDialogAndClick(pMessage, "Details");

                    // Test second dialog
                    Container detailsDialog = waitForDialog();
                    JTextPane textPane = (JTextPane) TestHelper.findComponent(
                        JTextPane.class, detailsDialog, 0);
                    assertNotNull("Details dialog has no text pane", textPane);
                    assertEquals(pDetailsMessage, textPane.getText());

                    // Click Close
                    clickButton(detailsDialog, "Close");

                }
            }
        );
    }

    /**
     * Asserts that a dialog with the given message has been opened, then
     * clicks the given button.
     */
    private void testErrorDialogAndClick(String pMessage, String pButtonText)
        throws Exception {
        // Test dialog
        Container errorDialog = waitForDialog();
        JOptionPane optionPane = (JOptionPane) TestHelper.findComponent(
            JOptionPane.class, errorDialog, 0);
        assertNotNull("Error dialog has no option pane", optionPane);
        assertEquals(pMessage, optionPane.getMessage());

        // Click button
        clickButton(errorDialog, pButtonText);

    }

    /**
     * Waits for a dialog to appear and returns it. Asserts that the dialog
     * appears in less than ten seconds and that only one dialog appears.
     */
    private static Container waitForDialog() throws InterruptedException {
        Thread.yield();
        List dialogs;
        int timeWaited = 0;
        while (true) {
            dialogs = TestHelper.getShowingDialogs();
            if (dialogs.size() > 0) {
                break;
            }
            assertTrue("Dialog didn't appear for 10 seconds", timeWaited < 10000);
            Thread.sleep(100);
            timeWaited += 100;
        }
        assertEquals(1, dialogs.size());
        return (Container) dialogs.get(0);
    }

    /**
     * Finds the JButton with the given text in the given Container, asserts
     * that it was found (using the given message) and clicks it.
     */
    private void clickButton(Container pContainer, final String pButtonText) {
        JButton button = (JButton) TestHelper.findComponent(
            new Finder() {
                public boolean testComponent(Component pComponent) {
                    return pComponent instanceof JButton &&
                        ((JButton) pComponent).getText().equals(pButtonText);
                }
            },
            pContainer, 0);
        assertNotNull(
            "Container has no button with text \"" + pButtonText + "\"", button);
        mJFCTestHelper.enterClickAndLeave(new MouseEventData(this, button));
    }

    /**
     * Runs the action in a separate thread, runs the test (which is expected
     * to close any dialogs, thus allowing the action thread to exit) and cleans
     * up any open dialogs.
     */
    private void forkAndTest(Protectable pAction, Protectable pTest)
        throws Throwable {
        // Much voodoo here! See JFCUnit's EmployeeTestCase example.
        ProtectiveThread modalThread = new ProtectiveThread(pAction);
        try {
            modalThread.start();
            pTest.protect();
            while (modalThread.isAlive()) {
                Thread.sleep(100);
            }
            Throwable thrown = modalThread.getFirstThrown();
            if (thrown != null) {
                throw thrown;
            }
            // If the action or test threw a Throwable we won't get here, which
            // is fine: either the open dialog was caused by the earlier Throwable
            // or we'll find it after we fix the other Throwable and rerun the
            // test.
            assertEquals("One or more dialogs remained open after the test",
                0, TestHelper.getShowingDialogs().size());
        } finally {
            // Close any dialogs that remain open
            Iterator i = TestHelper.getShowingDialogs().iterator();
            while (i.hasNext()) {
                JFCTestHelper.disposeWindow((Dialog) i.next(), this);
            }

            // Interrupt any threads that remain active
            if (modalThread.isAlive()) {
                modalThread.interrupt();
            }

        }
    }

}
