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
        }
        catch (NullPointerException e) {
            return;
        }
        fail();
    }

    public void testCreateCollectionBad() {
        try {
            new HashStickySet(Arrays.asList(new String[] { "", "" }));
        }
        catch (DuplicateElementException e) {
            return;
        }
        fail();
    }

    public void testAddBad() {
        StickySet set = new HashStickySet();
        set.add("");
        try {
            set.add("");
        }
        catch (DuplicateElementException e) {
            return;
        }
        fail();
    }

    public void testAddAllBad() {
        StickySet set = new HashStickySet();
        set.add("");
        try {
            set.addAll(CollectionUtil.asList(""));
        }
        catch (DuplicateElementException e) {
            return;
        }
        fail();
    }

}
