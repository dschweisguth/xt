package org.schweisguth.xttest.common.gameimpl.moving;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MovingSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(MovingStateTest.class);
            suite.addTestSuite(EndedGameEventTest.class);
            suite.addTestSuite(ExchangedEventTest.class);
            suite.addTestSuite(FinishedEventTest.class);
            suite.addTestSuite(PassedEventTest.class);
            suite.addTestSuite(RearrangedBoardEventTest.class);
            suite.addTestSuite(TookBackEventTest.class);
            suite.addTestSuite(TransferredEventTest.class);
            return suite;
        }
        catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private MovingSuite() {
    }

}
