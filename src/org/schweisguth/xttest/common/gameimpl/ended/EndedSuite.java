package org.schweisguth.xttest.common.gameimpl.ended;

import junit.framework.Test;
import junit.framework.TestSuite;

public class EndedSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(EndedStateTest.class);
            suite.addTestSuite(StartedNewGameEventTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private EndedSuite() {
    }

}
