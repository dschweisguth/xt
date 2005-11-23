package org.schweisguth.xt.common.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.schweisguth.xt.common.util.logging.Level;
import org.schweisguth.xt.common.util.logging.Logger;

public class IOUtil {
    public static void close(InputStream pResource) {
        try {
            pResource.close();
        } catch (IOException e) {
            Logger.global.log(Level.WARNING, "Couldn't close resource", e);
        }
    }

    public static void close(OutputStream pResource) {
        try {
            pResource.close();
        } catch (IOException e) {
            Logger.global.log(Level.WARNING, "Couldn't close resource", e);
        }
    }

    private IOUtil() {
    }

}
