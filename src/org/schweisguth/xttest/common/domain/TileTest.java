package org.schweisguth.xttest.common.domain;

import junit.framework.TestCase;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class TileTest extends TestCase {
    public void testCompareToAA() {
        assertTrue(Tile.get('A').compareTo(Tile.get('A')) == 0);
    }

    public void testCompareToAB() {
        assertTrue(Tile.get('A').compareTo(Tile.get('B')) < 0);
    }

    public void testCompareToBA() {
        assertTrue(Tile.get('B').compareTo(Tile.get('A')) > 0);
    }

    public void testCompareToABlank() {
        assertTrue(Tile.get('A').compareTo(Tile.get(' ')) < 0);
    }

    public void testCompareToBlankA() {
        assertTrue(Tile.get(' ').compareTo(Tile.get('A')) > 0);
    }

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(Tile.get('B'));
        tester.setExpectedString("A");
        tester.doAssert(Tile.get('A'));
    }

}
