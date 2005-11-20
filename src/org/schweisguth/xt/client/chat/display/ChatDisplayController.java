package org.schweisguth.xt.client.chat.display;

import java.awt.Component;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.StartNewGameCommand;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Listener;
import org.schweisguth.xt.common.game.Request;

public class ChatDisplayController {
    // Constants
    public static final String WELCOME_BACK = "Welcome back to CrossTease.";
    public static final String START_NEW_GAME =
        " Press Start New Game to start a new game.";
    public static final String WELCOME = "Welcome to CrossTease.";
    public static final String JOIN_OR_WATCH =
        " Press Join Game to join the game, or just watch.";
    public static final String WATCH =
        " A game is in progress, but you can watch.";

    // Fields
    private final ChatDisplayView mView = new ChatDisplayView();
    private final Client mClient;

    // Constructors

    public ChatDisplayController(Client pClient) {
        mClient = pClient;
        pClient.addListener(new ChatListener());
    }

    private class ChatListener implements Listener {
        private boolean mPrintedWelcomeMessage = false;

        public void send(Event pEvent) {
            if (! mPrintedWelcomeMessage) {
                printWelcomeMessage(pEvent);
                mPrintedWelcomeMessage = true;
            }
            mView.append(pEvent.toHTML());
        }

        private void printWelcomeMessage(Event pEvent) {
            String message;
            Game game = pEvent.getGame();
            String player = mClient.getPlayer();
            if (game.getPlayers().contains(player)) {
                message = WELCOME_BACK;
                if (
                    game.canExecute(new Request(player, new StartNewGameCommand())))
                {
                    message += START_NEW_GAME;
                }
            } else {
                message = WELCOME;
                if (game.canExecute(new Request(player, new JoinCommand()))) {
                    message += JOIN_OR_WATCH;
                } else if (
                    game.canExecute(new Request(player, new StartNewGameCommand())))
                {
                    message += START_NEW_GAME;
                } else {
                    message += WATCH;
                }
            }
            mView.append(message);
        }

    }

    // Methods

    public Component getView() {
        return mView;
    }

    public String getText() {
        return mView.getText();
    }

}
