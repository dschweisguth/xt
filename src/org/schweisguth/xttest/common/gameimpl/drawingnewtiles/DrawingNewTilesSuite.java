package org.schweisguth.xttest.common.gameimpl.drawingnewtiles;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DrawingNewTilesSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(DrawingNewTilesStateTest.class);
            suite.addTestSuite(DrewNewTilesEventTest.class);
            return suite;
        }
        catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private DrawingNewTilesSuite() {
    }

}