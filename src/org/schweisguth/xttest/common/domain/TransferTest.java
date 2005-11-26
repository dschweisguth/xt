package org.schweisguth.xttest.common.domain;

import junit.framework.TestCase;
import org.schweisguth.xt.common.domain.Transfer;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class TransferTest extends TestCase {
    public void test() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new Transfer(1, 0, 0));
        tester.addOther(new Transfer(0, 1, 0));
        tester.setExpectedString("0->(0, 0)");
        tester.doAssert(new Transfer(0, 0, 0));
    }
}
