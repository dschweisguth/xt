package org.schweisguth.xt.common.gameimpl.stateimpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.schweisguth.xt.common.command.ChatCommand;
import org.schweisguth.xt.common.command.Command;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.Player;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.game.Event;
import org.schweisguth.xt.common.game.Game;
import org.schweisguth.xt.common.game.Request;
import org.schweisguth.xt.common.gameimpl.state.State;
import org.schweisguth.xt.common.gameimpl.state.StateContext;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.collection.HashStickyMap;
import org.schweisguth.xt.common.util.collection.SetList;
import org.schweisguth.xt.common.util.collection.StickyMap;
import org.schweisguth.xt.common.util.contract.Assert;
import org.schweisguth.xt.common.util.exception.ChainedRuntimeException;

public abstract class StateImpl implements State {
    private static final long serialVersionUID = 4167168619407816591L;

    // Fields
    private StateContext mContext = new NullStateContext();
    private final SetList mPlayers = new ArraySetList();
    private final Map mRacks = new HashMap();
    private BoxLid mBoxLid;
    private final Board mBoard;
    private final ScoreSheet mScoreSheet;

    // Constructors

    protected StateImpl(State pSource) {
        this(pSource.getPlayers(), pSource.getRacks(), pSource.getBoxLid(),
            pSource.getBoard(), pSource.getScoreSheet());
    }

    protected StateImpl(SetList pPlayers, Map pRacks, BoxLid pBoxLid,
        Board pBoard, ScoreSheet pScoreSheet) {
        Assert.assertNotNull(pPlayers);
        Assert.assertTrue(
            pPlayers.size() <= Game.MAX_PLAYERS);
        Player.assertAreValid(pPlayers);
        Assert.assertNotNull(pRacks);
        Assert.assertEquals(new HashSet(pPlayers), pRacks.keySet());
        Assert.assertTrue(
            CollectionUtil.containsOnlyInstancesOf(pRacks.values(), Rack.class));
        Assert.assertNotNull(pBoxLid);
        Assert.assertNotNull(pBoard);
        Assert.assertNotNull(pScoreSheet);

        mPlayers.addAll(pPlayers);
        mRacks.putAll(pRacks);
        mBoxLid = pBoxLid;
        mBoard = pBoard;
        mScoreSheet = pScoreSheet;

        Assert.assertFalse(getBoard().hasApprovedTiles() &&
            getScoreSheet().isIn(ScoreSheet.EMPTY));
        Assert.assertEquals(mPlayers, mScoreSheet.getPlayers());
    }

    // Methods: for subclass constructors

    protected static StickyMap createEmptyRacks(SetList pPlayers) {
        StickyMap racks = new HashStickyMap();
        for (Iterator players = pPlayers.iterator(); players.hasNext();) {
            racks.put(players.next(), new Rack());
        }
        return racks;
    }

    protected static StickyMap stringsToMap(String[] pPlayers, String[] pRacks) {
        StickyMap racks = new HashStickyMap();
        for (int i = 0; i < pPlayers.length; i++) {
            racks.put(pPlayers[i], new Rack(pRacks[i]));
        }
        return racks;
    }

    // Methods: queries

    public abstract String getName();

    public SetList getPlayers() {
        return mPlayers;
    }

    public Map getRacks() {
        return mRacks;
    }

    public Rack getRack(String pPlayer) {
        Player.assertIsValid(pPlayer);
        Assert.assertTrue(mRacks.containsKey(pPlayer));
        return (Rack) mRacks.get(pPlayer);
    }

    public BoxLid getBoxLid() {
        return mBoxLid;
    }

    public final Board getBoard() {
        return mBoard;
    }

    public final ScoreSheet getScoreSheet() {
        return mScoreSheet;
    }

    // Methods: queries for subclasses

    protected StateContext getContext() {
        return mContext;
    }

    protected int getPlayerCount() {
        return mPlayers.size();
    }

    protected boolean isPlaying(String pPlayer) {
        Player.assertIsValid(pPlayer);
        return mPlayers.contains(pPlayer);
    }

    // Methods: commands

    public void setContext(StateContext pContext) {
        Assert.assertNotNull(pContext);
        mContext = pContext;
    }

    protected void setBoxLid(BoxLid pBoxLid) {
        Assert.assertNotNull(pBoxLid);
        mBoxLid = pBoxLid;
    }

    public boolean canExecute(Request pRequest) {
        Assert.assertNotNull(pRequest);
        try {
            Object result = invoke("canExecute", pRequest);
            return ((Boolean) result).booleanValue();
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public void execute(Request pRequest) {
        Assert.assertNotNull(pRequest);
        Assert.assertTrue(canExecute(pRequest));
        String methodName = "execute";
        try {
            getContext().send((Event) invoke(methodName, pRequest));
        } catch (NoSuchMethodException e) {
            throw new ChainedRuntimeException("Couldn't find " + methodName +
                "(" + pRequest.getClass().getName() + ")", e);
        }
    }

    private Object invoke(String pMethodName, Request pRequest)
        throws NoSuchMethodException {
        Command command = pRequest.getCommand();
        Class commandClass = command.getClass();
        try {
            Method method = getClass().getMethod(pMethodName,
                new Class[] { String.class, commandClass });
            Object result = method.invoke(this,
                new Object[] { pRequest.getPlayer(), command });
            Assert.assertNotNull(result);
            return result;
        } catch (IllegalAccessException e) {
            throw new ChainedRuntimeException("Couldn't access " + pMethodName +
                "(" + commandClass.getName() + ")", e);
        } catch (InvocationTargetException e) {
            throw new ChainedRuntimeException("Couldn't invoke " + pMethodName +
                "(" + commandClass.getName() + ")", e);
        }
    }

    public boolean canExecute(String pPlayer, ChatCommand pCommand) {
        Player.assertIsValid(pPlayer);
        Assert.assertNotNull(pCommand);
        return true;
    }

    public Event execute(String pPlayer, ChatCommand pCommand) {
        Player.assertIsValid(pPlayer);
        Assert.assertNotNull(pCommand);
        return new ChatEvent(getContext().getGame(),
            new Request(pPlayer, pCommand));
    }

    // Methods: commands for subclasses

    protected void assertPlayerCountAfterStarting() {
        int playerCount = getPlayerCount();
        Assert.assertTrue(2 <= playerCount);
        Assert.assertTrue(playerCount <= Game.MAX_PLAYERS);
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        if (pOther == null || ! pOther.getClass().equals(getClass())) {
            return false;
        }
        StateImpl other = (StateImpl) pOther;
        return other.mPlayers.equals(mPlayers) && other.mRacks.equals(mRacks) &&
            other.mBoxLid.equals(mBoxLid) && other.mBoard.equals(mBoard) &&
            other.mScoreSheet.equals(mScoreSheet);
    }

    public int hashCode() {
        return 81 * mPlayers.hashCode() + 27 * mRacks.hashCode() +
            9 * mBoxLid.hashCode() + 3 * mBoard.hashCode() +
            mScoreSheet.hashCode();
    }

}
