package org.schweisguth.xttest.common.gameimpl;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.schweisguth.xttest.common.gameimpl.approving.ApprovingSuite;
import org.schweisguth.xttest.common.gameimpl.challenging.ChallengingSuite;
import org.schweisguth.xttest.common.gameimpl.drawingforfirst.DrawingForFirstSuite;
import org.schweisguth.xttest.common.gameimpl.drawingnewtiles.DrawingNewTilesSuite;
import org.schweisguth.xttest.common.gameimpl.drawingstartingtiles.DrawingStartingTilesSuite;
import org.schweisguth.xttest.common.gameimpl.ended.EndedSuite;
import org.schweisguth.xttest.common.gameimpl.hascurrentplayer.HasCurrentPlayerSuite;
import org.schweisguth.xttest.common.gameimpl.joining.JoiningSuite;
import org.schweisguth.xttest.common.gameimpl.moving.MovingSuite;
import org.schweisguth.xttest.common.gameimpl.stateimpl.StateImplSuite;

public class GameImplSuite {
    public static Test suite() throws Throwable {
        try {
            TestSuite suite = new TestSuite();
            suite.addTest(ApprovingSuite.suite());
            suite.addTest(ChallengingSuite.suite());
            suite.addTest(DrawingForFirstSuite.suite());
            suite.addTest(DrawingNewTilesSuite.suite());
            suite.addTest(DrawingStartingTilesSuite.suite());
            suite.addTest(EndedSuite.suite());
            suite.addTest(HasCurrentPlayerSuite.suite());
            suite.addTest(JoiningSuite.suite());
            suite.addTest(MovingSuite.suite());
            suite.addTest(StateImplSuite.suite());
            suite.addTestSuite(GameImplTest.class);
            suite.addTestSuite(LoggedInEventTest.class);
            suite.addTestSuite(LoggedOutEventTest.class);
            suite.addTestSuite(WentOffLineEventTest.class);
            return suite;
        } catch (Throwable e) {
            e.printStackTrace(); // JUnit just prints the exception class name!
            throw e;
        }
    }

    private GameImplSuite() {
    }

}
