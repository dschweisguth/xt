package org.schweisguth.xttest.common.gameimpl.challenging;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ChallengingSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(ChallengingStateTest.class);
            suite.addTestSuite(OverruledChallengeEventTest.class);
            suite.addTestSuite(SustainedChallengeEventTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private ChallengingSuite() {
    }

}
