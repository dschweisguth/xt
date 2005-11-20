package org.schweisguth.xt.common.gameimpl.eventimpl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.collection.SetList;
import org.schweisguth.xt.common.util.contract.Assert;
import org.schweisguth.xt.common.util.object.ValueObject;

public abstract class BaseEvent extends ValueObject implements Event {
    private static final long serialVersionUID = -4963433969060899128L;

    // Constants
    public static final String DRAW_YOUR_STARTING_TILES =
        " {0}, draw your starting tiles.";
    public static final String NO_MORE_TILES =
        " There are no more tiles left in the box lid.";
    public static final String ITS_YOUR_TURN = " {0}, it''s your turn.";
    public static final String STILL_HAS = " {0} still has {1}.";
    public static final String TIED_GAME = " {0} tied with {1} points each!";
    public static final String WINS = " {0} wins with {1} points!";

    // Fields
    private final Game mGame;
    private final Request mRequest;

    // Constructors

    protected BaseEvent(Game pGame, Request pRequest) {
        Assert.assertNotNull(pGame);
        Assert.assertNotNull(pRequest);

        mGame = pGame;
        mRequest = pRequest;

    }

    // Methods: overrides

    protected List getFields() {
        List fields = super.getFields();
        fields.add(mGame);
        fields.add(mRequest);
        return fields;
    }

    // Methods: implements Event

    public Game getGame() {
        return mGame;
    }

    // Methods: for subclasses

    protected static Request assertCommandClass(Class pCommandClass,
        Request pRequest) {
        Assert.assertEquals(pCommandClass, pRequest.getCommand().getClass());
        return pRequest;
    }

    protected Command getCommand() {
        return mRequest.getCommand();
    }

    protected String getPlayer() {
        return mRequest.getPlayer();
    }

    protected static String format(String pPattern, Object pArg) {
        return MessageFormat.format(pPattern, new Object[] { pArg });
    }

    protected static String format(String pPattern, Object pArg1, Object pArg2) {
        return MessageFormat.format(pPattern,
            new Object[] { pArg1, pArg2 });
    }

    private static String format(String pPattern, Object pArg1, int pArg2) {
        return format(pPattern, pArg1, new Integer(pArg2));
    }

    protected static String format(String pPattern, Object pArg1, int pArg2,
        Object pArg3) {
        return MessageFormat.format(pPattern,
            new Object[] { pArg1, new Integer(pArg2), pArg3 });
    }

    static String format(String pPattern, Object pArg1, int pArg2,
        int pArg3) {
        return format(pPattern, pArg1, pArg2, new Integer(pArg3));
    }

    protected String getDrawYourStartingTilesMessage() {
        return format(DRAW_YOUR_STARTING_TILES, getGame().getCurrentPlayer());
    }

    protected String getItsYourTurnMessage() {
        return format(ITS_YOUR_TURN, getGame().getCurrentPlayer());
    }

    protected String getStillHasMessage() {
        StringBuffer message = new StringBuffer();
        for (Iterator players = getGame().getPlayers().iterator();
            players.hasNext();) {
            String player = (String) players.next();
            Rack rack = getGame().getRack(player);
            if (rack.getTileCount() > 0) {
                message.append(format(STILL_HAS, player,
                    CollectionUtil.enumerate(getLetters(rack))));
            }
        }
        return message.toString();
    }

    private static Collection getLetters(Rack pRack) {
        List letters = new ArrayList();
        for (Iterator tiles = pRack.iterator(); tiles.hasNext();) {
            Tile tile = (Tile) tiles.next();
            if (tile != null) {
                letters.add(String.valueOf(tile.getLetter()));
            }
        }
        return letters;
    }

    protected String getWinnerMessage() {
        ScoreSheet scores = getGame().getScoreSheet();
        SetList winners = scores.getWinners();
        if (winners.size() > 1) {
            return format(TIED_GAME, CollectionUtil.enumerate(winners),
                scores.getHighTotal());
        } else {
            return format(WINS, winners.get(0), scores.getHighTotal());
        }
    }

}
