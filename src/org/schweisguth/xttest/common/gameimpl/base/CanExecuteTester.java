package org.schweisguth.xttest.common.gameimpl.base;

import java.util.Iterator;
import junit.framework.Assert;
import org.schweisguth.xt.common.command.ApproveCommand;
import org.schweisguth.xt.common.command.ChallengeCommand;
import org.schweisguth.xt.common.command.ChatCommand;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.command.DrawForFirstCommand;
import org.schweisguth.xt.common.command.DrawNewTilesCommand;
import org.schweisguth.xt.common.command.DrawStartingTilesCommand;
import org.schweisguth.xt.common.command.EndGameCommand;
import org.schweisguth.xt.common.command.ExchangeCommand;
import org.schweisguth.xt.common.command.FinishCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.OverruleChallengeCommand;
import org.schweisguth.xt.common.command.PassCommand;
import org.schweisguth.xt.common.command.RearrangeBoardCommand;
import org.schweisguth.xt.common.command.RearrangeRackCommand;
import org.schweisguth.xt.common.command.StartCommand;
import org.schweisguth.xt.common.command.StartNewGameCommand;
import org.schweisguth.xt.common.command.SustainChallengeCommand;
import org.schweisguth.xt.common.command.TransferAnythingCommand;
import org.schweisguth.xt.common.domain.Player;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.stateimpl.StateImpl;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.SetList;

public class CanExecuteTester {
    private final SetList mFalse = new ArraySetList();
    private final SetList mTrue = new ArraySetList();

    public CanExecuteTester() {
        mFalse.add(new ApproveCommand());
        mFalse.add(new ChallengeCommand());
        mFalse.add(new DrawForFirstCommand());
        mFalse.add(new DrawNewTilesCommand());
        mFalse.add(new DrawStartingTilesCommand());
        mFalse.add(new EndGameCommand());
        mFalse.add(new ExchangeCommand(new int[] { 0 }));
        mFalse.add(new FinishCommand());
        mFalse.add(new JoinCommand());
        mFalse.add(new OverruleChallengeCommand());
        mFalse.add(new PassCommand());
        mFalse.add(new RearrangeRackCommand(0, 1));
        mFalse.add(
            new RearrangeBoardCommand(new Position(7, 7), new Position(7, 8)));
        mFalse.add(new StartCommand());
        mFalse.add(new StartNewGameCommand());
        mFalse.add(new SustainChallengeCommand());
        mFalse.add(new TransferAnythingCommand());
        mTrue.add(new ChatCommand("x"));
    }

    public void addFalse(Command pCommand) {
        Assert.assertNotNull(pCommand);
        mFalse.add(pCommand);
        mTrue.remove(pCommand);
    }

    public void addTrue(Command pCommand) {
        Assert.assertNotNull(pCommand);
        mFalse.remove(pCommand);
        mTrue.add(pCommand);
    }

    public void doAssert(StateImpl pState, String pPlayer) {
        Assert.assertNotNull(pState);
        Player.assertIsValid(pPlayer);

        for (Iterator iterator = mFalse.iterator(); iterator.hasNext();) {
            Command command = (Command) iterator.next();
            Assert.assertFalse(
                pPlayer + " should not be able to execute " + command +
                    " but can",
                pState.canExecute(new Request(pPlayer, command)));
        }
        for (Iterator iterator = mTrue.iterator(); iterator.hasNext();) {
            Command command = (Command) iterator.next();
            Assert.assertTrue(
                pPlayer + " should be able to execute " + command +
                    " but can't",
                pState.canExecute(new Request(pPlayer, command)));
        }

    }

}
