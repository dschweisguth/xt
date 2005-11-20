package org.schweisguth.xttest.testutil;

import junit.framework.TestCase;
import org.schweisguth.xt.common.domain.Axis;
import org.schweisguth.xt.common.domain.TransferSet;

public abstract class BaseTest extends TestCase {
    // Constants
    protected static final String[] TWO_PLAYERS = { "player1", "player2" };
    protected static final String[] THREE_PLAYERS =
        { "player1", "player2", "player3" };
    protected static final String AAAAAAA = "AAAAAAA";
    protected static final String EEEEEEE = "EEEEEEE";
    protected static final String[] AAAAAAA_EEEEEEE = { AAAAAAA, EEEEEEE };
    protected static final String[] AAAAAAA_EEEEEEE_IIIIIII =
        { AAAAAAA, EEEEEEE, "IIIIIII" };
    protected static final TransferSet MOVE_TWO =
        new TransferSet(new int[] { 0, 1 }, 7, 7, Axis.X);
    protected static final TransferSet MOVE_SEVEN =
        new TransferSet(new int[] { 0, 1, 2, 3, 4, 5, 6 }, 7, 7, Axis.X);

    // Methods

    protected static void assertIsSerializable(Object pObject) throws Exception {
        Util.assertIsSerializable(pObject);
    }

}
