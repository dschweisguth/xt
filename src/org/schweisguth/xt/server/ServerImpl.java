package org.schweisguth.xt.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.ListenableGame;
import org.schweisguth.xt.common.game.ListenerOperations;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.server.NATSocketFactory;
import org.schweisguth.xt.common.server.Server;
import org.schweisguth.xt.common.util.io.IOUtil;
import org.schweisguth.xt.common.util.logging.Level;
import org.schweisguth.xt.common.util.logging.Logger;

public class ServerImpl extends UnicastRemoteObject implements Server {
    // Constants
    private static final String SAVE_FILE = "game.ser";
    private static final String NEW_SAVE_FILE = "game-new.ser";

    // Fields: static
    private static boolean sInitializedRMI = false;

    // Methods: static

    public static void main(String[] pArgs) throws Exception {
        // Initialize RMI
        try {
            if (! sInitializedRMI) {
                RMISocketFactory.setSocketFactory(new NATSocketFactory());
                System.setProperty("java.security.policy", "xt.policy");
                LocateRegistry.createRegistry(1099);
                System.setSecurityManager(new RMISecurityManager());
                sInitializedRMI = true;
            }
        } catch (IOException e) {
            throw new RemoteException("Couldn't set socket factory", e);
        }

        // Load game from disk or construct a new one
        ListenableGame game = null;
        try {
            FileInputStream bytes = new FileInputStream(new File(SAVE_FILE));
            try {
                ObjectInputStream objects = new ObjectInputStream(bytes);
                try {
                    game = (ListenableGame) objects.readObject();
                } finally {
                    IOUtil.close(objects);
                }
            } finally {
                IOUtil.close(bytes);
            }
        } catch (FileNotFoundException e) {
            Logger.global.info("Couldn't find previous game to load");
        }
        if (game == null) {
            game = new GameImpl();
        }

        // Bind the game to the registry
        try {
            Naming.rebind(NAME, new ServerImpl(game));
            Logger.global.info("Server started.");
        } catch (MalformedURLException e) {
            throw new RemoteException("Couldn't bind server", e);
        }

    }

    public static void clear() {
        new File(SAVE_FILE).delete();
    }

    // Fields
    private final ListenableGame mDelegate;

    // Constructors

    private ServerImpl(ListenableGame pDelegate) throws RemoteException {
        mDelegate = pDelegate;
    }

    // Methods: implements Server

    public Game getGame() {
        return mDelegate;
    }

    public void execute(Request pRequest) {
        try {
            mDelegate.execute(pRequest);
            try {
                File newSaveFile = new File(NEW_SAVE_FILE);
                FileOutputStream bytes = new FileOutputStream(newSaveFile);
                try {
                    ObjectOutputStream objects = new ObjectOutputStream(bytes);
                    try {
                        objects.writeObject(mDelegate);
                    } finally {
                        IOUtil.close(objects);
                    }
                } finally {
                    IOUtil.close(bytes);
                }
                boolean renameSucceeded = newSaveFile.renameTo(new File(SAVE_FILE));
                if (! renameSucceeded) {
                    Logger.global.warning("Couldn't rename " + NEW_SAVE_FILE +
                        " to " + SAVE_FILE);
                }
            } catch (IOException e) {
                Logger.global.warning("Couldn't save game", e);
            }
        } catch (Error e) {
            logExecuteError(pRequest, e);
            throw e;
        } catch (RuntimeException e) {
            logExecuteError(pRequest, e);
            throw e;
        }
    }

    private static void logExecuteError(Request pRequest, Throwable e) {
        Logger.global.log(Level.SEVERE, "Couldn't execute " + pRequest, e);
    }

    public void addListener(String pPlayer, ListenerOperations pListener) {
        try {
            mDelegate.addListener(pPlayer, pListener);
        } catch (Error e) {
            handleAddListenerError(e);
            throw e;
        } catch (RuntimeException e) {
            handleAddListenerError(e);
            throw e;
        }
    }

    private static void handleAddListenerError(Throwable e) {
        Logger.global.log(Level.SEVERE, "Couldn't add listener", e);
    }

    public void removeListener(ListenerOperations mListener) {
        try {
            mDelegate.removeListener(mListener);
        } catch (Error e) {
            handleRemoveListenerError(e);
            throw e;
        } catch (RuntimeException e) {
            handleRemoveListenerError(e);
            throw e;
        }
    }

    private static void handleRemoveListenerError(Throwable e) {
        Logger.global.log(Level.SEVERE, "Couldn't remove listener", e);
    }

}
