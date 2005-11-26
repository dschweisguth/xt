package org.schweisguth.xttest.client.server;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ServerSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(ClientTest.class);
            suite.addTestSuite(RefreshEventTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private ServerSuite() {
    }

}
