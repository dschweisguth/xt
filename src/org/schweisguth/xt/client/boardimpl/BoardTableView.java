package org.schweisguth.xt.client.boardimpl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.schweisguth.xt.client.board.BoardModel;
import org.schweisguth.xt.client.util.Constants;
import org.schweisguth.xt.client.util.TileRenderer;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xt.common.domain.Tile;

class BoardTableView extends JTable {
    // Constructors

    public BoardTableView() {
        super(new BoardModel());

        // Size
        setRowHeight(Constants.TILE_HEIGHT);
        TableColumnModel columnModel = getColumnModel();
        for (int i = 0; i < Board.getSize(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setMinWidth(Constants.TILE_HEIGHT);
            column.setMaxWidth(Constants.TILE_HEIGHT);
            column.setPreferredWidth(Constants.TILE_HEIGHT);
            column.setWidth(Constants.TILE_HEIGHT);
        }
        // -1 hides the grid lines below the last row and to the right of the
        // last column of tiles
        int boardHeight = Constants.BOARD_SIZE - 1;
        Dimension boardSize = new Dimension(boardHeight + 6, boardHeight);
        setMaximumSize(boardSize);
        setMinimumSize(boardSize);
        setPreferredSize(boardSize);

        // Appearance
        setDefaultRenderer(Tile.class, new BoardOrTileRenderer());
        setGridColor(Color.white);

        // Selection
        setCellSelectionEnabled(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    private static class BoardOrTileRenderer implements TableCellRenderer {
        // Fields
        private TileRenderer mTileRenderer = new TileRenderer();
        private BoardRenderer mBoardRenderer = new BoardRenderer();

        // Methods

        public Component getTableCellRendererComponent(JTable pTable,
            Object pValue, boolean pIsSelected, boolean pHasFocus, int pRow,
            int pColumn) {
            Board board = ((BoardTableView) pTable).getBoardModel().getBoard();
            if (board.hasTile(pColumn, pRow)) {
                mTileRenderer.setIsUnapproved(
                    board.hasUnapprovedTile(pColumn, pRow));
                mTileRenderer =
                    (TileRenderer) mTileRenderer.getTableCellRendererComponent(
                        pTable, pValue, pIsSelected, pHasFocus, pRow, pColumn);
                return mTileRenderer;
            } else {
                mBoardRenderer = (BoardRenderer)
                    mBoardRenderer.getTableCellRendererComponent(pTable, pValue,
                        pIsSelected, pHasFocus, pRow, pColumn);
                return mBoardRenderer;
            }
        }

    }

    // Methods: overrides

    public boolean isCellEditable(int row, int column) {
        return false;
    }

    // Methods: other

    public BoardModel getBoardModel() {
        return (BoardModel) getModel();
    }

    public Position getSelection() {
        int row = getSelectionModel().getMinSelectionIndex();
        int column = getColumnModel().getSelectedColumns()[0];
        return new Position(column, row);
    }

    public void addBoardViewListener(BoardViewListener pBoardViewListener) {
        getColumnModel().addColumnModelListener(pBoardViewListener);
        getSelectionModel().addListSelectionListener(pBoardViewListener);
    }

    /**
     * Override the default so that the row selection is cleared first.
     * If the column selection is cleared first, one of the listeners calls
     * selectionIsEmpty(), it returns true, and something blows up. Rewriting
     * selectionIsEmpty() to return true if either the row or column is empty
     * solves that problem, but introduces a new problem where moving a tile
     * from rack to board and then on top of another tile on the board doesn't
     * replace it.
     * TODO: Revisit the need for this method after rewriting the board and rack controllers
     */
    public void clearSelection() {
        if (! selectionIsEmpty()) {
            getSelectionModel().clearSelection();
            getColumnModel().getSelectionModel().clearSelection();
        }
    }

    public boolean selectionIsEmpty() {
        return getSelectionModel().isSelectionEmpty();
    }

}
