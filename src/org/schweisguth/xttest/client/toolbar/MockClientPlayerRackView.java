package org.schweisguth.xttest.client.toolbar;

import java.util.Iterator;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelListener;
import org.schweisguth.xt.client.rack.ClientPlayerRackView;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.SetList;

class MockClientPlayerRackView implements ClientPlayerRackView {
    // Fields
    private int[] mSelectedColumns = new int[] { };
    private final SetList mListeners = new ArraySetList();

    // Methods: implements ClientPlayerRackView

    public void addRackViewListener(TableColumnModelListener pListener) {
        mListeners.add(pListener);
    }

    public int getSelectedColumnCount() {
        return mSelectedColumns.length;
    }

    public int getSelectedColumn() {
        throw new UnsupportedOperationException();
    }

    public int[] getSelectedColumns() {
        return mSelectedColumns;
    }

    public void clearSelection() {
        throw new UnsupportedOperationException();
    }

    // Methods: for setting mock state

    public void setSelectedColumns(int[] pSelectedColumns) {
        mSelectedColumns = pSelectedColumns;
        Iterator listeners = mListeners.iterator();
        while (listeners.hasNext()) {
            TableColumnModelListener listener =
                (TableColumnModelListener) listeners.next();
            listener.columnSelectionChanged(
                new ListSelectionEvent(this, 0, 0, false));
        }
    }

}
