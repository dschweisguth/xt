package org.schweisguth.xt.common.util.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ValueObject implements Serializable {
    private static final long serialVersionUID = 9011239939638076386L;

    // Fields
    private transient List mFields = null;
    private transient boolean mHashed = false;
    private transient int mHashCode;
    private transient String mString = null;

    // Methods

    protected List getFields() {
        return new ArrayList();
    }

    protected static List append(List pList, Object pObject) {
        pList.add(pObject);
        return pList;
    }

    private List getCachedFields() {
        if (mFields == null) {
            mFields = getFields();
        }
        return mFields;
    }

    public final boolean equals(Object pOther) {
        return pOther != null && pOther.getClass().equals(getClass()) &&
            getCachedFields().equals(((ValueObject) pOther).getCachedFields());
    }

    public final int hashCode() {
        if (! mHashed) {
            mHashCode = getClass().hashCode();
            Iterator fields = getCachedFields().iterator();
            while (fields.hasNext()) {
                mHashCode *= 3;
                mHashCode += fields.next().hashCode();
            }
            mHashed = true;
        }
        return mHashCode;
    }

    public final String toString() {
        if (mString == null) {
            String name = getClass().getName();
            StringBuffer string =
                new StringBuffer(name.substring(name.lastIndexOf((int) '.') + 1));
            string.append('(');
            Iterator fields = getCachedFields().iterator();
            while (fields.hasNext()) {
                Object field = fields.next();
                string.append(field);
                if (fields.hasNext()) {
                    string.append(", ");
                }
            }
            string.append(')');
            mString = string.toString();
        }
        return mString;
    }

}
