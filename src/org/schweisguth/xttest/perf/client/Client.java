package org.schweisguth.xttest.perf.client;

import org.schweisguth.xttest.perf.common.EmptyObject;
import org.schweisguth.xttest.perf.common.Server;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] pArgs) throws Exception {
        Server server = (Server) Naming.lookup("//schweisguth.org/Server");
        send(server, 200, null, "null");
        send(server, 200, new EmptyObject(), "EmptyObject");
        send(server, 200, new int[1000], "int[1000]");
    }

    private static void send(Server pServer, int pRequestCount, Object pPayload, String pName) throws RemoteException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < pRequestCount; i++) {
            pServer.send(pPayload);
        }
        System.out.println(pRequestCount + " " + pName + " requests took " + (System.currentTimeMillis() - start) + " ms");
    }

}
