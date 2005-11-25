package org.schweisguth.xttest.common.util.collection;

import java.util.Arrays;
import junit.framework.TestCase;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.collection.DuplicateElementException;
import org.schweisguth.xt.common.util.collection.HashStickySet;
import org.schweisguth.xt.common.util.collection.StickySet;

public class HashStickySetTest extends TestCase {
    public void testCreateCollectionNull() {
        try {
            new HashStickySet(null);
            fail();
        } catch (NullPointerException e) {
        }
    }

    public void testCreateCollectionBad() {
        try {
            new HashStickySet(Arrays.asList(new String[] { "", "" }));
            fail();
        } catch (DuplicateElementException e) {
        }
    }

    public void testAddBad() {
        StickySet set = new HashStickySet();
        set.add("");
        try {
            set.add("");
            fail();
        } catch (DuplicateElementException e) {
        }
    }

    public void testAddAllBad() {
        StickySet set = new HashStickySet();
        set.add("");
        try {
            set.addAll(CollectionUtil.asList(""));
            fail();
        } catch (DuplicateElementException e) {
        }
    }

}
