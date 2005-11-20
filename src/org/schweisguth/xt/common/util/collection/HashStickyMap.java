package org.schweisguth.xt.common.util.collection;

import java.util.HashMap;
import java.util.Map;

public class HashStickyMap extends HashMap implements StickyMap {
    private static final long serialVersionUID = 1822497692939671930L;

    public Object put(Object pKey, Object pValue) {
        PrivateCollectionUtil.rejectDuplicateKey(this, pKey, pValue);
        return super.put(pKey, pValue);
    }

    public void putAll(Map pSource) {
        PrivateCollectionUtil.rejectDuplicateKeys(this, pSource);
        super.putAll(pSource);
    }

}
