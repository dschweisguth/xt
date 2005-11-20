package org.schweisguth.xt.client.util;

import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Listener;
import org.schweisguth.xt.common.util.collection.HashStickyMap;
import org.schweisguth.xt.common.util.collection.StickyMap;
import org.schweisguth.xt.common.util.contract.Assert;

public class CardListener implements Listener {
    // Constants
    private static final String[] GAME_STATES =
        {
            Game.JOINING,
            Game.DRAWING_FOR_FIRST,
            Game.DRAWING_STARTING_TILES,
            Game.MOVING,
            Game.APPROVING,
            Game.CHALLENGING,
            Game.DRAWING_NEW_TILES,
            Game.ENDED
        };

    // Fields
    private final StickyMap mGameStateToCard = new HashStickyMap();
    private final CardView mView;

    // Constructors

    public CardListener(CardView pView, String[] pCards) {
        Assert.assertNotNull(pView);
        Assert.assertNotNull(pCards);
        Assert.assertEquals(GAME_STATES.length, pCards.length);
        mView = pView;
        for (int i = 0; i < GAME_STATES.length; i++) {
            mGameStateToCard.put(GAME_STATES[i], pCards[i]);
        }
    }

    // Methods

    public void send(Event pEvent) {
        mView.show(
            (String) mGameStateToCard.get(pEvent.getGame().getState()));
    }

}
