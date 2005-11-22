package org.schweisguth.xttest.common;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.schweisguth.xttest.common.command.CommandSuite;
import org.schweisguth.xttest.common.domain.DomainSuite;
import org.schweisguth.xttest.common.game.GameSuite;
import org.schweisguth.xttest.common.gameimpl.GameImplSuite;
import org.schweisguth.xttest.common.util.collection.CollectionSuite;

public class CommonSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTest(CollectionSuite.suite());
            suite.addTest(CommandSuite.suite());
            suite.addTest(DomainSuite.suite());
            suite.addTest(GameSuite.suite());
            suite.addTest(GameImplSuite.suite());
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private CommonSuite() {
    }

}
