package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.TransferSetCommand;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class TransferSetCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.addOther(new TransferSetCommand(new TransferSet(1, 0, 0)));
        tester.setExpectedString("TransferSetCommand([0->(0, 0)])");
        tester.doAssert(new TransferSetCommand(new TransferSet(0, 0, 0)));
    }
}
