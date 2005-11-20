package org.schweisguth.xt.client.error;

/**
 * Displayed when ErrorDialog is constructed with a null cause.
 */
class NullCauseException extends Exception {
    public NullCauseException(String pMessage) {
        super(pMessage);
    }
}
