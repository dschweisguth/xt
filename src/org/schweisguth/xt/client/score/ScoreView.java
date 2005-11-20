package org.schweisguth.xt.client.score;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;

// TODO include per-turn score and word

class ScoreView extends JTable {
    public ScoreView() {
        super(new ScoreModel());
        setBackground(Color.white);
        setGridColor(Color.white);
        setColumnSelectionAllowed(false);
        setRowSelectionAllowed(false);
        getTableHeader().setReorderingAllowed(false);
    }

    public ScoreModel getScoreModel() {
        return (ScoreModel) getModel();
    }

    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                scrollRectToVisible(getCellRect(getRowCount() - 1, 0, true));
            }
        });
    }

}
