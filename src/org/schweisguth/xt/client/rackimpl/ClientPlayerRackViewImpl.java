package org.schweisguth.xt.client.rackimpl;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.schweisguth.xt.client.rack.ClientPlayerRackView;
import org.schweisguth.xt.client.util.Constants;
import org.schweisguth.xt.client.util.TileRenderer;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.Tile;

class ClientPlayerRackViewImpl extends JTable
    implements PlayerRackView, ClientPlayerRackView {
    // Constructors

    public ClientPlayerRackViewImpl(ClientPlayerRackModel pModel) {
        super(pModel);

        // Size
        setRowHeight(Constants.TILE_HEIGHT);
        TableColumnModel columnModel = getColumnModel();
        for (int i = 0; i < Rack.MAX_TILE_COUNT; i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setMaxWidth(Constants.TILE_HEIGHT);
            column.setMinWidth(Constants.TILE_HEIGHT);
            column.setPreferredWidth(Constants.TILE_HEIGHT);
            column.setWidth(Constants.TILE_HEIGHT);
        }
        Dimension maxSize = new Dimension(
            Constants.TILE_HEIGHT * Rack.MAX_TILE_COUNT, Constants.TILE_HEIGHT);
        setMaximumSize(maxSize);
        setMinimumSize(maxSize);
        setPreferredSize(maxSize);

        // Appearance
        setDefaultRenderer(Tile.class, new RackOrTileRenderer());
        setGridColor(Constants.TILE_BACKGROUND_COLOR);

        // Selection
        setCellSelectionEnabled(true);
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    }

    private static class RackOrTileRenderer implements TableCellRenderer {
        // Fields
        private TableCellRenderer mRackRenderer = new RackRenderer();
        private TileRenderer mTileRenderer = new TileRenderer();

        // Methods

        public Component getTableCellRendererComponent(JTable pTable,
            Object pValue, boolean pIsSelected, boolean pHasFocus, int pRow,
            int pColumn) {
            if (pValue == null) {
                mRackRenderer =
                    (RackRenderer) mRackRenderer.getTableCellRendererComponent(
                        pTable, pValue, pIsSelected, pHasFocus, pRow, pColumn);
                return (Component) mRackRenderer;
            } else {
                mTileRenderer =
                    (TileRenderer) mTileRenderer.getTableCellRendererComponent(
                        pTable, pValue, pIsSelected, pHasFocus, pRow, pColumn);
                return mTileRenderer;
            }
        }

    }

    private static class RackRenderer extends DefaultTableCellRenderer {
        private RackRenderer() {
            setBackground(Constants.TILE_BACKGROUND_COLOR);
        }
    }

    // Methods: overrides

    public boolean isCellEditable(int row, int column) {
        return false;
    }

    // Methods: implements ClientPlayerRackView

    public void addRackViewListener(TableColumnModelListener pListener) {
        getColumnModel().addColumnModelListener(pListener);
    }

    // Methods: implements PlayerRackView

    public PlayerRackModel getPlayerRackModel() {
        return (PlayerRackModel) getModel();
    }

}
