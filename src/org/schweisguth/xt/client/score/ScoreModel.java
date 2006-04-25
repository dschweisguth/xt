package org.schweisguth.xt.client.score;

import javax.swing.table.AbstractTableModel;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.util.collection.ArraySetList;

public class ScoreModel extends AbstractTableModel {
    // Fields
    private ScoreSheet mScoreSheet = new ScoreSheet(new ArraySetList());

    // Methods: implements TableModel

    public int getColumnCount() {
        return mScoreSheet.getPlayerCount();
    }

    public int getRowCount() {
        return mScoreSheet.getRowCount();
    }

    public Object getValueAt(int pRowIndex, int pColumn) {
        return mScoreSheet.get(pRowIndex, pColumn);
    }

    // Methods: overrides AbstractTableModel

    public Class getColumnClass(int pColumn) {
        return Integer.class;
    }

    public String getColumnName(int pColumn) {
        return mScoreSheet.getPlayer(pColumn);
    }

    // Methods: other

    public void setScoreSheet(ScoreSheet pScoreSheet) {
        mScoreSheet = pScoreSheet;
        // TODO send change event only if scoresheet changes
        // TODO send only row change event when appropriate
        fireTableStructureChanged();
    }

}
