package org.schweisguth.xt.common.util.collection;

public class DuplicateElementException extends IllegalArgumentException {
    private static final long serialVersionUID = 8017791050177178083L;

    public DuplicateElementException() {
    }

    public DuplicateElementException(String pString) {
        super(pString);
    }

}
