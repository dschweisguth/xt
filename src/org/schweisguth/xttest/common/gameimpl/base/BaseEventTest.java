package org.schweisguth.xttest.common.gameimpl.base;

import java.text.MessageFormat;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.joining.JoiningState;
import org.schweisguth.xttest.testutil.BaseTest;

public abstract class BaseEventTest extends BaseTest {
    // Constants
    protected static final Game GAME = new GameImpl();
    protected static final Game GAME2
        = new GameImpl(new JoiningState(new String[] { "player1" }));
    protected static final String GAME_IMPL =
        GameImpl.class.getName() + "@........";

    // Methods: for subclasses

    protected static void assertMatches(String pPattern, Event pEvent) {
        String eventString = pEvent.toString();
        int tokenPosition = pPattern.indexOf(GAME_IMPL);
        String leftSide = pPattern.substring(0, tokenPosition);
        String rightSide = pPattern.substring(tokenPosition + GAME_IMPL.length(),
            pPattern.length());
        assertTrue(eventString.startsWith(leftSide));
        assertTrue(eventString.endsWith(rightSide));
    }

    protected static String format(String pPattern, Object pArg) {
        return MessageFormat.format(pPattern, new Object[] { pArg });
    }

    protected static String format(String pPattern, Object pArg1, Object pArg2) {
        return MessageFormat.format(pPattern, new Object[] { pArg1, pArg2 });
    }

    protected static String format(String pPattern, Object pArg1, int pArg2) {
        return format(pPattern, pArg1, new Integer(pArg2));
    }

    protected static String format(String pPattern, Object pArg1, int pArg2,
        Object pArg3) {
        return MessageFormat.format(pPattern,
            new Object[] { pArg1, new Integer(pArg2), pArg3 });
    }

    protected static String format(String pPattern, Object pArg1, int pArg2,
        int pArg3) {
        return format(pPattern, pArg1, pArg2, new Integer(pArg3));
    }

}
