package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.RearrangeBoardCommand;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class RearrangeBoardCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.addOther(
            new RearrangeBoardCommand(new Position(1, 0), new Position(0, 1)));
        tester.addOther(
            new RearrangeBoardCommand(new Position(0, 0), new Position(1, 1)));
        tester.setExpectedString("RearrangeBoardCommand((0, 0), (0, 1))");
        tester.doAssert(
            new RearrangeBoardCommand(new Position(0, 0), new Position(0, 1)));
    }
}
