package org.schweisguth.xttest.common.util.collection;

import junit.framework.TestCase;
import org.schweisguth.xt.common.util.collection.HashStickySetMap;

public class HashStickySetMapTest extends TestCase {
    public void testStickyPutOK1() {
        MapTestUtil.testStickyPutOK1(new HashStickySetMap());
    }

    public void testStickySetPutOK2() {
        MapTestUtil.testStickySetPutOK2(new HashStickySetMap());
    }

    public void testStickyPutDuplicate() {
        MapTestUtil.testStickyPutDuplicate(new HashStickySetMap());
    }

    public void testStickyPutAllOK1() {
        MapTestUtil.testStickyPutAllOK1(new HashStickySetMap());
    }

    public void testStickySetPutAllOK2() {
        MapTestUtil.testStickySetPutAllOK2(new HashStickySetMap());
    }

    public void testStickyPutAllDuplicate() {
        MapTestUtil.testStickyPutAllDuplicate(new HashStickySetMap());
    }

    public void testSetPutOK1() {
        MapTestUtil.testSetPutOK1(new HashStickySetMap());
    }

    public void testSetPutOK2() {
        MapTestUtil.testSetPutOK2(new HashStickySetMap());
    }

    public void testSetPutDuplicate() {
        MapTestUtil.testSetPutDuplicate(new HashStickySetMap());
    }

    public void testSetPutAllOK1() {
        MapTestUtil.testSetPutAllOK1(new HashStickySetMap());
    }

    public void testSetPutAllOK2() {
        MapTestUtil.testSetPutAllOK2(new HashStickySetMap());
    }

    public void testSetPutAllDuplicate1() {
        MapTestUtil.testSetPutAllDuplicate1(new HashStickySetMap());
    }

    public void testSetPutAllDuplicate2() {
        MapTestUtil.testSetPutAllDuplicate2(new HashStickySetMap());
    }

}
