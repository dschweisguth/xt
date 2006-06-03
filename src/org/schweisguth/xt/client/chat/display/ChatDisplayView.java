package org.schweisguth.xt.client.chat.display;

import java.awt.Color;
import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import org.schweisguth.xt.common.util.logging.Level;
import org.schweisguth.xt.common.util.logging.Logger;

/**
 * TODO why doesn't this class work under Java 1.5?
 */
class ChatDisplayView extends JEditorPane {
    private static final String END_ID = "end";

    public ChatDisplayView() {
        super("text/html",
            "<html><body><a id=\"" + END_ID + "\"></a></body></html>");
        setBackground(Color.white);
        setEditable(false);
    }

    public final void append(String pText) {
        if (pText.length() == 0) {
            return;
        }
        HTMLDocument doc = (HTMLDocument) getDocument();
        try {
            doc.insertBeforeStart(doc.getElement(END_ID), pText + "<br>");
            setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            Logger.global.log(Level.WARNING,
                "Couldn't find element \"" + END_ID + "\"", e);
        } catch (IOException e) {
            Logger.global.log(Level.WARNING, "Couldn't insert", e);
        }
    }

}
