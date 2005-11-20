package org.schweisguth.xt.client.board;

import javax.swing.table.DefaultTableModel;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xt.common.domain.Tile;

public class BoardModel extends DefaultTableModel {
    // Fields
    private Board mBoard = new Board();

    // Methods: overrides

    public Class getColumnClass(int columnIndex) {
        return Tile.class;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        mBoard.place((Tile) aValue, new Position(columnIndex, rowIndex));
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    // Methods: implements TableModel

    public int getColumnCount() {
        return Board.getSize();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return mBoard.hasTile(columnIndex, rowIndex)
            ? mBoard.getTile(columnIndex, rowIndex)
            : null;
    }

    public int getRowCount() {
        return Board.getSize();
    }

    // Methods: other

    public Board getBoard() {
        return mBoard;
    }

    public void setBoard(Board pBoard) {
        if (mBoard != pBoard) {
            mBoard = pBoard;
            fireTableDataChanged();
        }
    }

}
