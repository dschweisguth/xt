package org.schweisguth.xttest.common.gameimpl.approving;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ApprovingSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(ApprovingStateTest.class);
            suite.addTestSuite(ApprovedEventTest.class);
            suite.addTestSuite(ChallengedEventTest.class);
            return suite;
        }
        catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private ApprovingSuite() {
    }

}
