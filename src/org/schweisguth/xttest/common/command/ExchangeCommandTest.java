package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.ExchangeCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class ExchangeCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.addOther(new ExchangeCommand(new int[] { 1 }));
        tester.setExpectedString("ExchangeCommand([0])");
        tester.doAssert(new ExchangeCommand(new int[] { 0 }));
    }
}
