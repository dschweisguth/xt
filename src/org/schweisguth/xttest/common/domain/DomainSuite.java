package org.schweisguth.xttest.common.domain;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DomainSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(AxisTest.class);
            suite.addTestSuite(BoxLidTest.class);
            suite.addTestSuite(BoardTest.class);
            suite.addTestSuite(ModifierTest.class);
            suite.addTestSuite(MoveTest.class);
            suite.addTestSuite(RackTest.class);
            suite.addTestSuite(ScoreSheetTest.class);
            suite.addTestSuite(TileTest.class);
            suite.addTestSuite(TransferSetTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private DomainSuite() {
    }

}
