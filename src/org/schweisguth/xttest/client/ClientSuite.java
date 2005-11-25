package org.schweisguth.xttest.client;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.schweisguth.xttest.client.action.CommandActionTest;
import org.schweisguth.xttest.client.rackimpl.PlayerRackControllerSuite;
import org.schweisguth.xttest.client.server.ServerSuite;
import org.schweisguth.xttest.client.toolbar.ToolBarSuite;

public class ClientSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(CommandActionTest.class);
            suite.addTest(PlayerRackControllerSuite.suite());
            suite.addTest(ServerSuite.suite());
            suite.addTest(ToolBarSuite.suite());
            suite.addTestSuite(BoxLidTest.class);
            suite.addTestSuite(ChatDisplayTest.class);
            suite.addTestSuite(SeatingControllerTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private ClientSuite() {
    }

}
