package org.schweisguth.xttest.common.domain;

import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xt.common.util.collection.ArraySetList;
import org.schweisguth.xt.common.util.collection.SetList;
import org.schweisguth.xttest.testutil.BaseTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class ScoreSheetTest extends BaseTest {
    public void testGetCurrentPlayerIndex() {
        assertEquals(0, new ScoreSheet(TWO_PLAYERS).getCurrentPlayerIndex());
    }

    public void testGetCurrentPlayerIndex1() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        assertEquals(1, scores.getCurrentPlayerIndex());
    }

    public void testGetCurrentPlayerIndex2() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS, 1);
        scores.incrementScore(1);
        assertEquals(0, scores.getCurrentPlayerIndex());
    }

    public void testGetHighTotal() {
        assertHighTotal(0, new ScoreSheet(TWO_PLAYERS));
    }

    public void testGetHighTotal1() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        assertHighTotal(1, scores);
    }

    public void testGetHighTotal1Negative() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(-1);
        assertHighTotal(0, scores);
    }

    public void testGetHighTotal2() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS, 1);
        scores.incrementScore(1);
        assertHighTotal(1, scores);
    }

    public void testGetHighTotal12() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        scores.incrementScore(2);
        assertHighTotal(2, scores);
    }

    public void testGetHighTotal21() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS, 1);
        scores.incrementScore(1);
        scores.incrementScore(2);
        assertHighTotal(2, scores);
    }

    public void testGetHighTotal121() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        scores.incrementScore(2);
        scores.incrementScore(4);
        assertHighTotal(5, scores);
    }

    public void testGetHighTotal212() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS, 1);
        scores.incrementScore(1);
        scores.incrementScore(2);
        scores.incrementScore(4);
        assertHighTotal(5, scores);
    }

    private static void assertHighTotal(int pHighTotal, ScoreSheet pScores) {
        assertFalse(pScores.isIn(ScoreSheet.GAME_ENDED));
        assertEquals(pHighTotal, pScores.getHighTotal());
        pScores.endGame();
        assertEquals(pHighTotal, pScores.getHighTotal());
    }

    public void testGetWinners() {
        assertWinners(TWO_PLAYERS, new ScoreSheet(TWO_PLAYERS));
    }

    public void testGetWinners1() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        assertWinners(new String[] { "player1" }, scores);
    }

    public void testGetWinners1Negative() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(-1);
        assertWinners(new String[] { "player2" }, scores);
    }

    public void testGetWinners2() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS, 1);
        scores.incrementScore(1);
        assertWinners(new String[] { "player2" }, scores);
    }

    public void testGetWinners12() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        scores.incrementScore(2);
        assertWinners(new String[] { "player2" }, scores);
    }

    public void testGetWinners12Tie() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        scores.incrementScore(1);
        assertWinners(TWO_PLAYERS, scores);
    }

    public void testGetWinners21() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS, 1);
        scores.incrementScore(1);
        scores.incrementScore(2);
        assertWinners(new String[] { "player1" }, scores);
    }

    public void testGetWinners21Tie() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS, 1);
        scores.incrementScore(1);
        scores.incrementScore(1);
        assertWinners(TWO_PLAYERS, scores);
    }

    public void testGetWinners121() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        scores.incrementScore(2);
        scores.incrementScore(4);
        assertWinners(new String[] { "player1" }, scores);
    }

    public void testGetWinners121Tie() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(0);
        scores.incrementScore(1);
        scores.incrementScore(1);
        assertWinners(TWO_PLAYERS, scores);
    }

    public void testGetWinners212() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS, 1);
        scores.incrementScore(1);
        scores.incrementScore(2);
        scores.incrementScore(4);
        assertWinners(new String[] { "player2" }, scores);
    }

    public void testGetWinners212Tie() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS, 1);
        scores.incrementScore(0);
        scores.incrementScore(1);
        scores.incrementScore(1);
        assertWinners(TWO_PLAYERS, scores);
    }

    private static void assertWinners(String[] pWinners, ScoreSheet pScores) {
        SetList winners = new ArraySetList(pWinners);
        assertEquals(winners, pScores.getWinners());
        pScores.endGame();
        assertEquals(winners, pScores.getWinners());
    }

    public void testIncrementScore1() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        assertTrue(scores.isIn(ScoreSheet.PLAYING));
        assertEquals(1, scores.getRowCount());
        assertEquals(new Integer(1), scores.get(0, 0));
        assertEquals(null, scores.get(0, 1));
    }

    public void testIncrementScore2() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        scores.incrementScore(2);
        scores.incrementScore(4);
        assertTrue(scores.isIn(ScoreSheet.PLAYING));
        assertEquals(2, scores.getRowCount());
        assertEquals(new Integer(1), scores.get(0, 0));
        assertEquals(new Integer(2), scores.get(0, 1));
        assertEquals(new Integer(5), scores.get(1, 0));
        assertEquals(null, scores.get(1, 1));
    }

    public void testStartNewRow0() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.endGame();
        assertTrue(scores.isIn(ScoreSheet.GAME_ENDED));
        assertEquals(0, scores.getRowCount());
    }

    public void testStartNewRow1() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        scores.endGame();
        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(1);
        expectedScores.incrementScore(0);
        expectedScores.endGame();
        assertEquals(expectedScores, scores);
    }

    public void testStartNewRow2() {
        ScoreSheet scores = new ScoreSheet(TWO_PLAYERS);
        scores.incrementScore(1);
        scores.incrementScore(2);
        scores.incrementScore(4);
        scores.endGame();
        ScoreSheet expectedScores = new ScoreSheet(TWO_PLAYERS);
        expectedScores.incrementScore(1);
        expectedScores.incrementScore(2);
        expectedScores.incrementScore(4);
        expectedScores.incrementScore(0);
        expectedScores.endGame();
        assertEquals(expectedScores, scores);
    }

    public void testValueObjectBehavior() throws Exception {
        new ValueObjectTester().doAssert(new ScoreSheet(TWO_PLAYERS));
    }

}
