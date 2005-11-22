package org.schweisguth.xttest.common.gameimpl.drawingstartingtiles;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DrawingStartingTilesSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(DrawingStartingTilesStateTest.class);
            suite.addTestSuite(DrewStartingTilesEventTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private DrawingStartingTilesSuite() {
    }

}
