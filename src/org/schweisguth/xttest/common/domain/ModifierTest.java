package org.schweisguth.xttest.common.domain;

import junit.framework.TestCase;
import org.schweisguth.xt.common.domain.Modifier;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class ModifierTest extends TestCase {
    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(Modifier.DEFAULT);
        tester.addOther(Modifier.DOUBLE_WORD);
        tester.doAssert(Modifier.DOUBLE_LETTER);
    }

    public void testToStringDefault() {
        assertEquals("   ", Modifier.DEFAULT.toString());
    }

    public void testToStringLetter() {
        assertEquals("2LS", Modifier.DOUBLE_LETTER.toString());
    }

    public void testToStringWord() {
        assertEquals("2WS", Modifier.DOUBLE_WORD.toString());
    }

}
