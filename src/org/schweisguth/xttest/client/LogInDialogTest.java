package org.schweisguth.xttest.client;

import javax.swing.JButton;
import javax.swing.JTextField;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.TestHelper;
import org.schweisguth.xt.client.LogInDialog;

public class LogInDialogTest extends JFCTestCase {
    public void testEmptyEmpty() {
        assertOKButtonState("", "", false);
    }

    public void testFullEmpty() {
        assertOKButtonState("x", "", false);
    }

    public void testEmptyFull() {
        assertOKButtonState("", "x", false);
    }

    public void testFullFull() {
        assertOKButtonState("x", "x", true);
    }

    private static void assertOKButtonState(String pHostName, String pUserName,
        boolean pEnabled) {
        LogInDialog dialog = createDialog(pHostName, pUserName);
        JButton logInButton = (JButton) TestHelper.findNamedComponent(
            JButton.class, "logInButton", dialog, 0);
        assertEquals(pEnabled, logInButton.isEnabled());
    }

    public void testOKButton() {
        // Set up
        LogInDialog dialog = createDialog("myHostName", "myUserName");
        JButton logInButton = (JButton) TestHelper.findNamedComponent(
            JButton.class, "logInButton", dialog, 0);
        logInButton.doClick();

        // Test
        assertFalse(dialog.isVisible());
        assertFalse(dialog.wasCancelled());
        assertEquals("myHostName", dialog.getHostName());
        assertEquals("myUserName", dialog.getUserName());

    }

    public void testCancelButton() {
        // Set up
        LogInDialog dialog = createDialog("myHostName", "myUserName");
        JButton cancelButton = (JButton) TestHelper.findNamedComponent(
            JButton.class, "cancelButton", dialog, 0);
        cancelButton.doClick();

        // Test
        assertFalse(dialog.isVisible());
        assertTrue(dialog.wasCancelled());

    }

    private static LogInDialog createDialog(String pHostName, String pUserName) {
        LogInDialog dialog = new LogInDialog(null);
        dialog.setModal(false);
        dialog.setVisible(true);
        JTextField hostNameField = (JTextField) TestHelper.findNamedComponent(
            JTextField.class, "hostNameField", dialog, 0);
        hostNameField.setText(pHostName);
        JTextField userNameField = (JTextField) TestHelper.findNamedComponent(
            JTextField.class, "userNameField", dialog, 0);
        userNameField.setText(pUserName);
        return dialog;
    }

}
