package org.schweisguth.xttest.common.gameimpl.moving;

import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.RearrangeBoardCommand;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.joining.JoinedEvent;
import org.schweisguth.xt.common.gameimpl.moving.RearrangedBoardEvent;
import org.schweisguth.xttest.common.gameimpl.base.BaseEventTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class RearrangedBoardEventTest extends BaseEventTest {
    // Constants
    private static final RearrangeBoardCommand COMMAND =
        new RearrangeBoardCommand(new Position(0, 0), new Position(1, 0));
    private static final Request REQUEST = new Request("player1", COMMAND);
    private static final RearrangedBoardEvent EVENT =
        new RearrangedBoardEvent(GAME, REQUEST);

    // Tests

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(
            new JoinedEvent(GAME, new Request("player1", new JoinCommand())));
        tester.addOther(new RearrangedBoardEvent(GAME2, REQUEST));
        tester.addOther(
            new RearrangedBoardEvent(GAME, new Request("player2", COMMAND)));
        tester.doAssert(EVENT);
    }

    public void testToString() {
        assertMatches(
            "RearrangedBoardEvent(" + GAME_IMPL + ", " + REQUEST + ")", EVENT);
    }

    public void testToHTML() {
        assertEquals("", EVENT.toHTML());
    }

}
