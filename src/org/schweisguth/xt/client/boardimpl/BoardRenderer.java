package org.schweisguth.xt.client.boardimpl;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import org.schweisguth.xt.client.util.Constants;
import org.schweisguth.xt.client.util.JBox;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.Modifier;
import org.schweisguth.xt.common.util.collection.HashStickyMap;
import org.schweisguth.xt.common.util.collection.HashStickySetMap;
import org.schweisguth.xt.common.util.collection.StickyMap;
import org.schweisguth.xt.common.util.collection.StickySetMap;

class BoardRenderer extends JBox implements TableCellRenderer {
    // Constants
    private static final Border BORDER = BorderFactory.createEmptyBorder();
    private static final StickySetMap MODIFIER_COLORS = new HashStickySetMap();

    static {
        MODIFIER_COLORS.put(Modifier.DEFAULT, Constants.BOARD_BACKGROUND_COLOR);
        MODIFIER_COLORS.put(Modifier.DOUBLE_LETTER, Color.cyan);
        MODIFIER_COLORS.put(Modifier.TRIPLE_LETTER, Color.blue);
        MODIFIER_COLORS.put(Modifier.DOUBLE_WORD, Color.pink);
        MODIFIER_COLORS.put(Modifier.TRIPLE_WORD, Color.red);
    }

    private static final StickyMap MODIFIER_WORD_1 = new HashStickyMap();

    static {
        MODIFIER_WORD_1.put(Modifier.DOUBLE_LETTER, "DOUBLE");
        MODIFIER_WORD_1.put(Modifier.TRIPLE_LETTER, "TRIPLE");
        MODIFIER_WORD_1.put(Modifier.DOUBLE_WORD, "DOUBLE");
        MODIFIER_WORD_1.put(Modifier.TRIPLE_WORD, "TRIPLE");
    }

    private static final StickyMap MODIFIER_WORD_2 = new HashStickyMap();

    static {
        MODIFIER_WORD_2.put(Modifier.DOUBLE_LETTER, "LETTER");
        MODIFIER_WORD_2.put(Modifier.TRIPLE_LETTER, "LETTER");
        MODIFIER_WORD_2.put(Modifier.DOUBLE_WORD, "WORD");
        MODIFIER_WORD_2.put(Modifier.TRIPLE_WORD, "WORD");
    }

    // Fields
    private final JLabel mLabelOne = new JLabel();
    private final JLabel mLabelTwo = new JLabel();

    // Constructors

    public BoardRenderer() {
        super(BoxLayout.Y_AXIS);

        mLabelOne.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        mLabelOne.setAlignmentY(JLabel.CENTER_ALIGNMENT);

        mLabelTwo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        mLabelTwo.setAlignmentY(JLabel.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(mLabelOne);
        add(mLabelTwo);
        add(Box.createVerticalGlue());

    }

    // Methods

    public Component getTableCellRendererComponent(JTable pTable, Object pValue,
        boolean pIsSelected, boolean pHasFocus, int pRow, int pColumn) {
        Modifier modifier = Board.getModifier(pColumn, pRow);

        // Border
        setBorder(pHasFocus
            ? UIManager.getBorder("Table.focusCellHighlightBorder")
            : BORDER);

        // Colors
        Color foregroundColor;
        Color backgroundColor;
        if (pIsSelected) {
            foregroundColor = pTable.getSelectionForeground();
            backgroundColor = pTable.getSelectionBackground();
        } else {
            foregroundColor = pTable.getForeground();
            backgroundColor = (Color) MODIFIER_COLORS.get(modifier);
        }
        setForeground(foregroundColor);
        setBackground(backgroundColor);

        // Text
        if (pRow == 7 && pColumn == 7) {
            mLabelOne.setText("+");
            mLabelOne.setFont(Constants.TILE_LETTER_FONT);
            mLabelTwo.setText("");
            mLabelTwo.setFont(Constants.MODIFIER_FONT);
        } else {
            mLabelOne.setText((String) MODIFIER_WORD_1.get(modifier));
            mLabelOne.setFont(Constants.MODIFIER_FONT);
            mLabelTwo.setText((String) MODIFIER_WORD_2.get(modifier));
            mLabelTwo.setFont(Constants.MODIFIER_FONT);
        }

        return this;
    }

}
