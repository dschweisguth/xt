package org.schweisguth.xttest.testutil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import junit.framework.Assert;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.SetList;
import org.schweisguth.xt.common.util.io.IOUtil;

public class ValueObjectTester {
    private final SetList mOthers = new ArraySetList();
    private String mExpectedString = null;
    private boolean mShouldBeSerializable = true;

    public void addOther(Object pObject) {
        mOthers.add(pObject);
    }

    public void setExpectedString(String pExpectedString) {
        mExpectedString = pExpectedString;
    }

    public void setShouldBeSerializable(boolean pShouldBeSerializable) {
        mShouldBeSerializable = pShouldBeSerializable;
    }

    public void doAssert(Object pObject) throws Exception {
        assertEquals(pObject, pObject);
        assertDoesNotEqual(pObject, null);
        assertDoesNotEqual(pObject, new Object());
        for (Iterator others = mOthers.iterator(); others.hasNext();) {
            assertDoesNotEqual(pObject, others.next());
        }
        if (mExpectedString != null) {
            Assert.assertEquals(mExpectedString, pObject.toString());
        }
        if (mShouldBeSerializable) {
            assertIsSerializable(pObject);
        }
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

    private static void assertIsSerializable(Object pObject) throws Exception {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectsOut = new ObjectOutputStream(bytesOut);
            try {
                objectsOut.writeObject(pObject);
            } finally {
                IOUtil.close(objectsOut);
            }
        } finally {
            IOUtil.close(bytesOut);
        }
        ByteArrayInputStream bytesIn =
            new ByteArrayInputStream(bytesOut.toByteArray());
        Object serializedObject;
        try {
            ObjectInputStream objectsIn = new ObjectInputStream(bytesIn);
            try {
                serializedObject = objectsIn.readObject();
            } finally {
                IOUtil.close(objectsIn);
            }
        } finally {
            IOUtil.close(bytesIn);
        }
        Assert.assertEquals(pObject, serializedObject);
    }

}
