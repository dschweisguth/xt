package org.schweisguth.xttest.common.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import junit.framework.TestCase;
import org.schweisguth.xt.common.util.collection.CollectionUtil;

public class CollectionUtilTest extends TestCase {
    public void testContainsOnlyInstancesOfTrueZero() {
        List list = new ArrayList();
        assertTrue(CollectionUtil.containsOnlyInstancesOf(list, String.class));
    }

    public void testContainsOnlyInstancesOfTrueOne() {
        List list = new ArrayList();
        list.add("");
        assertTrue(CollectionUtil.containsOnlyInstancesOf(list, String.class));
    }

    public void testContainsOnlyInstancesOfFalseOne() {
        List list = new ArrayList();
        list.add(new Integer(0));
        assertFalse(CollectionUtil.containsOnlyInstancesOf(list, String.class));
    }

    public void testContainsOnlyInstancesOfFalseTwo() {
        List list = new ArrayList();
        list.add("");
        list.add(new Integer(0));
        assertFalse(CollectionUtil.containsOnlyInstancesOf(list, String.class));
    }

    public void testContainsOnlyNullOrInstancesOfTrueZero() {
        List list = new ArrayList();
        assertTrue(
            CollectionUtil.containsOnlyNullOrInstancesOf(list, String.class));
    }

    public void testContainsOnlyNullOrInstancesOfTrueOne() {
        List list = new ArrayList();
        list.add("");
        assertTrue(
            CollectionUtil.containsOnlyNullOrInstancesOf(list, String.class));
    }

    public void testContainsOnlyNullOrInstancesOfTrueOneNull() {
        List list = new ArrayList();
        list.add(null);
        assertTrue(
            CollectionUtil.containsOnlyNullOrInstancesOf(list, String.class));
    }

    public void testContainsOnlyNullOrInstancesOfFalseOne() {
        List list = new ArrayList();
        list.add(new Integer(0));
        assertFalse(
            CollectionUtil.containsOnlyNullOrInstancesOf(list, String.class));
    }

    public void testContainsOnlyNullOrInstancesOfTrueTwo() {
        List list = new ArrayList();
        list.add("");
        list.add(null);
        assertTrue(
            CollectionUtil.containsOnlyNullOrInstancesOf(list, String.class));
    }

    public void testContainsOnlyNullOrInstancesOfFalseTwo() {
        List list = new ArrayList();
        list.add("");
        list.add(new Integer(0));
        assertFalse(
            CollectionUtil.containsOnlyNullOrInstancesOf(list, String.class));
    }

    public void testEqualsIgnoringOrder0() {
        assertTrue(CollectionUtil.equalsIgnoringOrder(
            Collections.EMPTY_LIST, Collections.EMPTY_LIST));
    }

    public void testEqualsIgnoringOrder2() {
        assertTrue(CollectionUtil.equalsIgnoringOrder(
            Arrays.asList(new String[] { "a", "b" }),
            Arrays.asList(new String[] { "b", "a" })));
    }

    public void testEqualsIgnoringOrder2Unmodifiable() {
        assertTrue(CollectionUtil.equalsIgnoringOrder(
            Collections.unmodifiableList(
                Arrays.asList(new String[] { "a", "b" })),
            Collections.unmodifiableList(
                Arrays.asList(new String[] { "b", "a" }))));
    }

    public void testEqualsIgnoringOrder21False() {
        assertFalse(CollectionUtil.equalsIgnoringOrder(
            Arrays.asList(new String[] { "a", "b" }),
            Arrays.asList(new String[] { "a" })));
    }

    public void testEqualsIgnoringOrder22False() {
        assertFalse(CollectionUtil.equalsIgnoringOrder(
            Arrays.asList(new String[] { "a", "b" }),
            Arrays.asList(new String[] { "a", "a" })));
    }

    public void testEqualsIgnoringOrder23False() {
        assertFalse(CollectionUtil.equalsIgnoringOrder(
            Arrays.asList(new String[] { "a", "b" }),
            Arrays.asList(new String[] { "a", "b", "a" })));
    }

    public void testReplace11() {
        List target = Arrays.asList(new String[] { null });
        List source = Arrays.asList(new String[] { "1" });
        CollectionUtil.replace(target, source);
        assertEquals(source, target);
    }

    public void testReplace21() {
        List target = Arrays.asList(new String[] { null, null });
        List source = Arrays.asList(new String[] { "1" });
        List expected = Arrays.asList(new String[] { "1", null });
        CollectionUtil.replace(target, source);
        assertEquals(expected, target);
    }

    public void testGetNotNullCount0() {
        assertEquals(0,
            CollectionUtil.getNotNullCount(CollectionUtil.asList(null)));
    }

    public void testGetNotNullCount() {
        assertEquals(1,
            CollectionUtil.getNotNullCount(CollectionUtil.asList("")));
    }

    public void testEquals2DArraysTrue() {
        assertTrue(CollectionUtil.equals(new Object[][] { }, new Object[][] { }));
    }

    public void testEquals2DArraysFalse() {
        assertFalse(
            CollectionUtil.equals(new Object[][] { }, new Object[][] { { } }));
    }

    public void testEnumerate0() {
        assertEquals("", CollectionUtil.enumerate(
            Arrays.asList(new String[] { })));
    }

    public void testEnumerate1() {
        assertEquals("1", CollectionUtil.enumerate(
            Arrays.asList(new String[] { "1" })));
    }

    public void testEnumerate2() {
        assertEquals("1 and 2", CollectionUtil.enumerate(
            Arrays.asList(new String[] { "1", "2" })));
    }

    public void testEnumerate3() {
        assertEquals("1, 2 and 3", CollectionUtil.enumerate(
            Arrays.asList(new String[] { "1", "2", "3" })));
    }

    public void testEnumerate4() {
        assertEquals("1, 2, 3 and 4", CollectionUtil.enumerate(
            Arrays.asList(new String[] { "1", "2", "3", "4" })));
    }

}
