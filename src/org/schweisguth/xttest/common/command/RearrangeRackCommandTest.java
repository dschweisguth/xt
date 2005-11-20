package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.RearrangeRackCommand;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class RearrangeRackCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.addOther(new RearrangeRackCommand(2, 1));
        tester.addOther(new RearrangeRackCommand(0, 2));
        tester.setExpectedString("RearrangeRackCommand(0, 1)");
        tester.doAssert(new RearrangeRackCommand(0, 1));
    }
}
