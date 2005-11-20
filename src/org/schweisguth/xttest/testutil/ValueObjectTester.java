package org.schweisguth.xttest.testutil;

import java.util.Iterator;
import junit.framework.Assert;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.SetList;

public class ValueObjectTester {
    private final SetList mOthers = new ArraySetList();
    private String mExpectedString = null;

    public void addOther(Object pObject) {
        mOthers.add(pObject);
    }

    public void setExpectedString(String pExpectedString) {
        mExpectedString = pExpectedString;
    }

    public void doAssert(Object pObject) throws Exception {
        assertEquals(pObject, pObject);
        assertDoesNotEqual(pObject, null);
        for (Iterator others = mOthers.iterator(); others.hasNext();) {
            assertDoesNotEqual(pObject, others.next());
        }
        if (mExpectedString != null) {
            Assert.assertEquals(mExpectedString, pObject.toString());
        }
        Util.assertIsSerializable(pObject);
    }

    private static void assertEquals(Object pOne, Object pOther) {
        Assert.assertEquals(pOne, pOther);
        Assert.assertEquals(pOther, pOne);
        if (pOther != null) {
            Assert.assertEquals(pOne.hashCode(), pOther.hashCode());
        }
    }

    private static void assertDoesNotEqual(Object pObject, Object pOther) {
        Assert.assertFalse(pObject.equals(pOther));
        if (pOther != null) {
            Assert.assertFalse(pOther.equals(pObject));
        }
    }

}
