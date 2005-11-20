package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.TransferAnythingCommand;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class TransferAnythingCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.setExpectedString("TransferAnythingCommand()");
        tester.doAssert(new TransferAnythingCommand());
    }
}
