package org.schweisguth.xttest.common.util.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import junit.framework.TestCase;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.collection.DuplicateElementException;

public class ArrayListSetListTest extends TestCase {
    public void testCreateCollection() {
        assertEquals(2,
            new ArraySetList(Arrays.asList(new String[] { "x", "y" })).size());
    }

    public void testCreateCollectionNull() {
        try {
            new ArraySetList((Collection) null);
            fail();
        } catch (NullPointerException e) {
        }
    }

    public void testCreateCollectionBad() {
        try {
            new ArraySetList(Arrays.asList(new String[] { "", "" }));
            fail();
        } catch (DuplicateElementException e) {
        }
    }

    public void testCreateArray() {
        assertEquals(2, new ArraySetList(new String[] { "x", "y" }).size());
    }

    public void testCreateArrayNull() {
        try {
            new ArraySetList((Object[]) null);
            fail();
        } catch (NullPointerException e) {
        }
    }

    public void testCreateArrayBad() {
        try {
            new ArraySetList(new String[] { "", "" });
            fail();
        } catch (DuplicateElementException e) {
        }
    }

    public void testAddBad() {
        try {
            new ArraySetList(new String[] { "" }).add("");
            fail();
        } catch (DuplicateElementException e) {
        }
    }

    public void testAddAllBad() {
        List addend = CollectionUtil.asList("");
        try {
            new ArraySetList(new String[] { "" }).addAll(addend);
            fail();
        } catch (DuplicateElementException e) {
        }
    }

    public void testEqualsList() {
        assertTrue(new ArraySetList().equals(Collections.EMPTY_LIST));
    }

    public void testListEquals() {
        assertTrue(Collections.EMPTY_LIST.equals(new ArraySetList()));
    }

    public void testEqualsSet() {
        assertFalse(new ArraySetList().equals(Collections.EMPTY_SET));
    }

    public void testSetEquals() {
        assertFalse(Collections.EMPTY_SET.equals(new ArraySetList()));
    }

}
