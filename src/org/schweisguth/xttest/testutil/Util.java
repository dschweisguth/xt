package org.schweisguth.xttest.testutil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import junit.framework.Assert;

class Util {
    public static void assertIsSerializable(Object pObject) throws Exception {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ObjectOutputStream objectsOut = new ObjectOutputStream(bytesOut);
        objectsOut.writeObject(pObject);
        objectsOut.close();
        bytesOut.close();
        ByteArrayInputStream bytesIn =
            new ByteArrayInputStream(bytesOut.toByteArray());
        ObjectInputStream objectsIn = new ObjectInputStream(bytesIn);
        Object serializedObject = objectsIn.readObject();
        objectsIn.close();
        bytesIn.close();
        Assert.assertEquals(pObject, serializedObject);
    }

    private Util() {
    }

}
