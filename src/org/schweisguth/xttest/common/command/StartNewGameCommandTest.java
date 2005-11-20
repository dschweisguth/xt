package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.StartNewGameCommand;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class StartNewGameCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.setExpectedString("StartNewGameCommand()");
        tester.doAssert(new StartNewGameCommand());
    }
}
