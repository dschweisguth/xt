package org.schweisguth.xttest;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.schweisguth.xttest.client.ClientSuite;
import org.schweisguth.xttest.common.CommonSuite;

public class Suite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTest(ClientSuite.suite());
            suite.addTest(CommonSuite.suite());
            return suite;
        }
        catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private Suite() {
    }

}
