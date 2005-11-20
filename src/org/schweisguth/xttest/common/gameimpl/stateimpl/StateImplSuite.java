package org.schweisguth.xttest.common.gameimpl.stateimpl;

import junit.framework.Test;
import junit.framework.TestSuite;

public class StateImplSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(StateImplTest.class);
            suite.addTestSuite(ChatEventTest.class);
            return suite;
        }
        catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private StateImplSuite() {
    }

}
