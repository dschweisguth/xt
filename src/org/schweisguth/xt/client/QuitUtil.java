package org.schweisguth.xt.client;

import java.rmi.RemoteException;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.util.logging.Logger;
import org.schweisguth.xt.common.util.logging.Level;

// TODO Do apple-Q, clicking the close box and File->Exit all get here on MacOS 10.1?

public class QuitUtil {
    public static void quit(Client pClient) {
        try {
            pClient.disconnect();
            Logger.global.log(Level.FINE, "Removed remote listeners.");
        } catch (RemoteException e) {
            Logger.global.log(Level.WARNING,
                "Couldn't remove remote listeners", e);
        }
        System.exit(0);
    }
}
