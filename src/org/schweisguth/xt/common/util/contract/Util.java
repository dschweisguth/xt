package org.schweisguth.xt.common.util.contract;

class Util {
    public static String format(String message, Object expected, Object actual) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        return
            formatted + "expected:<" + expected + "> but was:<" + actual + ">";
    }

    private Util() {
    }

}
