package org.schweisguth.xttest.client;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.schweisguth.xttest.client.action.ActionSuite;
import org.schweisguth.xttest.client.rackimpl.PlayerRackControllerSuite;
import org.schweisguth.xttest.client.server.ServerSuite;
import org.schweisguth.xttest.client.toolbar.ToolBarSuite;

public class ClientSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTest(ActionSuite.suite());
            suite.addTestSuite(BoxLidTest.class);
            suite.addTestSuite(ChatDisplayTest.class);
            suite.addTest(PlayerRackControllerSuite.suite());
            suite.addTest(ServerSuite.suite());
            suite.addTestSuite(SeatingControllerTest.class);
            suite.addTest(ToolBarSuite.suite());
            return suite;
        }
        catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private ClientSuite() {
    }

}
