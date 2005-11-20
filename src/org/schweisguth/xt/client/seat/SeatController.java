package org.schweisguth.xt.client.seat;

import javax.swing.event.TableColumnModelListener;
import org.schweisguth.xt.client.rack.ClientPlayerRackView;

public interface SeatController {
    void addClientPlayerRackListener(TableColumnModelListener pListener);

    ClientPlayerRackView getClientPlayerRackView();

    boolean clientPlayerRackViewHasSelection();
}
