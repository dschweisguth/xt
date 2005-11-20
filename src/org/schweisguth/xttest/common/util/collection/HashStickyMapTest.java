package org.schweisguth.xttest.common.util.collection;

import junit.framework.TestCase;
import org.schweisguth.xt.common.util.collection.HashStickyMap;

public class HashStickyMapTest extends TestCase {
    public void testStickyPutOK1() {
        MapTestUtil.testStickyPutOK1(new HashStickyMap());
    }

    public void testStickyPutOK2() {
        MapTestUtil.testStickyPutOK2(new HashStickyMap());
    }

    public void testStickyPutDuplicate() {
        MapTestUtil.testStickyPutDuplicate(new HashStickyMap());
    }

    public void testStickyPutAllOK1() {
        MapTestUtil.testStickyPutAllOK1(new HashStickyMap());
    }

    public void testStickyPutAllOK2() {
        MapTestUtil.testStickyPutAllOK2(new HashStickyMap());
    }

    public void testStickyPutAllDuplicate() {
        MapTestUtil.testStickyPutAllDuplicate(new HashStickyMap());
    }

}
