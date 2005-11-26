package org.schweisguth.xttest.common.domain;

import junit.framework.TestCase;
import org.schweisguth.xt.common.domain.Modifier;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class ModifierTest extends TestCase {
    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(Modifier.DEFAULT);
        tester.addOther(Modifier.DOUBLE_WORD);
        tester.setExpectedString("2LS");
        tester.doAssert(Modifier.DOUBLE_LETTER);
    }
}
