package org.schweisguth.xt.common.util.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import org.schweisguth.xt.common.util.contract.Assert;

public class HashStickySet extends HashSet implements StickySet {
    private static final long serialVersionUID = -6013915314438974590L;

    public HashStickySet() {
    }

    public HashStickySet(Collection pSource) {
        for (Iterator i = pSource.iterator(); i.hasNext();) {
            add(i.next());
        }
    }

    public final boolean add(Object pElement) {
        if (contains(pElement)) {
            throw new DuplicateElementException();
        } else {
            return super.add(pElement);
        }
    }

    public boolean addAll(Collection pCollection) {
        Assert.assertNotNull(pCollection);
        if (PrivateCollectionUtil.containsAny(this, pCollection)) {
            throw new DuplicateElementException();
        } else {
            return super.addAll(pCollection);
        }
    }

}
