package org.schweisguth.xttest.common.game;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.StartCommand;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class RequestTest extends TestCase {
    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new Request("player2", new JoinCommand()));
        tester.addOther(new Request("player1", new StartCommand()));
        tester.setExpectedString("Request(player1, JoinCommand())");
        tester.doAssert(new Request("player1", new JoinCommand()));
    }
}
