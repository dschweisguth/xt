package org.schweisguth.xt.client.boardimpl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.schweisguth.xt.client.board.BoardModel;
import org.schweisguth.xt.client.board.BoardView;
import org.schweisguth.xt.common.domain.Position;

public class BoardViewImpl extends JPanel implements BoardView {
    // Fields
    private final BoardTableView mTable = new BoardTableView();

    // Constructors

    public BoardViewImpl() {
        super(new GridLayout(1, 1));

        // Table
        add(mTable);

        // Border
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.white),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createLineBorder(Color.white))));

        // Size
        Dimension size = mTable.getPreferredSize();
        size.height += 6;
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);

    }

    // Methods: delegate to table

    public BoardModel getBoardModel() {
        return mTable.getBoardModel();
    }

    public Position getSelection() {
        return mTable.getSelection();
    }

    public boolean selectionIsEmpty() {
        return mTable.selectionIsEmpty();
    }

    public void clearSelection() {
        mTable.clearSelection();
    }

    public void addBoardViewListener(BoardViewListener pBoardViewListener) {
        mTable.addBoardViewListener(pBoardViewListener);
    }

    public void setCellSelectionEnabled(boolean pIsEnabled) {
        mTable.setCellSelectionEnabled(pIsEnabled);
    }

}
