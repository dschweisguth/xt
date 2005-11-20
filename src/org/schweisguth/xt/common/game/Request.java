package org.schweisguth.xt.common.game;

import java.io.Serializable;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.domain.Player;
import org.schweisguth.xt.common.util.contract.Assert;

public class Request implements Serializable {
    private static final long serialVersionUID = 178901475293140759L;

    // Fields
    private final String mPlayer;
    private final Command mCommand;

    // Constructors

    public Request(String pPlayer, Command pCommand) {
        Player.assertIsValid(pPlayer);
        Assert.assertNotNull(pCommand);

        mPlayer = pPlayer;
        mCommand = pCommand;

    }

    // Methods: queries

    public String getPlayer() {
        return mPlayer;
    }

    public Command getCommand() {
        return mCommand;
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        if (pOther == null || ! pOther.getClass().equals(getClass())) {
            return false;
        }
        Request other = (Request) pOther;
        return other.mPlayer.equals(mPlayer) &&
            other.getCommand().equals(mCommand);
    }

    public int hashCode() {
        return 3 * mPlayer.hashCode() + mCommand.hashCode();
    }

    public String toString() {
        return "Request(" + mPlayer + ", " + mCommand + ")";
    }

}
