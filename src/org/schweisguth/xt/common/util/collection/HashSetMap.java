package org.schweisguth.xt.common.util.collection;

import java.util.HashMap;
import java.util.Map;

public class HashSetMap extends HashMap implements SetMap {
    private static final long serialVersionUID = 6028761702029304212L;

    public Object put(Object pKey, Object pValue) {
        PrivateCollectionUtil.rejectDuplicateValue(this, pKey, pValue);
        return super.put(pKey, pValue);
    }

    public void putAll(Map pSource) {
        PrivateCollectionUtil.rejectDuplicateValues(this, pSource);
        super.putAll(pSource);
    }

}
