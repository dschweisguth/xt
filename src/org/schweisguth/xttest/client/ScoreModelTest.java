package org.schweisguth.xttest.client;

import org.schweisguth.xt.client.score.ScoreModel;
import org.schweisguth.xt.common.domain.ScoreSheet;
import org.schweisguth.xttest.testutil.BaseTest;

public class ScoreModelTest extends BaseTest {
    public void testSetScoreSheet() {
        ScoreModel model = new ScoreModel();
        ScoreSheet scoreSheet = new ScoreSheet(TWO_PLAYERS);
        scoreSheet.incrementScore(1);
        scoreSheet.incrementScore(2);
        model.setScoreSheet(scoreSheet);

        assertEquals(2, model.getColumnCount());
        assertEquals(1, model.getRowCount());
        assertEquals("player1", model.getColumnName(0));
        assertEquals("player2", model.getColumnName(1));
        assertEquals(new Integer(1), model.getValueAt(0, 0));
        assertEquals(new Integer(2), model.getValueAt(0, 1));
    }
}
