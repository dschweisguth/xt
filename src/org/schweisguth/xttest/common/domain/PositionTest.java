package org.schweisguth.xttest.common.domain;

import junit.framework.TestCase;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class PositionTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new Position(1, 0));
        tester.setExpectedString("(0, 0)");
        tester.doAssert(new Position(0, 0));
    }
}
