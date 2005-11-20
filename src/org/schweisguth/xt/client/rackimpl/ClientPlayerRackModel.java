package org.schweisguth.xt.client.rackimpl;

import javax.swing.table.AbstractTableModel;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xt.common.util.contract.Assert;

class ClientPlayerRackModel extends AbstractTableModel
    implements PlayerRackModel {
    // Fields
    private Rack mRack;

    // Constructors

    public ClientPlayerRackModel() {
        setRack(new Rack());
    }

    // Methods: implements PlayerRackModel

    public Rack getRack() {
        return mRack;
    }

    public void setRack(Rack pRack) {
        Assert.assertNotNull(pRack);

        // Send the change event only when necessary, as it clears the selection
        if (! pRack.equals(mRack)) {
            mRack = pRack;
            fireTableDataChanged();
        }

    }

    // Methods: overrides

    public Class getColumnClass(int pColumnIndex) {
        return Tile.class;
    }

    public int getRowCount() {
        return 1;
    }

    public int getColumnCount() {
        return Rack.MAX_TILE_COUNT;
    }

    public Object getValueAt(int pRowIndex, int pColumnIndex) {
        Assert.assertEquals(0, pRowIndex);
        return mRack.get(pColumnIndex);
    }

    public void setValueAt(Object pTile, int pRowIndex, int pColumnIndex) {
        Assert.assertEquals(0, pRowIndex);

        if (mRack.contains(pColumnIndex)) {
            mRack.remove(pColumnIndex);
        }
        mRack.add((Tile) pTile, pColumnIndex);
        fireTableCellUpdated(pRowIndex, pColumnIndex);

    }

}
