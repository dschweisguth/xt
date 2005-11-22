package org.schweisguth.xttest.common.domain;

import junit.framework.TestCase;
import org.schweisguth.xt.common.domain.Axis;
import org.schweisguth.xt.common.domain.Move;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xt.common.util.collection.HashStickySet;
import org.schweisguth.xt.common.util.collection.StickySet;
import org.schweisguth.xt.common.util.contract.AssertionFailedError;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class MoveTest extends TestCase {
    public void testCanFinishTrue0() {
        assertFalse(new Move().canFinish());
    }

    public void testCanFinishTrue1() {
        Move move = new Move();
        move.add(new Position(0, 0));
        assertTrue(move.canFinish());
    }

    public void testCanFinishTrue2X1() {
        Move move = new Move();
        move.add(new Position(0, 0));
        move.add(new Position(1, 0));
        assertTrue(move.canFinish());
    }

    public void testCanFinishTrue2X2() {
        Move move = new Move();
        move.add(new Position(0, 0));
        move.add(new Position(2, 0));
        assertTrue(move.canFinish());
    }

    public void testCanFinishTrue2Y1() {
        Move move = new Move();
        move.add(new Position(0, 0));
        move.add(new Position(0, 1));
        assertTrue(move.canFinish());
    }

    public void testCanFinishTrue2Y2() {
        Move move = new Move();
        move.add(new Position(0, 0));
        move.add(new Position(0, 2));
        assertTrue(move.canFinish());
    }

    public void testCanFinishFalse() {
        Move move = new Move();
        move.add(new Position(0, 0));
        move.add(new Position(1, 1));
        assertFalse(move.canFinish());
    }

    public void testParallels1() {
        Move move = new Move();
        move.add(new Position(0, 0));
        try {
            move.getParallels();
        } catch (AssertionFailedError e) {
            return;
        }
        fail();
    }

    public void testParallelsX() {
        Move move = new Move();
        move.add(new Position(0, 0));
        move.add(new Position(1, 0));
        assertEquals(Axis.X, move.getParallels());
    }

    public void testParallelsY() {
        Move move = new Move();
        move.add(new Position(0, 0));
        move.add(new Position(0, 1));
        assertEquals(Axis.Y, move.getParallels());
    }

    public void testGetEnvelope0() {
        try {
            new Move().getEnvelope();
        } catch (AssertionFailedError e) {
            return;
        }
        fail();
    }

    public void testGetEnvelope1() {
        Position position = new Position(0, 0);
        Move move = new Move();
        move.add(position);
        StickySet expected = new HashStickySet();
        expected.add(position);
        assertEquals(expected, move.getEnvelope());
    }

    public void testGetEnvelope2AdjacentX() {
        Position position1 = new Position(0, 0);
        Position position2 = new Position(1, 0);
        Move move = new Move();
        move.add(position1);
        move.add(position2);
        StickySet expected = new HashStickySet();
        expected.add(position1);
        expected.add(position2);
        assertEquals(expected, move.getEnvelope());
    }

    public void testGetEnvelope2NotAdjacentX() {
        Position position1 = new Position(0, 0);
        Position position2 = new Position(2, 0);
        Move move = new Move();
        move.add(position1);
        move.add(position2);
        StickySet expected = new HashStickySet();
        expected.add(position1);
        expected.add(new Position(1, 0));
        expected.add(position2);
        assertEquals(expected, move.getEnvelope());
    }

    public void testGetEnvelope2AdjacentY() {
        Position position1 = new Position(0, 0);
        Position position2 = new Position(0, 1);
        Move move = new Move();
        move.add(position1);
        move.add(position2);
        StickySet expected = new HashStickySet();
        expected.add(position1);
        expected.add(position2);
        assertEquals(expected, move.getEnvelope());
    }

    public void testGetEnvelope2NotAdjacentY() {
        Position position1 = new Position(0, 0);
        Position position2 = new Position(0, 2);
        Move move = new Move();
        move.add(position1);
        move.add(position2);
        StickySet expected = new HashStickySet();
        expected.add(position1);
        expected.add(new Position(0, 1));
        expected.add(position2);
        assertEquals(expected, move.getEnvelope());
    }

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new Move());
        Move other = new Move();
        other.add(new Position(1, 0));
        tester.addOther(other);
        Move move = new Move();
        move.add(new Position(0, 0));
        assertFalse(move.equals(other));
        tester.doAssert(move);
    }

}
