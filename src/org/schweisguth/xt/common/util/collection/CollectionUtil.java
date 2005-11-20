package org.schweisguth.xt.common.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.schweisguth.xt.common.util.contract.Assert;

public class CollectionUtil {
    public static List asList(Object pObject) {
        List list = new ArrayList();
        list.add(pObject);
        return list;
    }

    public static StickySet asStickySet(Object pObject) {
        StickySet set = new HashStickySet();
        set.add(pObject);
        return set;
    }

    public static boolean containsOnlyNull(Collection pCollection) {
        Iterator i = pCollection.iterator();
        while (i.hasNext()) {
            if (i.next() != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsOnlyInstancesOf(Collection pCollection,
        Class pClass) {
        Assert.assertNotNull(pCollection);
        Assert.assertNotNull(pClass);

        for (Iterator i = pCollection.iterator(); i.hasNext();) {
            if (! pClass.isInstance(i.next())) {
                return false;
            }
        }
        return true;

    }

    public static boolean containsOnlyNullOrInstancesOf(Collection pCollection,
        Class pClass) {
        Assert.assertNotNull(pCollection);
        Assert.assertNotNull(pClass);

        for (Iterator i = pCollection.iterator(); i.hasNext();) {
            Object element = i.next();
            if (! (element == null || pClass.isInstance(element))) {
                return false;
            }
        }
        return true;

    }

    public static boolean equalsIgnoringOrder(Collection pOne, Collection pTwo) {
        Assert.assertNotNull(pOne);
        Assert.assertNotNull(pTwo);

        if (pOne.equals(pTwo)) {
            return true;
        } else {
            List one = new ArrayList(pOne);
            Collections.sort(one);
            List two = new ArrayList(pTwo);
            Collections.sort(two);
            return one.equals(two);
        }

    }

    public static void replace(List pTarget, Collection pSource) {
        Assert.assertNotNull(pTarget);
        Assert.assertNotNull(pSource);
        Assert.assertTrue(pSource.size() <= pTarget.size());

        Iterator source = pSource.iterator();
        for (int position = 0; position < pSource.size(); position++) {
            pTarget.set(position, source.next());
        }

    }

    public static int getNotNullCount(Collection pCollection) {
        int count = 0;
        for (Iterator i = pCollection.iterator(); i.hasNext();) {
            if (i.next() != null) {
                count++;
            }
        }
        return count;
    }

    public static boolean equals(Object[][] pOne, Object[][] pOther) {
        if (pOne.length != pOther.length) {
            return false;
        }
        for (int i = 0; i < pOne.length; i++) {
            if (! Arrays.equals(pOne[i], pOther[i])) {
                return false;
            }
        }
        return true;
    }

    public static String enumerate(Collection pItems) {
        StringBuffer text = new StringBuffer();
        int itemIndex = 0;
        for (Iterator items = pItems.iterator(); items.hasNext();) {
            text.append((String) items.next());
            if (itemIndex <= pItems.size() - 3) {
                text.append(", ");
            } else if (itemIndex == pItems.size() - 2) {
                text.append(" and ");
            }
            itemIndex++;
        }
        return text.toString();
    }

    private CollectionUtil() {
    }

}
