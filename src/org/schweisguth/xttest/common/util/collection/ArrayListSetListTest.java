package org.schweisguth.xttest.common.util.collection;

import java.util.Collections;
import java.util.List;
import junit.framework.TestCase;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.collection.DuplicateElementException;

public class ArrayListSetListTest extends TestCase {
    public void testCreateArrayNull() {
        try {
            new ArraySetList((Object[]) null);
        }
        catch (NullPointerException e) {
            return;
        }
        fail();
    }

    public void testCreateArrayBad() {
        try {
            new ArraySetList(new String[] { "", "" });
        }
        catch (DuplicateElementException e) {
            return;
        }
        fail();
    }

    public void testAddBad() {
        try {
            new ArraySetList(new String[] { "" }).add("");
        }
        catch (DuplicateElementException e) {
            return;
        }
        fail();
    }

    public void testAddAllBad() {
        List addend = CollectionUtil.asList("");
        try {
            new ArraySetList(new String[] { "" }).addAll(addend);
        }
        catch (DuplicateElementException e) {
            return;
        }
        fail();
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
