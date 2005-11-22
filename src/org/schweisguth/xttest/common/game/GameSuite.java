package org.schweisguth.xttest.common.game;

import junit.framework.Test;
import junit.framework.TestSuite;

public class GameSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(RequestTest.class);
            suite.addTestSuite(ListenerManagerTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private GameSuite() {
    }

}
