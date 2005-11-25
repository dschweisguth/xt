package org.schweisguth.xttest.common.domain;

import junit.framework.TestCase;
import org.schweisguth.xt.common.domain.Axis;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xt.common.domain.Transfer;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.util.contract.AssertionFailedError;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class TransferSetTest extends TestCase {
    public void testCreateIntArrayIntIntAxisX() {
        TransferSet expected = new TransferSet();
        final int rackPosition0 = 0;
        expected.add(rackPosition0, 0, 0);
        final int rackPosition1 = 1;
        expected.add(rackPosition1, 1, 0);
        assertEquals(expected, new TransferSet(
            new int[] { rackPosition0, rackPosition1 }, 0, 0, Axis.X));
    }

    public void testCreateIntArrayIntIntAxisY() {
        TransferSet expected = new TransferSet();
        final int rackPosition0 = 0;
        expected.add(rackPosition0, 0, 0);
        final int rackPosition1 = 1;
        expected.add(rackPosition1, 0, 1);
        assertEquals(expected, new TransferSet(
            new int[] { rackPosition0, rackPosition1 }, 0, 0, Axis.Y));
    }

    public void testAddDuplicateRackPosition() {
        final int rackPosition = 0;
        TransferSet transferSet = new TransferSet(rackPosition, 0, 0);
        try {
            transferSet.add(rackPosition, 1, 0);
            fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testAddDuplicateBoardPosition() {
        final Position boardPosition = new Position(0, 0);
        TransferSet transferSet = new TransferSet(0, boardPosition);
        try {
            transferSet.add(1, boardPosition);
            fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testMoveIntEmpty() {
        final Position boardPosition = new Position(0, 0);
        final int source = 0;
        TransferSet transferSet = new TransferSet(source, boardPosition);
        final int destination = 1;
        transferSet.move(source, destination);
        assertEquals(new TransferSet(destination, boardPosition), transferSet);
    }

    public void testMoveIntSwap() {
        int source = 0;
        int destination = 1;
        TransferSet transferSet = new TransferSet();
        transferSet.add(source, new Position(0, 0));
        transferSet.add(destination, new Position(1, 0));
        transferSet.move(source, destination);
        TransferSet expected = new TransferSet();
        expected.add(source, new Position(1, 0));
        expected.add(destination, new Position(0, 0));
        assertEquals(expected, transferSet);
    }

    public void testMoveIntBadSource() {
        TransferSet transferSet = new TransferSet(0, 0, 0);
        try {
            transferSet.move(1, 2);
            fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testMoveIntBadDestinationSelf() {
        int source = 0;
        int destination = 1;
        TransferSet transferSet = new TransferSet();
        transferSet.add(source, new Position(0, 0));
        transferSet.add(destination, new Position(1, 0));
        try {
            transferSet.move(source, source);
            fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testMovePositionEmpty() {
        Position source = new Position(0, 0);
        Position destination = new Position(1, 0);
        final int rackPosition = 0;
        TransferSet transferSet = new TransferSet(rackPosition, source);
        transferSet.move(source, destination);
        assertEquals(new TransferSet(rackPosition, destination), transferSet);
    }

    public void testMovePositionSwap() {
        Position source = new Position(0, 0);
        Position destination = new Position(1, 0);
        TransferSet transferSet = new TransferSet();
        transferSet.add(0, source);
        transferSet.add(1, destination);
        transferSet.move(source, destination);
        TransferSet expected = new TransferSet();
        expected.add(0, destination);
        expected.add(1, source);
    }

    public void testMovePositionBadSource() {
        TransferSet transferSet = new TransferSet(0, 0, 0);
        try {
            transferSet.move(new Position(1, 0), new Position(2, 0));
            fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testMovePositionBadDestinationSelf() {
        Position source = new Position(0, 0);
        Position destination = new Position(1, 0);
        TransferSet transferSet = new TransferSet();
        transferSet.add(0, source);
        transferSet.add(1, destination);
        try {
            transferSet.move(source, source);
            fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testTakeBack() {
        TransferSet transferSet = new TransferSet(0, 0, 0);
        transferSet.takeBack(new Transfer(0, 0, 0));
        assertEquals(new TransferSet(), transferSet);
    }

    public void testTakeBackReplace() {
        TransferSet transferSet = new TransferSet();
        transferSet.add(new Transfer(0, 0, 0));
        transferSet.add(new Transfer(1, 1, 0));
        transferSet.takeBack(new Transfer(0, 1, 0));
        assertEquals(new TransferSet(1, 0, 0), transferSet);
    }

    public void testTakeBackBad() {
        TransferSet transferSet = new TransferSet(0, 0, 0);
        try {
            transferSet.takeBack(new Transfer(0, 1, 0));
            fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testValueObjectBehavior() throws Exception {
        new ValueObjectTester().doAssert(new TransferSet(0, 0, 0));
    }

}
