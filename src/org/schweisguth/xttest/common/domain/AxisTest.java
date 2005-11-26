package org.schweisguth.xttest.common.domain;

import junit.framework.TestCase;
import org.schweisguth.xt.common.domain.Axis;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class AxisTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(Axis.Y);
        tester.setExpectedString("X");
        tester.doAssert(Axis.X);
    }
}
