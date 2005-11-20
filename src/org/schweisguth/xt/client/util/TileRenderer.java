package org.schweisguth.xt.client.util;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import org.schweisguth.xt.common.domain.Tile;

public class TileRenderer extends JBox implements TableCellRenderer {
    // Constants
    private static final Color UNAPPROVED_TILE_COLOR = Color.yellow;
    private static final Border BORDER =
        BorderFactory.createLineBorder(Constants.TILE_BORDER_COLOR);

    // Fields
    private final JLabel mLetterLabel = new JLabel();
    private final JLabel mValueLabel = new JLabel();
    private boolean mIsUnapproved = false;

    // Constructors

    public TileRenderer() {
        super(BoxLayout.X_AXIS);

        add(Box.createHorizontalGlue());

        // Letter
        mLetterLabel.setFont(Constants.TILE_LETTER_FONT);
        add(mLetterLabel);

        // Value
        mValueLabel.setFont(Constants.TILE_VALUE_FONT);
        JPanel mValuePanel = new JPanel();
        mValuePanel.setOpaque(false);
        mValuePanel.setLayout(new BoxLayout(mValuePanel, BoxLayout.Y_AXIS));
        mValuePanel.add(Box.createVerticalGlue());
        mValuePanel.add(mValueLabel);
        add(mValuePanel);

        add(Box.createHorizontalGlue());

    }

    // Methods

    public void setIsUnapproved(boolean pIsUnapproved) {
        mIsUnapproved = pIsUnapproved;
    }

    public Component getTableCellRendererComponent(JTable pTable, Object pValue,
        boolean pIsSelected, boolean pHasFocus, int pRow, int pColumn) {
        // Overall
        Color foregroundColor;
        Color backgroundColor;
        if (pIsSelected) {
            foregroundColor = pTable.getSelectionForeground();
            backgroundColor = pTable.getSelectionBackground();
        } else {
            foregroundColor = pTable.getForeground();
            backgroundColor = mIsUnapproved
                ? UNAPPROVED_TILE_COLOR
                : Constants.TILE_BACKGROUND_COLOR;
        }
        setForeground(foregroundColor);
        setBackground(backgroundColor);
        setBorder(pHasFocus
            ? UIManager.getBorder("Table.focusCellHighlightBorder")
            : BORDER);

        // Letter
        mLetterLabel.setText("" + ((Tile) pValue).getLetter());

        // Value
        int value = ((Tile) pValue).getValue();
        mValueLabel.setText(value == 0 ? "" : "" + value);

        return this;
    }

}
