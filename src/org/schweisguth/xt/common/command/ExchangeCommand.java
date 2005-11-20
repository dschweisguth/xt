package org.schweisguth.xt.common.command;

import java.util.ArrayList;
import java.util.List;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.util.contract.Assert;

public class ExchangeCommand extends Command {
    private static final long serialVersionUID = 5990508418553526566L;

    private final int[] mRackPositions;

    public ExchangeCommand(int[] pRackPositions) {
        super("");
        Assert.assertNotNull(pRackPositions);
        Assert.assertTrue(0 < pRackPositions.length);
        Assert.assertTrue(pRackPositions.length <= Rack.MAX_TILE_COUNT);
        mRackPositions = pRackPositions;
    }

    protected List getFields() {
        return append(super.getFields(), asList(mRackPositions));
    }

    private static List asList(int[] pArray) {
        List list = new ArrayList();
        for (int i = 0; i < pArray.length; i++) {
            list.add(new Integer(pArray[i]));
        }
        return list;
    }

    public int[] getRackPositions() {
        return mRackPositions;
    }

}
