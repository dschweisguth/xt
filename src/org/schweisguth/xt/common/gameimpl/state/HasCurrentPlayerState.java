package org.schweisguth.xt.common.gameimpl.state;

import org.schweisguth.xt.common.command.RearrangeRackCommand;
import org.schweisguth.xt.common.game.Event;

public interface HasCurrentPlayerState extends State {
    String getCurrentPlayer();

    int getCurrentPlayerIndex();

    boolean canExecute(String pPlayer, RearrangeRackCommand pCommand);

    Event execute(String pPlayer, RearrangeRackCommand pCommand);
}
