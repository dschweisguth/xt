package org.schweisguth.xttest.common.domain;

import org.schweisguth.xt.common.domain.BoxLid;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xttest.testutil.BaseTest;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class BoxLidTest extends BaseTest {
    public void testCreateString0() {
        assertTrue(new BoxLid("").isEmpty());
    }

    public void testCreateString1() {
        BoxLid boxLid = new BoxLid("A");
        assertEquals(1, boxLid.getTileCount());
        assertEquals(Tile.get('A'), boxLid.draw());
    }

    public void testCreateString2() {
        BoxLid boxLid = new BoxLid("AA");
        assertEquals(2, boxLid.getTileCount());
        assertEquals(Tile.get('A'), boxLid.draw());
        assertEquals(Tile.get('A'), boxLid.draw());
    }

    public void testEqualsTrueNew() {
        assertEqualsTrueFull(new BoxLid());
    }

    public void testEqualsTrueInitialized() {
        BoxLid boxLid = new BoxLid();
        boxLid.initialize();
        assertEqualsTrueFull(boxLid);
    }

    public void testEqualsTrueUsedInitialized() {
        BoxLid boxLid = new BoxLid();
        boxLid.draw();
        boxLid.initialize();
        assertEqualsTrueFull(boxLid);
    }

    public void testEqualsTrueFakeInitialized() {
        BoxLid boxLid = new BoxLid("");
        boxLid.initialize();
        assertEqualsTrueFull(boxLid);
    }

    public void testEqualsTrueFakeUsedInitialized() {
        BoxLid boxLid = new BoxLid("A");
        boxLid.draw();
        boxLid.initialize();
        assertEqualsTrueFull(boxLid);
    }

    private static void assertEqualsTrueFull(BoxLid pFullBoxLid) {
        assertTrue(pFullBoxLid.equals(pFullBoxLid));

        BoxLid newBoxLid = new BoxLid();
        assertTrue(newBoxLid.equals(pFullBoxLid));
        assertTrue(pFullBoxLid.equals(newBoxLid));
        assertEquals(newBoxLid.getTileCount(), pFullBoxLid.getTileCount());

        BoxLid initializedBoxLid = new BoxLid();
        initializedBoxLid.initialize();
        assertTrue(initializedBoxLid.equals(pFullBoxLid));
        assertTrue(pFullBoxLid.equals(initializedBoxLid));
        assertEquals(initializedBoxLid.getTileCount(), pFullBoxLid.getTileCount());

        BoxLid usedInitializedBoxLid = new BoxLid();
        usedInitializedBoxLid.initialize();
        assertTrue(usedInitializedBoxLid.equals(pFullBoxLid));
        assertTrue(pFullBoxLid.equals(usedInitializedBoxLid));
        assertEquals(usedInitializedBoxLid.getTileCount(), pFullBoxLid.getTileCount());

        BoxLid fakeInitializedBoxLid = new BoxLid("");
        fakeInitializedBoxLid.initialize();
        assertTrue(fakeInitializedBoxLid.equals(pFullBoxLid));
        assertTrue(pFullBoxLid.equals(fakeInitializedBoxLid));
        assertEquals(fakeInitializedBoxLid.getTileCount(), pFullBoxLid.getTileCount());

        BoxLid fakeUsedInitializedBoxLid = new BoxLid("A");
        fakeUsedInitializedBoxLid.draw();
        fakeUsedInitializedBoxLid.initialize();
        assertTrue(fakeUsedInitializedBoxLid.equals(pFullBoxLid));
        assertTrue(pFullBoxLid.equals(fakeUsedInitializedBoxLid));
        assertEquals(fakeUsedInitializedBoxLid.getTileCount(),
            pFullBoxLid.getTileCount());

    }

    public void testEqualsTrueSameUsed() {
        BoxLid boxLid = new BoxLid();
        boxLid.draw();
        assertTrue(boxLid.equals(boxLid));
    }

    public void testEqualsTrueDifferentUsedUsed() {
        BoxLid one = new BoxLid();
        one.draw(Tile.get('A'));
        BoxLid other = new BoxLid();
        other.draw(Tile.get('A'));
        assertTrue(one.equals(other));
    }

    public void testEqualsTrueDifferentFakeFake1() {
        assertTrue(new BoxLid("A").equals(new BoxLid("A")));
    }

    public void testEqualsTrueDifferentFakeFake2SameOrder() {
        assertTrue(new BoxLid("AB").equals(new BoxLid("BA")));
    }

    public void testEqualsTrueDifferentFakeFake2DifferentOrder() {
        assertTrue(new BoxLid("AB").equals(new BoxLid("BA")));
    }

    public void testEqualsFalseNewNull() {
        assertFalse(new BoxLid().equals(null));
    }

    public void testEqualsFalseFullWrongClass() {
        assertEqualsFalseFull("");
    }

    public void testEqualsFalseFullUsed() {
        BoxLid boxLid = new BoxLid();
        boxLid.draw();
        assertEqualsFalseFull(boxLid);
    }

    public void testEqualsFalseFullFakeEmpty() {
        assertEqualsFalseFull(new BoxLid(""));
    }

    public void testEqualsFalseFullFakeNotEmpty() {
        assertEqualsFalseFull(new BoxLid("A"));
    }

    private static void assertEqualsFalseFull(Object pNotFullBoxLid) {
        BoxLid newBoxLid = new BoxLid();
        assertFalse(newBoxLid.equals(pNotFullBoxLid));
        assertFalse(pNotFullBoxLid.equals(newBoxLid));

        BoxLid initializedBoxLid = new BoxLid();
        initializedBoxLid.initialize();
        assertFalse(initializedBoxLid.equals(pNotFullBoxLid));
        assertFalse(pNotFullBoxLid.equals(initializedBoxLid));

        BoxLid usedInitializedBoxLid = new BoxLid();
        usedInitializedBoxLid.initialize();
        assertFalse(usedInitializedBoxLid.equals(pNotFullBoxLid));
        assertFalse(pNotFullBoxLid.equals(usedInitializedBoxLid));

        BoxLid fakeInitializedBoxLid = new BoxLid("");
        fakeInitializedBoxLid.initialize();
        assertFalse(fakeInitializedBoxLid.equals(pNotFullBoxLid));
        assertFalse(pNotFullBoxLid.equals(fakeInitializedBoxLid));

        BoxLid fakeUsedInitializedBoxLid = new BoxLid("A");
        fakeUsedInitializedBoxLid.draw();
        fakeUsedInitializedBoxLid.initialize();
        assertFalse(fakeUsedInitializedBoxLid.equals(pNotFullBoxLid));
        assertFalse(pNotFullBoxLid.equals(fakeUsedInitializedBoxLid));

    }

    public void testValueObjectBehavior() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new BoxLid());
        tester.addOther(new BoxLid(""));
        tester.addOther(new BoxLid("B"));
        tester.setExpectedString("[A]");
        tester.doAssert(new BoxLid("A"));
    }

}
