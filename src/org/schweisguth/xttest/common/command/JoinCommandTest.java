package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.StartCommand;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class JoinCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new StartCommand());
        tester.setExpectedString("JoinCommand()");
        tester.doAssert(new JoinCommand());
    }
}
