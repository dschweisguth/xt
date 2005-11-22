package org.schweisguth.xttest.common.gameimpl.drawingforfirst;

import junit.framework.Test;
import junit.framework.TestSuite;

public class DrawingForFirstSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(DrawingForFirstStateTest.class);
            suite.addTestSuite(DrewForFirstEventTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private DrawingForFirstSuite() {
    }

}
