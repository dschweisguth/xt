package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.PassCommand;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class PassCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.setExpectedString("PassCommand()");
        tester.doAssert(new PassCommand());
    }
}
