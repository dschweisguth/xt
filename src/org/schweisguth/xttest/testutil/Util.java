package org.schweisguth.xttest.testutil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import junit.framework.Assert;
import org.schweisguth.xt.common.util.io.IOUtil;

class Util {
    public static void assertIsSerializable(Object pObject) throws Exception {
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

    private Util() {
    }

}
