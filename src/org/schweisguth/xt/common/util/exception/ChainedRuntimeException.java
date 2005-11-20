package org.schweisguth.xt.common.util.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ChainedRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 2762283297408125965L;

    private final Throwable mCause;

    public ChainedRuntimeException(Throwable pCause) {
        mCause = pCause;
    }

    public ChainedRuntimeException(String pMessage, Throwable pCause) {
        super(pMessage);
        mCause = pCause;
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream pStream) {
        super.printStackTrace(pStream);
        ExceptionUtil.ps(mCause, pStream);
    }

    public void printStackTrace(PrintWriter pStream) {
        super.printStackTrace(pStream);
        ExceptionUtil.pw(mCause, pStream);
    }

}
