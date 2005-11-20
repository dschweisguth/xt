package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.ChallengeCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class ChallengeCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.setExpectedString("ChallengeCommand()");
        tester.doAssert(new ChallengeCommand());
    }
}
