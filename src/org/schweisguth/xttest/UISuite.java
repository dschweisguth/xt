package org.schweisguth.xttest;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.schweisguth.xttest.client.LogInDialogTest;
import org.schweisguth.xttest.client.error.ErrorDialogTest;

public class UISuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(ErrorDialogTest.class);
            suite.addTestSuite(LogInDialogTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private UISuite() {
    }

}
