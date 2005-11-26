package org.schweisguth.xttest.common.domain;

import java.util.Arrays;
import junit.framework.TestCase;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xt.common.util.contract.AssertionFailedError;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class RackTest extends TestCase {
    public void testGetTileCount() {
        assertEquals(5, new Rack("--AAAAA").getTileCount());
    }

    public void testIsEmpty() {
        assertFalse(new Rack("--AAAAA").isEmpty());
    }

    public void testAddTileIntOK0() {
        Rack rack = new Rack();
        rack.add(Tile.get('A'), 0);
        assertEquals(new Rack("A"), rack);
    }

    public void testAddTileIntOK1() {
        Rack rack = new Rack();
        rack.add(Tile.get('A'), 1);
        assertEquals(new Rack("-A"), rack);
    }

    public void testAddTileIntTaken() {
        Rack rack = new Rack("A");
        try {
            rack.add(Tile.get('A'), 0);
            fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testAddList() {
        Rack rack = new Rack("N--N--N");
        rack.add(Arrays.asList(new Tile[]
            { Tile.get('A'), Tile.get('B'), Tile.get('C'), Tile.get('D') }));
        assertEquals(new Rack("NABNCDN"), rack);
    }

    public void testAddListFull() {
        Rack rack = new Rack("NNNNNNN");
        try {
            rack.add(Arrays.asList(new Tile[] { Tile.get('Q') }));
            fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testCanMoveTrueToEmpty() {
        assertTrue(new Rack("A").canMove(0, 1));
    }

    public void testCanMoveTrueSwap() {
        assertTrue(new Rack("AA").canMove(0, 1));
    }

    public void testCanMoveFalseBadSource() {
        assertFalse(new Rack().canMove(0, 1));
    }

    public void testCanMoveFalseBadDestinationSelf() {
        assertFalse(new Rack("A").canMove(0, 0));
    }

    public void testMoveToEmpty() {
        Rack rack = new Rack("A");
        rack.move(0, 1);
        assertEquals(new Rack("-A"), rack);
    }

    public void testMoveSwap() {
        Rack rack = new Rack("AB");
        rack.move(0, 1);
        assertEquals(new Rack("BA"), rack);
    }

    public void testMoveBad() {
        try {
            new Rack().move(0, 1);
            fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testRemoveTile0() {
        Rack rack = new Rack();
        try {
            rack.remove(0);
            fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testRemoveTile1() {
        Rack rack = new Rack("A");
        rack.remove(0);
        assertEquals(new Rack(), rack);
    }

    public void testRemoveTile3() {
        Rack rack = new Rack("ABC");
        rack.remove(1);
        assertEquals(new Rack("A-C"), rack);
    }

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new Rack());
        tester.addOther(new Rack("B"));
        tester.setExpectedString("[A, null, null, null, null, null, null]");
        tester.doAssert(new Rack("A"));
    }

}
