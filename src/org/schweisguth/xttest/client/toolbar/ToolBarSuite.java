package org.schweisguth.xttest.client.toolbar;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ToolBarSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(ExchangeActionTest.class);
            suite.addTestSuite(ToolBarTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private ToolBarSuite() {
    }

}
