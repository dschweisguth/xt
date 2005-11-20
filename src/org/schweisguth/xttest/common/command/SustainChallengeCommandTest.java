package org.schweisguth.xttest.common.command;

import junit.framework.TestCase;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.SustainChallengeCommand;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class SustainChallengeCommandTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new JoinCommand());
        tester.setExpectedString("SustainChallengeCommand()");
        tester.doAssert(new SustainChallengeCommand());
    }
}
