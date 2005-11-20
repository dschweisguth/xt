package org.schweisguth.xt.common.server;

import org.schweisguth.xt.common.configuration.Configuration;
import org.schweisguth.xt.common.util.logging.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class NATSocketFactory extends RMISocketFactory {
    private static final String NAT_SERVER = "natServer";
    private static final String FIXED_SERVER_SOCKET_PORT =
        "fixedServerSocketPort";

    /**
     * If the requested host has an entry in the configuration, opens a socket
     * to the host given in the configuration (the NAT server) instead of to
     * the requested host (the NAT client). For this to work, the NAT client
     * must have a static address and the NAT server must map the requested
     * port to the same port on the NAT client.
     */
    public Socket createSocket(String host, int port) throws IOException {
        String actualHost = Configuration.instance().get(
            getClass(), NAT_SERVER + "." + host, host);
        Logger.global.finer(
            "Creating socket to host " + actualHost + " on port " + port);
        return new Socket(actualHost, port);
    }

    /**
     * If a fixedServerSocketPort is configured and the requested port is 0
     * (meaning the socket may be created on any port), creates the socket on
     * the fixed port. This allows a server on NAT client to be accessed via a
     * necessarily fixed port mapping on its NAT server.
     */
    public ServerSocket createServerSocket(int port) throws IOException {
        int fixedServerSocketPort = Configuration.instance().getInt(
            getClass(), FIXED_SERVER_SOCKET_PORT, 0);
        int actualPort = port == 0 ? fixedServerSocketPort : port;
        Logger.global.finer("Creating server socket on port " + actualPort);
        return new ServerSocket(actualPort);
    }

}
