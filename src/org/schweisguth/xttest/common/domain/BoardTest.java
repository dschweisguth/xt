package org.schweisguth.xttest.common.domain;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.schweisguth.xt.common.domain.Board;
import org.schweisguth.xt.common.domain.Position;
import org.schweisguth.xt.common.domain.Rack;
import org.schweisguth.xt.common.domain.Tile;
import org.schweisguth.xt.common.domain.TransferSet;
import org.schweisguth.xt.common.util.contract.AssertionFailedError;
import org.schweisguth.xttest.testutil.ValueObjectTester;

public class BoardTest extends TestCase {
    public void testCanPlaceTrue() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        assertTrue(board.canPlace(8, 7));
    }

    public void testCanPlaceTrueSecondTileOutOfLine() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        assertTrue(board.canPlace(8, 8));
    }

    public void testCanPlaceFalseSamePosition() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        assertFalse(board.canPlace(7, 7));
    }

    public void testPlaceBad() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        try {
            board.place(Tile.get('A'), 7, 7);
            Assert.fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testPlaceRack() {
        Board board = new Board();
        board.place(new Rack("A"), new TransferSet(0, 0, 0));
        Board expectedBoard = new Board();
        expectedBoard.place(Tile.get('A'), 0, 0);
        assertEquals(expectedBoard, board);
    }

    public void testPlaceRackBad() {
        Board board = new Board();
        try {
            board.place(new Rack(""), new TransferSet(0, 0, 0));
            fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testCanMoveTrueEmptyDestination() {
        final Position source = new Position(0, 0);
        Board board = new Board();
        board.place(Tile.get('A'), source);
        assertTrue(board.canMove(source, new Position(1, 0)));
    }

    public void testCanMoveTrueSwap() {
        final Position source = new Position(0, 0);
        final Position destination = new Position(1, 0);
        Board board = new Board();
        board.place(Tile.get('A'), source);
        board.place(Tile.get('A'), destination);
        assertTrue(board.canMove(source, destination));
    }

    public void testCanMoveFalseEmptySource() {
        assertFalse(new Board().canMove(new Position(0, 0), new Position(1, 0)));
    }

    public void testCanMoveFalseCommittedSource() {
        final Position source = new Position(7, 7);
        Board board = new Board();
        board.place(Tile.get('A'), source);
        board.place(Tile.get('A'), new Position(8, 7));
        board.approve();
        assertFalse(board.canMove(source, new Position(0, 0)));
    }

    public void testCanMoveFalseBadDestinationSame() {
        final Position source = new Position(0, 0);
        Board board = new Board();
        board.place(Tile.get('A'), source);
        assertFalse(board.canMove(source, source));
    }

    public void testCanMoveFalseBadDestinationOtherApproved() {
        final Position source = new Position(9, 7);
        final Position destination = new Position(7, 7);
        Board board = new Board();
        board.place(Tile.get('A'), destination);
        board.place(Tile.get('A'), new Position(8, 7));
        board.approve();
        board.place(Tile.get('A'), source);
        assertFalse(board.canMove(source, destination));
    }

    public void testMoveToEmpty() {
        final Tile tile = Tile.get('A');
        final Position source = new Position(0, 0);
        final Position destination = new Position(1, 0);
        Board board = new Board();
        board.place(tile, source);
        board.move(source, destination);
        Board expectedBoard = new Board();
        expectedBoard.place(tile, destination);
        assertEquals(expectedBoard, board);
    }

    public void testMoveSwap() {
        final Tile tile1 = Tile.get('A');
        final Tile tile2 = Tile.get('B');
        final Position source = new Position(0, 0);
        final Position destination = new Position(1, 0);
        Board board = new Board();
        board.place(tile1, source);
        board.place(tile2, destination);
        board.move(source, destination);
        Board expectedBoard = new Board();
        expectedBoard.place(tile1, destination);
        expectedBoard.place(tile2, source);
        assertEquals(expectedBoard, board);
    }

    public void testMoveBad() {
        final Position source = new Position(0, 0);
        Board board = new Board();
        board.place(Tile.get('A'), source);
        try {
            board.move(source, source);
            Assert.fail();
        } catch (AssertionFailedError e) {
        }
    }

    public void testCanFinishTrueFirstMove() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 8, 7);
        assertTrue(board.canFinish());
    }

    public void testCanFinishTrueFirstMoveVertical() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 7, 6);
        assertTrue(board.canFinish());
    }

    public void testCanFinishFalseOneTile() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        assertFalse(board.canFinish());
    }

    public void testCanFinishFalseOffCenter() {
        Board board = new Board();
        board.place(Tile.get('A'), 8, 7);
        board.place(Tile.get('A'), 9, 7);
        assertFalse(board.canFinish());
    }

    public void testCanFinishFalseOutOfLine() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 8, 8);
        assertFalse(board.canFinish());
    }

    public void testCanFinishFalseNonContiguous() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 9, 7);
        assertFalse(board.canFinish());
    }

    public void testCanFinishTrueSecondMoveInLine() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 8, 7);
        board.approve();
        board.place(Tile.get('A'), 9, 7);
        assertTrue(board.canFinish());
    }

    public void testCanFinishFalseSecondMoveInLineNotAdjacent() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 8, 7);
        board.approve();
        board.place(Tile.get('A'), 10, 7);
        assertFalse(board.canFinish());
    }

    public void testCanFinishTrueSecondMoveOutOfLine() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 8, 7);
        board.approve();
        board.place(Tile.get('A'), 7, 6);
        assertTrue(board.canFinish());
    }

    public void testCanFinishFalseSecondMoveOutOfLineNotAdjacent() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 8, 7);
        board.approve();
        board.place(Tile.get('A'), 9, 6);
        assertFalse(board.canFinish());
    }

    public void testHasApprovedTilesTrue() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 8, 7);
        board.approve();
        assertTrue(board.hasApprovedTiles());
    }

    public void testHasApprovedTilesFalse() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 8, 7);
        assertFalse(board.hasApprovedTiles());
    }

    public void testScoreFirstMove() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 8, 7);
        assertEquals(4, board.getScore());
    }

    public void testScoreFirstMoveRotated() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 7, 6);
        assertEquals(4, board.getScore());
    }

    public void testScoreSecondMoveInLine() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 8, 7);
        board.approve();
        board.place(Tile.get('E'), 9, 7);
        assertEquals(3, board.getScore());
    }

    public void testScoreSecondMoveInLineRotated() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 7, 6);
        board.approve();
        board.place(Tile.get('E'), 7, 5);
        assertEquals(3, board.getScore());
    }

    public void testScoreSecondMoveOutOfLine() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 8, 7);
        board.approve();
        board.place(Tile.get('E'), 7, 6);
        assertEquals(2, board.getScore());
    }

    public void testScoreSecondMoveOutOfLineRotated() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 7, 6);
        board.approve();
        board.place(Tile.get('E'), 8, 7);
        assertEquals(2, board.getScore());
    }

    public void testScoreSecondMoveTwoWords() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 8, 7);
        board.approve();
        board.place(Tile.get('E'), 9, 7);
        board.place(Tile.get('E'), 9, 6);
        assertEquals(5, board.getScore());
    }

    public void testScoreSecondMoveThreeWords() {
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        board.place(Tile.get('A'), 8, 7);
        board.approve();
        board.place(Tile.get('E'), 7, 6);
        board.place(Tile.get('E'), 8, 6);
        assertEquals(8, board.getScore());
    }

    public void testScoreTwoWordsOneMultiplied() {
        Board board = new Board();
        board.place(Tile.get('R'), 5, 7);
        board.place(Tile.get('I'), 6, 7);
        board.place(Tile.get('G'), 7, 7);
        board.approve();
        board.place(Tile.get('P'), 4, 7);
        board.place(Tile.get('R'), 4, 6);
        board.place(Tile.get('I'), 4, 5);
        board.place(Tile.get('G'), 4, 4);
        assertEquals(21, board.getScore());
    }

    public void testScoreTwoWordsTwoMultiplied() {
        Board board = new Board();
        board.place(Tile.get('E'), 7, 7);
        board.place(Tile.get('X'), 8, 7);
        board.place(Tile.get('E'), 9, 7);
        board.place(Tile.get('C'), 10, 7);
        board.place(Tile.get('U'), 11, 7);
        board.place(Tile.get('T'), 12, 7);
        board.place(Tile.get('E'), 13, 7);
        board.approve();
        board.place(Tile.get('D'), 14, 13);
        board.place(Tile.get('E'), 14, 12);
        board.place(Tile.get('V'), 14, 11);
        board.place(Tile.get('I'), 14, 10);
        board.place(Tile.get('O'), 14, 9);
        board.place(Tile.get('U'), 14, 8);
        board.place(Tile.get('S'), 14, 7);
        assertEquals(3 * 15 + 3 * 17, board.getScore());
    }

    public void testValueObjectBehavior1() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new Object());
        Board other = new Board();
        other.place(Tile.get('A'), 7, 7);
        tester.addOther(other);
        tester.doAssert(new Board());
    }

    public void testValueObjectBehavior2() throws Exception {
        ValueObjectTester tester = new ValueObjectTester();
        tester.addOther(new Object());
        Board other = new Board();
        other.place(Tile.get('B'), 7, 7);
        tester.addOther(other);
        Board board = new Board();
        board.place(Tile.get('A'), 7, 7);
        tester.doAssert(board);
    }

}
