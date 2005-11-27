package org.schweisguth.xt.common.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import org.schweisguth.xt.common.util.contract.Assert;

public class ArraySetList extends ArrayList implements SetList {
    private static final long serialVersionUID = 5680878208464972285L;

    // Constructors

    public ArraySetList() {
    }

    public ArraySetList(Collection pCollection) {
        super(pCollection.size());
        if (PrivateCollectionUtil.containsDuplicates(pCollection)) {
            throw new DuplicateElementException();
        }
        super.addAll(pCollection);
    }

    public ArraySetList(Object[] pSource) {
        super(pSource.length);
        if (PrivateCollectionUtil.containsDuplicates(pSource)) {
            throw new DuplicateElementException();
        }
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
