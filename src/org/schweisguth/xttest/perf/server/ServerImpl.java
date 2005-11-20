package org.schweisguth.xttest.perf.server;

import org.schweisguth.xttest.perf.common.Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {
    public static void main(String[] pArgs) throws RemoteException, MalformedURLException {
        System.setProperty("java.security.policy", "server.policy");
        LocateRegistry.createRegistry(1099);
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        Naming.rebind("Server", new ServerImpl());
        System.out.println("Started.");
    }

    public ServerImpl() throws RemoteException {
    }

    public void send(Object object) throws RemoteException {
    }

}
