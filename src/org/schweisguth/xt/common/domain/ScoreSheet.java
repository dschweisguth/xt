package org.schweisguth.xt.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.collection.SetList;
import org.schweisguth.xt.common.util.contract.Assert;

public class ScoreSheet implements Serializable {
    private static final long serialVersionUID = 6744255292607906334L;

    // Constants
    public static final String EMPTY = "EMPTY";
    public static final String PLAYING = "PLAYING";
    public static final String GAME_ENDED = "OVER";

    // Fields
    private String mState = EMPTY;
    private final SetList mColumns = new ArraySetList();
    private final List mRows = new ArrayList();
    private int mCurrentPlayerIndex = 0;

    // Constructors

    public ScoreSheet(SetList pPlayers) {
        Assert.assertNotNull(pPlayers);
        Assert.assertTrue(
            CollectionUtil.containsOnlyInstancesOf(pPlayers, String.class));

        mColumns.addAll(pPlayers);

    }

    public ScoreSheet(SetList pPlayers, int pCurrentPlayerIndex) {
        this(pPlayers);
        setCurrentPlayerIndex(pCurrentPlayerIndex);
    }

    public ScoreSheet(String[] pPlayers) {
        this(new ArraySetList(pPlayers));
    }

    public ScoreSheet(String[] pPlayers, int pCurrentPlayerIndex) {
        this(new ArraySetList(pPlayers), pCurrentPlayerIndex);
    }

    // Methods: queries

    public boolean isIn(String pState) {
        return mState.equals(pState);
    }

    public List getPlayers() {
        return mColumns;
    }

    public int getPlayerCount() {
        return mColumns.size();
    }

    public String getPlayer(int pColumn) {
        Assert.assertTrue(0 <= pColumn);
        Assert.assertTrue(pColumn < mColumns.size());

        return (String) mColumns.get(pColumn);

    }

    public int getRowCount() {
        return mRows.size();
    }

    public Object get(int pRow, int pColumn) {
        Assert.assertTrue(0 <= pRow);
        Assert.assertTrue(pRow < mRows.size());
        Assert.assertTrue(0 <= pColumn);
        Assert.assertTrue(pColumn < mColumns.size());

        return ((List) mRows.get(pRow)).get(pColumn);

    }

    public int getInt(int pRow, String pPlayer) {
        Assert.assertTrue(0 <= pRow);
        Assert.assertTrue(pRow < mRows.size());
        Assert.assertTrue(mColumns.contains(pPlayer));
        Object score = get(pRow, mColumns.indexOf(pPlayer));
        Assert.assertNotNull(score);

        return ((Integer) score).intValue();

    }

    public int getCurrentPlayerIndex() {
        return mCurrentPlayerIndex;
    }

    public int getHighTotal() {
        Assert.assertTrue(getPlayerCount() > 0);
        Iterator scores = getScores().values().iterator();
        int highTotal = ((Integer) scores.next()).intValue();
        while (scores.hasNext()) {
            int score = ((Integer) scores.next()).intValue();
            highTotal = Math.max(highTotal, score);
        }
        return highTotal;
    }

    public SetList getWinners() {
        SetList winners = new ArraySetList();
        int highTotal = getHighTotal();
        Iterator entries = getScores().entrySet().iterator();
        while (entries.hasNext()) {
            Entry entry = (Entry) entries.next();
            if (((Integer) entry.getValue()).intValue() == highTotal) {
                winners.add(entry.getKey());
            }
        }
        return winners;
    }

    // Methods: commands

    public void addPlayer(String pPlayer) {
        Assert.assertEquals(EMPTY, mState);
        Player.assertIsValid(pPlayer);
        mColumns.add(pPlayer);
    }

    public final void setCurrentPlayerIndex(int pCurrentPlayerIndex) {
        Assert.assertEquals(EMPTY, mState);
        mCurrentPlayerIndex = pCurrentPlayerIndex;
    }

    public void incrementScore(int pScore) {
        if (mState.equals(EMPTY)) {
            mState = PLAYING;
        }
        if (mRows.isEmpty() || getLastRow().get(mCurrentPlayerIndex) != null) {
            mRows.add(new ArrayList(Collections.nCopies(mColumns.size(), null)));
        }
        int total = pScore;
        if (mRows.size() > 1) {
            List nextToLastRow = (List) mRows.get(mRows.size() - 2);
            total +=
                ((Integer) nextToLastRow.get(mCurrentPlayerIndex)).intValue();
        }
        getLastRow().set(mCurrentPlayerIndex, new Integer(total));
        mCurrentPlayerIndex++;
        mCurrentPlayerIndex %= getPlayerCount();
    }

    public void endGame() {
        Assert.assertFalse(GAME_ENDED.equals(mState));

        mState = GAME_ENDED;
        mCurrentPlayerIndex = 0;
        if (! mRows.isEmpty()) {
            List lastRow = getLastRow();
            if (mRows.size() == 1) {
                for (int column = 0; column < lastRow.size(); column++) {
                    if (lastRow.get(column) == null) {
                        lastRow.set(column, new Integer(0));
                    }
                }
            } else {
                List nextToLastRow = (List) mRows.get(mRows.size() - 2);
                for (int column = 0; column < lastRow.size(); column++) {
                    if (lastRow.get(column) == null) {
                        lastRow.set(column, nextToLastRow.get(column));
                    }
                }
            }
        }

    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        if (pOther == null || ! pOther.getClass().equals(getClass())) {
            return false;
        }
        ScoreSheet other = (ScoreSheet) pOther;
        return other.mState.equals(mState) &&
            other.mColumns.equals(mColumns) && other.mRows.equals(mRows);
    }

    public int hashCode() {
        return 9 * mState.hashCode() +
            3 * mColumns.hashCode() + mColumns.hashCode();
    }

    public String toString() {
        return "ScoreSheet(" + mState + ", " + mColumns + ", " + mRows + ")";
    }

    // Methods: helper

    private List getLastRow() {
        Assert.assertFalse(mRows.isEmpty());
        return (List) mRows.get(mRows.size() - 1);
    }

    private SortedMap getScores() {
        SortedMap scores = new TreeMap();
        int rowCount = getRowCount();
        for (int column = 0; column < getPlayerCount(); column++) {
            int score;
            if (rowCount == 0) {
                score = 0;
            } else {
                Object scoreInLastRow = get(rowCount - 1, column);
                if (scoreInLastRow != null) {
                    score = ((Integer) scoreInLastRow).intValue();
                } else if (rowCount > 1) {
                    Object scoreInNextToLastRow = get(rowCount - 2, column);
                    score = ((Integer) scoreInNextToLastRow).intValue();
                } else {
                    score = 0;
                }
            }
            scores.put(getPlayer(column), new Integer(score));
        }
        return scores;
    }

}
