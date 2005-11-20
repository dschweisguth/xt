package org.schweisguth.xt.common.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.schweisguth.xt.common.util.contract.Assert;

public class ArraySetList extends ArrayList implements SetList {
    private static final long serialVersionUID = 5680878208464972285L;

    // Constructors

    public ArraySetList() {
    }

    public ArraySetList(Collection pCollection) {
        Assert.assertFalse(
            PrivateCollectionUtil.containsDuplicates(pCollection));
        for (Iterator iterator = pCollection.iterator(); iterator.hasNext();) {
            add(iterator.next());
        }
    }

    public ArraySetList(Object[] pSource) {
        super(pSource.length);
        for (int i = 0; i < pSource.length; i++) {
            add(pSource[i]);
        }
    }

    // Methods

    public Object set(int pIndex, Object pElement) {
        throw new UnsupportedOperationException();
    }

    public final boolean add(Object pElement) {
        if (contains(pElement)) {
            throw new DuplicateElementException();
        } else {
            return super.add(pElement);
        }
    }

    public void add(int pIndex, Object pElement) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection pCollection) {
        Assert.assertNotNull(pCollection);
        if (PrivateCollectionUtil.containsAny(this, pCollection)) {
            throw new DuplicateElementException();
        } else {
            return super.addAll(pCollection);
        }
    }

    public boolean addAll(int pIndex, Collection pCollection) {
        throw new UnsupportedOperationException();
    }

}
