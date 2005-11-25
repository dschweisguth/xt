package org.schweisguth.xttest.common.command;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CommandSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(ApproveCommandTest.class);
            suite.addTestSuite(ChallengeCommandTest.class);
            suite.addTestSuite(ChatCommandTest.class);
            suite.addTestSuite(DrawForFirstCommandTest.class);
            suite.addTestSuite(DrawNewTilesCommandTest.class);
            suite.addTestSuite(DrawStartingTilesCommandTest.class);
            suite.addTestSuite(EndGameCommandTest.class);
            suite.addTestSuite(ExchangeCommandTest.class);
            suite.addTestSuite(FinishCommandTest.class);
            suite.addTestSuite(GoOffLineCommandTest.class);
            suite.addTestSuite(JoinCommandTest.class);
            suite.addTestSuite(LogInCommandTest.class);
            suite.addTestSuite(LogOutCommandTest.class);
            suite.addTestSuite(OverruleChallengeCommandTest.class);
            suite.addTestSuite(PassCommandTest.class);
            suite.addTestSuite(RearrangeBoardCommandTest.class);
            suite.addTestSuite(RearrangeRackCommandTest.class);
            suite.addTestSuite(StartCommandTest.class);
            suite.addTestSuite(StartNewGameCommandTest.class);
            suite.addTestSuite(SustainChallengeCommandTest.class);
            suite.addTestSuite(TakeBackCommandTest.class);
            suite.addTestSuite(TransferAnythingCommandTest.class);
            suite.addTestSuite(TransferCommandTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private CommandSuite() {
    }

}
