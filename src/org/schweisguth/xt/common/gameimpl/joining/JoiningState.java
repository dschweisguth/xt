package org.schweisguth.xt.common.gameimpl.joining;

import org.schweisguth.xt.common.command.JoinCommand;
import org.schweisguth.xt.common.command.StartCommand;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.stateimpl.StateImpl;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.SetList;
import org.schweisguth.xt.common.util.contract.Assert;

public class JoiningState extends StateImpl {
    private static final long serialVersionUID = 6686375094310674709L;

    // Constructors

    public JoiningState() {
        this(new ArraySetList());
    }

    public JoiningState(String[] pPlayers) {
        this(new ArraySetList(pPlayers));
    }

    private JoiningState(SetList pPlayers) {
        super(pPlayers, createEmptyRacks(pPlayers), new BoxLid(), new Board(),
            new ScoreSheet(pPlayers));
        Assert.assertTrue(getScoreSheet().isIn(ScoreSheet.EMPTY));
    }

    // Methods

    public String getName() {
        return Game.JOINING;
    }

    // Methods: commands for subclasses

    public boolean canExecute(String pPlayer, JoinCommand pCommand) {
        return getPlayerCount() < Game.MAX_PLAYERS && !isPlaying(pPlayer);
    }

    public Event execute(String pPlayer, JoinCommand pCommand) {
        getPlayers().add(pPlayer);
        getRacks().put(pPlayer, new Rack());
        getScoreSheet().addPlayer(pPlayer);
        return new JoinedEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

    public boolean canExecute(String pPlayer, StartCommand pCommand) {
        return isPlaying(pPlayer) && getPlayerCount() >= 2;
    }

    public Event execute(String pPlayer, StartCommand pCommand) {
        getContext().goToDrawingForFirstState(this);
        return new StartedEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

}
