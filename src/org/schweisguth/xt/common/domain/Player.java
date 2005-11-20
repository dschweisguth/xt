package org.schweisguth.xt.common.domain;

import java.util.Collection;
import java.util.Iterator;
import org.schweisguth.xt.common.util.contract.Assert;

public class Player {
    private static boolean isValid(String pPlayer) {
        return pPlayer != null && pPlayer.length() > 0;
    }

    public static void assertIsValid(String pPlayer) {
        Assert.assertTrue(isValid(pPlayer));
    }

    public static void assertAreValid(Collection pPlayers) {
        Iterator players = pPlayers.iterator();
        while (players.hasNext()) {
            assertIsValid((String) players.next());
        }
    }

    private Player() {
    }

}
