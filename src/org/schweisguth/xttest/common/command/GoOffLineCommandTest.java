package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.GoOffLineCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class GoOffLineCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.setExpectedString("GoOffLineCommand()");
        tester.doAssert(new GoOffLineCommand());
    }
}
