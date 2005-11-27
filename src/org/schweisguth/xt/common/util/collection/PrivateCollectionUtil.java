package org.schweisguth.xt.common.util.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

class PrivateCollectionUtil {
    public static void rejectDuplicateKey(StickyMap pMap, Object pKey,
        Object pValue) {
        if (pMap.containsKey(pKey)) {
            throw new DuplicateElementException(
                "Tried to put duplicate key \"" + pKey + "\" with value \"" +
                    pValue + "\"");
        }
    }

    public static void rejectDuplicateKeys(StickyMap pMap, Map pSource) {
        if (containsAny(pMap.keySet(), pSource.keySet())) {
            throw new DuplicateElementException(
                "Tried to put one or more duplicate keys");
        }
    }

    public static void rejectDuplicateValue(SetMap pMap, Object pKey,
        Object pValue) {
        if (pMap.containsValue(pValue)) {
            throw new DuplicateElementException(
                "Tried to put key \"" + pKey + "\" with duplicate value \"" +
                    pValue + "\"");
        }
    }

    public static void rejectDuplicateValues(SetMap pMap, Map pSource) {
        if (containsAny(pMap.values(), pSource.values())) {
            throw new DuplicateElementException(
                "Tried to put one or more values which are already in map");
        }
        if (containsDuplicates(pSource.values())) {
            throw new DuplicateElementException(
                "Tried to put a map with duplicate values");
        }
    }

    public static boolean containsAny(Collection pSample, Collection pProbe) {
        Iterator i = pProbe.iterator();
        while (i.hasNext()) {
            if (pSample.contains(i.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsDuplicates(Collection pCollection) {
        return new HashSet(pCollection).size() != pCollection.size();
    }

    public static boolean containsDuplicates(Object[] pArray) {
        return containsDuplicates(Arrays.asList(pArray));
    }

    private PrivateCollectionUtil() {
    }

}
