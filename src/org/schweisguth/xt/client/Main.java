package org.schweisguth.xt.client;

import java.awt.Frame;
import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import javax.swing.UIManager;
import org.schweisguth.xt.client.error.ErrorDialog;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.client.server.ClientImpl;
import org.schweisguth.xt.client.server.ServerUtil;
import org.schweisguth.xt.common.configuration.Configuration;
import org.schweisguth.xt.common.server.NATSocketFactory;
import org.schweisguth.xt.common.server.Server;
import org.schweisguth.xt.common.util.logging.Level;
import org.schweisguth.xt.common.util.logging.Logger;

// TODO package as MacOS application
// TODO attempting to transfer onto approved tile results in selecting it
// TODO repeated clicks or apple-clicks on same tile result in server error
// TODO apple-clicking on multiple selected tiles doesn't turn off exchange button

class Main {
    private static final String QUIT_HANDLER = "quitHandler";

    public static void main(String[] pArgs) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
            RMISocketFactory.setSocketFactory(new NATSocketFactory());
            ClientImpl client = new ClientImpl();
            registerQuitHandler(client);
            Frame mainFrame = new ClientController(client).getView();
            mainFrame.setVisible(true);
            LogInDialog logInDialog = new LogInDialog(mainFrame);
            while (true) {
                logInDialog.show();
                if (logInDialog.wasCancelled()) {
                    System.exit(0);
                }
                try {
                    Server server =
                        ServerUtil.getServer(logInDialog.getHostName());
                    client.logIn(server, logInDialog.getUserName());
                    client.sendRefreshEvent();
                    logInDialog.setPrefs();
                    break;
                } catch (RemoteException e) {
                    logInDialog.clear();
                    String message = "Couldn't log in. Please try again.";
                    Logger.global.log(Level.INFO, message, e);
                    new ErrorDialog(message, e).show();
                }
            }
        } catch (Throwable e) {
            final String message = "Unexpected error.";
            Logger.global.log(Level.SEVERE, message, e);
            new ErrorDialog(message, e).show();
            System.exit(1);
        }
    }

    private static void registerQuitHandler(Client pClient) {
        String handlerClassName =
            Configuration.instance().get(Main.class, QUIT_HANDLER, "");
        if (! handlerClassName.equals("")) {
            try {
                Class handlerClass = Class.forName(handlerClassName);
                QuitHandler handler = (QuitHandler) handlerClass.newInstance();
                handler.register(pClient);
                Logger.global.config(
                    "Registered QuitHandler " + handlerClassName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(
                    "Couldn't load handler class " + handlerClassName, e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(
                    "Couldn't instantiate handler " + handlerClassName, e);
            } catch (InstantiationException e) {
                throw new RuntimeException(
                    "Couldn't instantiate handler " + handlerClassName, e);
            }

        }
    }

    private Main() {
    }

}
