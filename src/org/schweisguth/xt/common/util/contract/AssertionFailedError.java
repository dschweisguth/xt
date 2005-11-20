package org.schweisguth.xt.common.util.contract;

/**
 * Thrown when an assertion failed.
 */
public class AssertionFailedError extends Error {
    private static final long serialVersionUID = -5648034705425606072L;

    public AssertionFailedError() {
    }

    public AssertionFailedError(String message) {
        super(message);
    }
}
