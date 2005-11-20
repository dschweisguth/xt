package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.LogOutCommand;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class LogOutCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.setExpectedString("LogOutCommand()");
        tester.doAssert(new LogOutCommand());
    }
}
