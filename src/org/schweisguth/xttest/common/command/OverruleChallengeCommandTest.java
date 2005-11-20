package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.OverruleChallengeCommand;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class OverruleChallengeCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.setExpectedString("OverruleChallengeCommand()");
        tester.doAssert(new OverruleChallengeCommand());
    }
}
