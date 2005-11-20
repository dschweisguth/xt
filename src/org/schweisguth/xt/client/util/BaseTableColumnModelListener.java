package org.schweisguth.xt.client.util;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

public abstract class BaseTableColumnModelListener
    implements TableColumnModelListener {
    public void columnAdded(TableColumnModelEvent pEvent) {
    }

    public void columnRemoved(TableColumnModelEvent pEvent) {
    }

    public void columnMoved(TableColumnModelEvent pEvent) {
    }

    public void columnMarginChanged(ChangeEvent pEvent) {
    }

    public void columnSelectionChanged(ListSelectionEvent pEvent) {
    }

}
