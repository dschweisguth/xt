package org.schweisguth.xttest.common.util.collection;

import junit.framework.TestCase;
import org.schweisguth.xt.common.util.collection.HashSetMap;

public class HashSetMapTest extends TestCase {
    public void testSetPutOK1() {
        MapTestUtil.testSetPutOK1(new HashSetMap());
    }

    public void testSetPutOK2() {
        MapTestUtil.testSetPutOK2(new HashSetMap());
    }

    public void testSetPutDuplicate() {
        MapTestUtil.testSetPutDuplicate(new HashSetMap());
    }

    public void testSetPutAllOK1() {
        MapTestUtil.testSetPutAllOK1(new HashSetMap());
    }

    public void testSetPutAllOK2() {
        MapTestUtil.testSetPutAllOK2(new HashSetMap());
    }

    public void testSetPutAllDuplicate1() {
        MapTestUtil.testSetPutAllDuplicate1(new HashSetMap());
    }

    public void testSetPutAllDuplicate2() {
        MapTestUtil.testSetPutAllDuplicate2(new HashSetMap());
    }

}
