package org.schweisguth.xt.common.gameimpl.rackscanhavetiles;

import java.util.Iterator;
import java.util.Map;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xt.common.domain.Transfer;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.gameimpl.state.State;
import org.schweisguth.xt.common.gameimpl.stateimpl.StateImpl;
import org.schweisguth.xt.common.util.collection.SetList;

public abstract class RacksCanHaveTilesState extends StateImpl {
    private static final long serialVersionUID = 8690259224805614810L;

    // Constructors

    protected RacksCanHaveTilesState(State pSource) {
        super(pSource);
        assertPlayerCountAfterStarting();
    }

    protected RacksCanHaveTilesState(SetList pPlayers, Map pRacks,
        Board pBoard, ScoreSheet pScoreSheet) {
        super(pPlayers, pRacks, newBoxLidMinus(pRacks, pBoard), pBoard,
            pScoreSheet);
        assertPlayerCountAfterStarting();
    }

    private static BoxLid newBoxLidMinus(Map pRacks, Board pBoard) {
        BoxLid boxLid = new BoxLid();
        boxLid = subtract(boxLid, pRacks);
        boxLid = subtract(boxLid, pBoard);
        return boxLid;
    }

    private static BoxLid subtract(BoxLid pBoxLid, Map pRacks) {
        for (Iterator racks = pRacks.values().iterator(); racks.hasNext();) {
            Rack rack = (Rack) racks.next();
            for (Iterator tiles = rack.iterator(); tiles.hasNext();) {
                Tile tile = (Tile) tiles.next();
                if (tile != null) {
                    pBoxLid.draw(tile);
                }
            }
        }
        return pBoxLid;
    }

    private static BoxLid subtract(BoxLid pBoxLid, Board pBoard) {
        for (int x = 0; x < Board.getSize(); x++) {
            for (int y = 0; y < Board.getSize(); y++) {
                if (pBoard.hasTile(x, y)) {
                    pBoxLid.draw(pBoard.getTile(x, y));
                }
            }
        }
        return pBoxLid;
    }

    // Methods

    public void setBoxLid(BoxLid pBoxLid) {
        super.setBoxLid(pBoxLid);
    }

    // Methods for subclasses

    protected static String[] getRacks(String[] pRacks, int pCurrentPlayer,
        TransferSet pTransferSet) {
        String[] racks = (String[]) pRacks.clone();
        Iterator transfers = pTransferSet.iterator();
        while (transfers.hasNext()) {
            Transfer transfer = (Transfer) transfers.next();
            int rackPosition = transfer.getRackPosition();
            String rack = racks[pCurrentPlayer];
            racks[pCurrentPlayer] = rack.substring(0, rackPosition) + '-' +
                rack.substring(rackPosition + 1);
        }
        return racks;
    }

    protected static Board getBoard(String pRack, TransferSet pTransferSet) {
        Board board = new Board();
        Rack rack = new Rack(pRack);
        Iterator transfers = pTransferSet.iterator();
        while (transfers.hasNext()) {
            Transfer transfer = (Transfer) transfers.next();
            int rackPosition = transfer.getRackPosition();
            board.place(rack.get(rackPosition), transfer.getBoardPosition());
            rack.remove(rackPosition);
        }
        board.approve();
        return board;
    }

}
