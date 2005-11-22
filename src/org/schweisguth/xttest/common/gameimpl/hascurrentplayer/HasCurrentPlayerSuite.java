package org.schweisguth.xttest.common.gameimpl.hascurrentplayer;

import junit.framework.Test;
import junit.framework.TestSuite;

public class HasCurrentPlayerSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(HasCurrentPlayerStateTest.class);
            suite.addTestSuite(RearrangedRackEventTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private HasCurrentPlayerSuite() {
    }

}
