package org.schweisguth.xttest.client.action;

import junit.framework.Test;
import junit.framework.TestSuite;

// TODO eliminate these tests, since they retest logic tested in state tests?
public class ActionSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(JoinActionTest.class);
            suite.addTestSuite(StartActionTest.class);
            suite.addTestSuite(DrawForFirstActionTest.class);
            suite.addTestSuite(DrawStartingTilesActionTest.class);
            suite.addTestSuite(FinishActionTest.class);
            suite.addTestSuite(ApproveActionTest.class);
            suite.addTestSuite(DrawNewTilesActionTest.class);
            suite.addTestSuite(ChallengeActionTest.class);
            suite.addTestSuite(OverruleChallengeActionTest.class);
            suite.addTestSuite(SustainChallengeActionTest.class);
            suite.addTestSuite(PassActionTest.class);
            suite.addTestSuite(StartNewGameActionTest.class);
            return suite;
        }
        catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private ActionSuite() {
    }

}
