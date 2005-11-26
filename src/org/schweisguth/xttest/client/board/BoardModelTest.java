package org.schweisguth.xttest.client.board;

import junit.framework.TestCase;
import org.schweisguth.xt.client.board.BoardModel;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xt.common.domain.Board;

public class BoardModelTest extends TestCase {
    public void testSetValueAt() {
        BoardModel model = new BoardModel();
        model.setValueAt(Tile.get('A'), 0, 0);

        Board expectedBoard = new Board();
        expectedBoard.place(Tile.get('A'), 0, 0);
        assertEquals(expectedBoard, model.getBoard());
    }

    public void testGetValueAtTile() {
        BoardModel model = new BoardModel();
        model.setValueAt(Tile.get('A'), 0, 0);

        assertEquals(Tile.get('A'), model.getValueAt(0, 0));
    }

    public void testGetValueAtNull() {
        BoardModel model = new BoardModel();

        assertNull(model.getValueAt(0, 0));
    }

}
