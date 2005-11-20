package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.TakeBackCommand;
import org.schweisguth.xt.common.domain.Transfer;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class TakeBackCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.addOther(new TakeBackCommand(new Transfer(1, 0, 0)));
        tester.setExpectedString("TakeBackCommand(0->(0, 0))");
        tester.doAssert(new TakeBackCommand(new Transfer(0, 0, 0)));
    }
}
