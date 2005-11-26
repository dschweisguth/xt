package org.schweisguth.xttest.client.chat;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ChatSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTestSuite(ChatDisplayTest.class);
            suite.addTestSuite(ChatSendTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private ChatSuite() {
    }

}
