package org.schweisguth.xt.client.rack;

import javax.swing.event.TableColumnModelListener;

public interface ClientPlayerRackView {
    void addRackViewListener(TableColumnModelListener pListener);

    int getSelectedColumnCount();

    int getSelectedColumn();

    int[] getSelectedColumns();

    void clearSelection();
}
