package org.schweisguth.xttest.client.rackimpl;

import junit.framework.TestCase;
import org.schweisguth.xt.client.rackimpl.ClientPlayerRackModel;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.Tile;

public class ClientPlayerRackModelTest extends TestCase {
    public void testSetValueAt() {
        ClientPlayerRackModel model = new ClientPlayerRackModel();
        model.setValueAt(Tile.get('A'), 0, 0);

        assertEquals(new Rack("A"), model.getRack());

    }

    public void testGetValueAtTile() {
        ClientPlayerRackModel model = new ClientPlayerRackModel();
        model.setValueAt(Tile.get('A'), 0, 0);

        assertEquals(Tile.get('A'), model.getValueAt(0, 0));
    }

    public void testGetValueAtNull() {
        ClientPlayerRackModel model = new ClientPlayerRackModel();

        assertNull(model.getValueAt(0, 0));

    }

}
