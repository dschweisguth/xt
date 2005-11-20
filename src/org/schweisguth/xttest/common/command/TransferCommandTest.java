package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.TransferCommand;
import org.schweisguth.xt.common.domain.Transfer;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class TransferCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.addOther(new TransferCommand(new Transfer(1, 0, 0)));
        tester.setExpectedString("TransferCommand(0->(0, 0))");
        tester.doAssert(new TransferCommand(new Transfer(0, 0, 0)));
    }
}
