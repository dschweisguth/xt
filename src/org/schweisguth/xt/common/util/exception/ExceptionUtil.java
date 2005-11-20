package org.schweisguth.xt.common.util.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

class ExceptionUtil {
    private static final String CAUSED_BY = "Caused by:";

    private ExceptionUtil() {
    }

    public static void ps(Throwable pCause, PrintStream pStream) {
        pStream.print(CAUSED_BY);
        if (pCause != null) {
            pCause.printStackTrace(pStream);
        }
    }

    public static void pw(Throwable pCause, PrintWriter pStream) {
        pStream.print(CAUSED_BY);
        if (pCause != null) {
            pCause.printStackTrace(pStream);
        }
    }
}
