package org.schweisguth.xttest.client.chat;

import java.awt.event.ActionEvent;
import java.util.Collections;
import javax.swing.JTextField;
import junit.framework.TestCase;
import org.schweisguth.xt.client.chat.send.ChatSendController;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.common.command.ChatCommand;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.GameImpl;
import org.schweisguth.xt.common.gameimpl.stateimpl.ChatEvent;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xttest.common.gameimpl.base.LocalClient;
import org.schweisguth.xttest.common.gameimpl.base.TestClient;

public class ChatSendTest extends TestCase {
    public void testCreate() {
        Client client = new LocalClient(new GameImpl(), "player1");
        ChatSendController controller = new ChatSendController(client);
        assertEquals("", controller.getView().getText());
    }

    public void testEnterTextNonEmpty() {
        TestClient client = new TestClient(new GameImpl(), "player1");
        ChatSendController controller = new ChatSendController(client);
        JTextField textField = controller.getView().getTextField();
        String message = "Hi Mom!";
        textField.setText(message);
        ActionEvent event = new ActionEvent(textField, 0, message);
        controller.getView().getAction().actionPerformed(event);

        assertEquals("", textField.getText());

        Game expectedGame = new GameImpl();
        Event expectedEvent = new ChatEvent(expectedGame,
            new Request("player1", new ChatCommand(message)));
        assertEquals(CollectionUtil.asList(expectedEvent), client.getEvents());

    }

    public void testEnterText() {
        TestClient client = new TestClient(new GameImpl(), "player1");
        ChatSendController controller = new ChatSendController(client);
        JTextField textField = controller.getView().getTextField();
        String message = "";
        textField.setText(message);
        ActionEvent event = new ActionEvent(textField, 0, message);
        controller.getView().getAction().actionPerformed(event);

        assertEquals("", textField.getText());

        assertEquals(Collections.EMPTY_LIST, client.getEvents());

    }

}
