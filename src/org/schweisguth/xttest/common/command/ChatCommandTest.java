package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.ChatCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class ChatCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.addOther(new ChatCommand("Hi Mom!"));
        tester.setExpectedString("ChatCommand(Hi Grandma!)");
        tester.doAssert(new ChatCommand("Hi Grandma!"));
    }
}
