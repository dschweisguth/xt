package org.schweisguth.xt.client.letterdistribution;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.schweisguth.xt.client.util.Constants;
import org.schweisguth.xt.client.util.LocationManager;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.SetList;

class LetterDistributionView extends JFrame {
    public LetterDistributionView() {
        JPanel contentPane = new JPanel();
        contentPane.setBackground(Constants.BOARD_BACKGROUND_COLOR);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(createLabel("LETTER"));
        contentPane.add(createLabel("DISTRIBUTION"));
        contentPane.add(createLabel(" "));
        contentPane.add(createTable());
        contentPane.add(createLabel("BLANK - 2"));
        setContentPane(contentPane);
        pack();
        LocationManager.manage(this);
    }

    private static JLabel createLabel(String pPText) {
        JLabel label = new JLabel(pPText);
        label.setForeground(Color.black);
        label.setFont(Constants.LETTER_DISTRIBUTION_VIEW_FONT);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setAlignmentY(JLabel.CENTER_ALIGNMENT);
        return label;
    }

    private static JTable createTable() {
        JTable table = new JTable() {
            public boolean isFocusTraversable() {
                return false;
            }
        };

        // Appearance
        table.setBackground(Constants.BOARD_BACKGROUND_COLOR);
        table.setCellSelectionEnabled(false);
        table.setDefaultRenderer(Object.class,
            new LetterDistributionTableCellRenderer());
        table.setShowGrid(false);

        // Contents
        SortedMap tileCounts = BoxLid.getTileCounts();
        SetList entries = new ArraySetList(tileCounts.entrySet());
        int rowCount = tileCounts.size() / 2;
        DefaultTableModel model = new DefaultTableModel(0, 2) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int row = 0; row < rowCount; row++) {
            Vector rowData = new Vector();
            addEntry((Entry) entries.get(row), rowData);
            addEntry((Entry) entries.get(row + rowCount), rowData);
            model.addRow(rowData);
        }
        table.setModel(model);

        return table;
    }

    private static class LetterDistributionTableCellRenderer
        extends DefaultTableCellRenderer {
        private LetterDistributionTableCellRenderer() {
            setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        }

        public Component getTableCellRendererComponent(JTable pTable,
            Object pValue, boolean pIsSelected, boolean pHasFocus, int pRow,
            int pColumn) {
            DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)
                super.getTableCellRendererComponent(pTable, pValue, pIsSelected,
                    pHasFocus, pRow, pColumn);
            renderer.setFont(Constants.LETTER_DISTRIBUTION_VIEW_FONT);
            return renderer;
        }

    }

    private static void addEntry(Entry pEntry, List pRowData) {
        pRowData.add(pEntry.getKey() + "-" + pEntry.getValue());
    }

}
