package org.schweisguth.xttest.client.rackimpl;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PlayerRackControllerSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(ClientPlayerRackControllerTest.class);
            suite.addTestSuite(ClientPlayerRackModelTest.class);
            suite.addTestSuite(OtherPlayerRackControllerTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private PlayerRackControllerSuite() {
    }

}
