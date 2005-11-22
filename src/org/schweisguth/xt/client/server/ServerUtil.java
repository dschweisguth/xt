package org.schweisguth.xt.client.server;

import org.schweisguth.xt.common.server.Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServerUtil {
    // Methods

    public static Server getServer() throws RemoteException {
        return getServer("localhost");
    }

    public static Server getServer(String pHost) throws RemoteException {
        try {
            return (Server) Naming.lookup("//" + pHost + "/" + Server.NAME);
        } catch (NotBoundException e) {
            throw wrapInRemoteException(e);
        } catch (MalformedURLException e) {
            throw wrapInRemoteException(e);
        }
    }

    private static RemoteException wrapInRemoteException(Throwable pThrowable) {
        return new RemoteException("Couldn't look up server", pThrowable);
    }

    // Constructors

    private ServerUtil() {
    }

}
