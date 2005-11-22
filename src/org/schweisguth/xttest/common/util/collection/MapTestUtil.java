package org.schweisguth.xttest.common.util.collection;

import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.schweisguth.xt.common.util.collection.DuplicateElementException;
import org.schweisguth.xt.common.util.collection.SetMap;
import org.schweisguth.xt.common.util.collection.StickyMap;
import org.schweisguth.xt.common.util.collection.StickySetMap;

class MapTestUtil {
    // Constructors

    private MapTestUtil() {
    }

    // Methods

    public static void testStickyPutOK1(StickyMap pSubject) {
        pSubject.put("key1", "value1");
        Map expected = new HashMap();
        expected.put("key1", "value1");
        Assert.assertEquals(expected, pSubject);
    }

    public static void testStickyPutOK2(StickyMap pSubject) {
        pSubject.put("key1", "value1");
        pSubject.put("key2", "value1");
        Map expected = new HashMap();
        expected.put("key1", "value1");
        expected.put("key2", "value1");
        Assert.assertEquals(expected, pSubject);
    }

    public static void testStickySetPutOK2(StickySetMap pSubject) {
        pSubject.put("key1", "value1");
        pSubject.put("key2", "value2");
        Map expected = new HashMap();
        expected.put("key1", "value1");
        expected.put("key2", "value2");
        Assert.assertEquals(expected, pSubject);
    }

    public static void testStickyPutDuplicate(StickyMap pSubject) {
        pSubject.put("key1", "value1");
        Map subjectBeforePut = new HashMap(pSubject);
        try {
            pSubject.put("key1", "value2");
        } catch (DuplicateElementException e) {
            Assert.assertEquals(subjectBeforePut, pSubject);
            return;
        }
        Assert.fail();
    }

    public static void testStickyPutAllOK1(StickyMap pSubject) {
        Map source = new HashMap();
        source.put("key1", "value1");
        pSubject.putAll(source);
        Assert.assertEquals(source, pSubject);
    }

    public static void testStickyPutAllOK2(StickyMap pSubject) {
        Map source = new HashMap();
        source.put("key1", "value1");
        source.put("key2", "value1");
        pSubject.putAll(source);
        Assert.assertEquals(source, pSubject);
    }

    public static void testStickySetPutAllOK2(StickyMap pSubject) {
        Map source = new HashMap();
        source.put("key1", "value1");
        source.put("key2", "value2");
        pSubject.putAll(source);
        Assert.assertEquals(source, pSubject);
    }

    public static void testStickyPutAllDuplicate(StickyMap pSubject) {
        pSubject.put("key1", "value1");
        Map subjectBeforePutAll = new HashMap(pSubject);
        Map source = new HashMap();
        source.put("key1", "value2");
        try {
            pSubject.putAll(source);
        } catch (DuplicateElementException e) {
            Assert.assertEquals(subjectBeforePutAll, pSubject);
            return;
        }
        Assert.fail();
    }

    public static void testSetPutOK1(SetMap pSubject) {
        pSubject.put("key1", "value1");
        Map expected = new HashMap();
        expected.put("key1", "value1");
        Assert.assertEquals(expected, pSubject);
    }

    public static void testSetPutOK2(SetMap pSubject) {
        pSubject.put("key1", "value1");
        pSubject.put("key2", "value2");
        Map expected = new HashMap();
        expected.put("key1", "value1");
        expected.put("key2", "value2");
        Assert.assertEquals(expected, pSubject);
    }

    public static void testSetPutDuplicate(SetMap pSubject) {
        pSubject.put("key1", "value1");
        Map subjectBeforePut = new HashMap(pSubject);
        try {
            pSubject.put("key2", "value1");
        } catch (DuplicateElementException e) {
            Assert.assertEquals(subjectBeforePut, pSubject);
            return;
        }
        Assert.fail();
    }

    public static void testSetPutAllOK1(SetMap pSubject) {
        Map source = new HashMap();
        source.put("key1", "value1");
        pSubject.putAll(source);
        Assert.assertEquals(source, pSubject);
    }

    public static void testSetPutAllOK2(SetMap pSubject) {
        Map source = new HashMap();
        source.put("key1", "value1");
        source.put("key2", "value2");
        pSubject.putAll(source);
        Assert.assertEquals(source, pSubject);
    }

    public static void testSetPutAllDuplicate1(SetMap pSubject) {
        pSubject.put("key1", "value1");
        Map subjectBeforePutAll = new HashMap(pSubject);
        Map source = new HashMap();
        source.put("key2", "value1");
        try {
            pSubject.putAll(source);
        } catch (DuplicateElementException e) {
            Assert.assertEquals(subjectBeforePutAll, pSubject);
            return;
        }
        Assert.fail();
    }

    public static void testSetPutAllDuplicate2(SetMap pSubject) {
        Map subjectBeforePutAll = new HashMap(pSubject);
        Map source = new HashMap();
        source.put("key1", "value1");
        source.put("key2", "value1");
        try {
            pSubject.putAll(source);
        } catch (DuplicateElementException e) {
            Assert.assertEquals(subjectBeforePutAll, pSubject);
            return;
        }
        Assert.fail();
    }

}
