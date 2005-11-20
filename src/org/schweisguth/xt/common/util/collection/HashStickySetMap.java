package org.schweisguth.xt.common.util.collection;

import java.util.HashMap;
import java.util.Map;

public class HashStickySetMap extends HashMap implements StickySetMap {
    private static final long serialVersionUID = -8231472743933113116L;

    public Object put(Object pKey, Object pValue) {
        PrivateCollectionUtil.rejectDuplicateKey(this, pKey, pValue);
        PrivateCollectionUtil.rejectDuplicateValue(this, pKey, pValue);
        return super.put(pKey, pValue);
    }

    public void putAll(Map pSource) {
        PrivateCollectionUtil.rejectDuplicateKeys(this, pSource);
        PrivateCollectionUtil.rejectDuplicateValues(this, pSource);
        super.putAll(pSource);
    }

}
