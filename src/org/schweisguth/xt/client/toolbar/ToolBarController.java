package org.schweisguth.xt.client.toolbar;

import javax.swing.JComponent;
import org.schweisguth.xt.client.action.CommandAction;
import org.schweisguth.xt.client.seat.SeatController;
import org.schweisguth.xt.client.server.Client;
import org.schweisguth.xt.client.util.CardListener;
import org.schweisguth.xt.common.command.ApproveCommand;
import org.schweisguth.xt.common.command.ChallengeCommand;
import org.schweisguth.xt.common.command.FinishCommand;
import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.OverruleChallengeCommand;
import org.schweisguth.xt.common.command.PassCommand;
import org.schweisguth.xt.common.command.StartCommand;
import org.schweisguth.xt.common.command.StartNewGameCommand;
import org.schweisguth.xt.common.command.SustainChallengeCommand;
import org.schweisguth.xt.common.util.contract.Assert;

public class ToolBarController {
    // Fields
    private final ToolBarView mView;

    // Constructors

    public ToolBarController(Client pClient, SeatController pSeatController) {
        Assert.assertNotNull(pClient);
        Assert.assertNotNull(pSeatController);

        mView = new ToolBarView(
            new CommandAction(pClient, new JoinCommand()),
            new CommandAction(pClient, new StartCommand()),
            new CommandAction(pClient, new FinishCommand()),
            new CommandAction(pClient, new ApproveCommand()),
            new CommandAction(pClient, new ChallengeCommand()),
            new CommandAction(pClient, new OverruleChallengeCommand()),
            new CommandAction(pClient, new SustainChallengeCommand()),
            new ExchangeAction(pClient, pSeatController),
            new CommandAction(pClient, new PassCommand()),
            new EndGameAction(pClient),
            new CommandAction(pClient, new StartNewGameCommand()));
        pClient.addListener(new CardListener(mView, new String[]
            {
                ToolBarView.JOINING,
                ToolBarView.NO_BUTTONS,
                ToolBarView.NO_BUTTONS,
                ToolBarView.PLAYING,
                ToolBarView.PLAYING,
                ToolBarView.PLAYING,
                ToolBarView.PLAYING,
                ToolBarView.ENDED
            }));

    }

    // Methods

    public JComponent getView() {
        return mView;
    }

    public ToolBarView getToolBarView() {
        return mView;
    }

}
