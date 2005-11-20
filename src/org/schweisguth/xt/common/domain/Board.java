package org.schweisguth.xt.common.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import org.schweisguth.xt.common.util.collection.CollectionUtil;
import org.schweisguth.xt.common.util.collection.HashStickyMap;
import org.schweisguth.xt.common.util.collection.HashStickySet;
import org.schweisguth.xt.common.util.collection.StickyMap;
import org.schweisguth.xt.common.util.collection.StickySet;
import org.schweisguth.xt.common.util.contract.Assert;

public class Board implements Serializable {
    private static final long serialVersionUID = 7545892179562355356L;

    // Constants
    private static final StickyMap MODIFIERS = new HashStickyMap();

    static {
        Object[][] modifiers =
            {
                { new Position(3, 0), Modifier.DOUBLE_LETTER },
                { new Position(11, 0), Modifier.DOUBLE_LETTER },
                { new Position(6, 2), Modifier.DOUBLE_LETTER },
                { new Position(8, 2), Modifier.DOUBLE_LETTER },
                { new Position(0, 3), Modifier.DOUBLE_LETTER },
                { new Position(7, 3), Modifier.DOUBLE_LETTER },
                { new Position(14, 3), Modifier.DOUBLE_LETTER },
                { new Position(2, 6), Modifier.DOUBLE_LETTER },
                { new Position(6, 6), Modifier.DOUBLE_LETTER },
                { new Position(8, 6), Modifier.DOUBLE_LETTER },
                { new Position(12, 6), Modifier.DOUBLE_LETTER },
                { new Position(3, 7), Modifier.DOUBLE_LETTER },
                { new Position(11, 7), Modifier.DOUBLE_LETTER },
                { new Position(2, 8), Modifier.DOUBLE_LETTER },
                { new Position(6, 8), Modifier.DOUBLE_LETTER },
                { new Position(8, 8), Modifier.DOUBLE_LETTER },
                { new Position(12, 8), Modifier.DOUBLE_LETTER },
                { new Position(0, 11), Modifier.DOUBLE_LETTER },
                { new Position(7, 11), Modifier.DOUBLE_LETTER },
                { new Position(14, 11), Modifier.DOUBLE_LETTER },
                { new Position(6, 12), Modifier.DOUBLE_LETTER },
                { new Position(8, 12), Modifier.DOUBLE_LETTER },
                { new Position(3, 14), Modifier.DOUBLE_LETTER },
                { new Position(11, 14), Modifier.DOUBLE_LETTER },

                { new Position(5, 1), Modifier.TRIPLE_LETTER },
                { new Position(9, 1), Modifier.TRIPLE_LETTER },
                { new Position(1, 5), Modifier.TRIPLE_LETTER },
                { new Position(5, 5), Modifier.TRIPLE_LETTER },
                { new Position(9, 5), Modifier.TRIPLE_LETTER },
                { new Position(13, 5), Modifier.TRIPLE_LETTER },
                { new Position(1, 9), Modifier.TRIPLE_LETTER },
                { new Position(5, 9), Modifier.TRIPLE_LETTER },
                { new Position(9, 9), Modifier.TRIPLE_LETTER },
                { new Position(13, 9), Modifier.TRIPLE_LETTER },
                { new Position(5, 13), Modifier.TRIPLE_LETTER },
                { new Position(9, 13), Modifier.TRIPLE_LETTER },

                { new Position(1, 1), Modifier.DOUBLE_WORD },
                { new Position(1, 13), Modifier.DOUBLE_WORD },
                { new Position(2, 2), Modifier.DOUBLE_WORD },
                { new Position(2, 12), Modifier.DOUBLE_WORD },
                { new Position(3, 3), Modifier.DOUBLE_WORD },
                { new Position(3, 11), Modifier.DOUBLE_WORD },
                { new Position(4, 4), Modifier.DOUBLE_WORD },
                { new Position(4, 10), Modifier.DOUBLE_WORD },
                { new Position(7, 7), Modifier.DOUBLE_WORD },
                { new Position(10, 4), Modifier.DOUBLE_WORD },
                { new Position(10, 10), Modifier.DOUBLE_WORD },
                { new Position(11, 3), Modifier.DOUBLE_WORD },
                { new Position(11, 11), Modifier.DOUBLE_WORD },
                { new Position(12, 2), Modifier.DOUBLE_WORD },
                { new Position(12, 12), Modifier.DOUBLE_WORD },
                { new Position(13, 1), Modifier.DOUBLE_WORD },
                { new Position(13, 13), Modifier.DOUBLE_WORD },

                { new Position(0, 0), Modifier.TRIPLE_WORD },
                { new Position(7, 0), Modifier.TRIPLE_WORD },
                { new Position(14, 0), Modifier.TRIPLE_WORD },
                { new Position(0, 7), Modifier.TRIPLE_WORD },
                { new Position(14, 7), Modifier.TRIPLE_WORD },
                { new Position(0, 14), Modifier.TRIPLE_WORD },
                { new Position(7, 14), Modifier.TRIPLE_WORD },
                { new Position(14, 14), Modifier.TRIPLE_WORD }

            };
        for (int i = 0; i < modifiers.length; i++) {
            Object[] modifier = modifiers[i];
            MODIFIERS.put(modifier[0], modifier[1]);
        }
    }

    // Fields
    private final Tile[][] mTiles;
    private final Move mMove = new Move();

    // Methods: static

    public static int getSize() {
        return Position.BOARD_SIZE;
    }

    public static Modifier getModifier(int pX, int pY) {
        Position.assertIsValid(pX, pY);
        return getModifier(new Position(pX, pY));
    }

    private static Modifier getModifier(Position pPosition) {
        Assert.assertNotNull(pPosition);
        return MODIFIERS.containsKey(pPosition)
            ? (Modifier) MODIFIERS.get(pPosition) : Modifier.DEFAULT;
    }

    // Constructors

    public Board() {
        int size = getSize();
        mTiles = new Tile[size][size];
    }

    // Methods: queries

    public Tile getTile(int pX, int pY) {
        Position.assertIsValid(pX, pY);
        Assert.assertTrue(hasTile(pX, pY));
        return mTiles[pX][pY];
    }

    public Tile getTile(Position pPosition) {
        Assert.assertNotNull(pPosition);
        return getTile(pPosition.getX(), pPosition.getY());
    }

    public int getUnapprovedTileCount() {
        return mMove.size();
    }

    public boolean hasTile(int pX, int pY) {
        Position.assertIsValid(pX, pY);
        return mTiles[pX][pY] != null;
    }

    private boolean hasTile(Position pPosition) {
        Assert.assertNotNull(pPosition);
        return hasTile(pPosition.getX(), pPosition.getY());
    }

    public boolean hasApprovedTiles() {
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < getSize(); y++) {
                if (hasApprovedTile(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasApprovedTile(int pX, int pY) {
        return isOnBoardAndHasTile(pX, pY) && ! hasUnapprovedTile(pX, pY);
    }

    public boolean hasUnapprovedTiles() {
        return ! mMove.isEmpty();
    }

    public boolean hasUnapprovedTile(int pX, int pY) {
        return hasUnapprovedTile(new Position(pX, pY));
    }

    public boolean hasUnapprovedTile(Position pPosition) {
        Assert.assertNotNull(pPosition);
        return mMove.contains(pPosition);
    }

    // Methods: place, move, remove and preconditions

    public boolean canPlace(int pX, int pY) {
        return Position.isValid(pX, pY) && canPlace(new Position(pX, pY));
    }

    public boolean canPlace(Position pPosition) {
        Assert.assertNotNull(pPosition);
        return ! hasTile(pPosition);
    }

    public void place(Tile pTile, Position pPosition) {
        Assert.assertNotNull(pPosition);
        Assert.assertTrue(canPlace(pPosition));

        mTiles[pPosition.getX()][pPosition.getY()] = pTile;
        mMove.add(pPosition);

    }

    public void place(Tile pTile, int pX, int pY) {
        Position.assertIsValid(pX, pY);
        place(pTile, new Position(pX, pY));
    }

    private boolean canPlace(Rack pRack, TransferSet pTransferSet) {
        Assert.assertNotNull(pRack);
        Assert.assertNotNull(pTransferSet);

        for (Iterator transfers = pTransferSet.iterator(); transfers.hasNext();)
        {
            Transfer transfer = (Transfer) transfers.next();
            if (! (pRack.contains(transfer.getRackPosition()) &&
                canPlace(transfer.getBoardPosition()))) {
                return false;
            }
        }
        return true;

    }

    public void place(Rack pRack, TransferSet pTransferSet) {
        Assert.assertNotNull(pRack);
        Assert.assertNotNull(pTransferSet);
        Assert.assertTrue(canPlace(pRack, pTransferSet));

        for (Iterator transfers = pTransferSet.iterator(); transfers.hasNext();)
        {
            Transfer transfer = (Transfer) transfers.next();
            place(pRack.get(transfer.getRackPosition()),
                transfer.getBoardPosition());
        }

    }

    public boolean canMove(Position pSource, Position pDestination) {
        Assert.assertNotNull(pSource);
        Assert.assertNotNull(pDestination);
        return ! pSource.equals(pDestination) && hasUnapprovedTile(pSource) &&
            (canPlace(pDestination) || hasUnapprovedTile(pDestination));
    }

    public void move(Position pSource, Position pDestination) {
        Assert.assertNotNull(pSource);
        Assert.assertNotNull(pDestination);
        Assert.assertTrue(canMove(pSource, pDestination));

        if (hasTile(pDestination)) {
            Tile sourceTile = getTile(pSource);
            Tile destinationTile = getTile(pDestination);
            remove(pSource);
            remove(pDestination);
            place(sourceTile, pDestination);
            place(destinationTile, pSource);
        } else {
            Tile tile = getTile(pSource);
            remove(pSource);
            place(tile, pDestination);
        }

    }

    public void remove(Position pPosition) {
        Assert.assertNotNull(pPosition);
        Assert.assertTrue(hasTile(pPosition));

        mTiles[pPosition.getX()][pPosition.getY()] = null;
        mMove.remove(pPosition);

    }

    // Methods: canFinish

    public boolean canFinish() {
        return
            mMove.canFinish() &&
                (isOnStartingPosition() && mMove.size() >= 2 ||
                    moveHasApprovedNeighbor()) &&
                hasTiles(mMove.getEnvelope());
    }

    private boolean isOnStartingPosition() {
        return mMove.contains(Position.BOARD_SIZE / 2, Position.BOARD_SIZE / 2);
    }

    private boolean moveHasApprovedNeighbor() {
        Iterator positions = mMove.iterator();
        while (positions.hasNext()) {
            if (hasApprovedNeighbor((Position) positions.next())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasApprovedNeighbor(Position pPosition) {
        int x = pPosition.getX();
        int y = pPosition.getY();
        return
            hasApprovedTile(x - 1, y) ||
                hasApprovedTile(x + 1, y) ||
                hasApprovedTile(x, y - 1) ||
                hasApprovedTile(x, y + 1);
    }

    // Methods: score

    public int getScore() {
        Assert.assertTrue(canFinish());

        int score = 0;
        Iterator newWords = getNewWords().iterator();
        while (newWords.hasNext()) {
            score += score((StickySet) newWords.next());
        }

        Assert.assertTrue(score >= 0);
        return score;
    }

    private StickySet getNewWords() {
        StickySet newWords = new HashStickySet();
        Axis axisMoveParallels =
            mMove.size() == 1 ? Axis.X : mMove.getParallels();

        // The move may form a word along its axis ...
        Position arbitrary = (Position) mMove.iterator().next();
        if (hasNeighbor(arbitrary, axisMoveParallels)) {
            newWords.add(getNewWord(arbitrary, axisMoveParallels));
        }

        // ... and may form a word along the other axis at any position.
        Iterator positions = mMove.iterator();
        Axis axisMoveCrosses = axisMoveParallels.other();
        while (positions.hasNext()) {
            Position position = (Position) positions.next();
            if (hasNeighbor(position, axisMoveCrosses)) {
                newWords.add(getNewWord(position, axisMoveCrosses));
            }
        }

        Assert.assertTrue(newWords.size() >= 1);
        return newWords;
    }

    /**
     * @return a StickySet of Positions
     */
    private StickySet getNewWord(Position pPosition, Axis pAxis) {
        Assert.assertTrue(hasUnapprovedTile(pPosition));
        Assert.assertTrue(hasNeighbor(pPosition, pAxis));

        StickySet newWord = new HashStickySet();
        newWord.add(pPosition);
        newWord.addAll(extendNewWord(pPosition, pAxis, -1));
        newWord.addAll(extendNewWord(pPosition, pAxis, 1));

        Assert.assertTrue(newWord.size() > 1);
        Assert.assertTrue(hasTiles(newWord));
        return newWord;
    }

    private StickySet extendNewWord(Position pPosition, Axis pAxis,
        int pIncrement) {
        StickySet positions = new HashStickySet();
        int x = pPosition.getX();
        int y = pPosition.getY();
        while (true) {
            if (pAxis.equals(Axis.X)) {
                x += pIncrement;
            } else {
                y += pIncrement;
            }
            if (isOnBoardAndHasTile(x, y)) {
                positions.add(new Position(x, y));
            } else {
                break;
            }
        }
        return positions;
    }

    /**
     * @param pWord a StickySet of Positions from either the move or the board
     */
    private int score(StickySet pWord) {
        Assert.assertTrue(hasTiles(pWord));

        int score = 0;
        int multiplier = 1;
        Iterator positions = pWord.iterator();
        while (positions.hasNext()) {
            Position position = (Position) positions.next();
            score += score(position);
            if (mMove.contains(position)) {
                multiplier *= getModifier(position).getWordMultiplier();
            }
        }
        score *= multiplier;

        Assert.assertTrue(score >= 0);
        return score;
    }

    private int score(Position pPosition) {
        Assert.assertTrue(hasTile(pPosition));

        int score = getTile(pPosition).getValue();
        if (mMove.contains(pPosition)) {
            score *= getModifier(pPosition).getLetterMultiplier();
        }

        Assert.assertTrue(score >= 0);
        return score;
    }

    // Methods: approve

    public void approve() {
        Assert.assertTrue(canFinish());
        mMove.clear();
    }

    // Methods: helper

    private boolean hasTiles(Collection pWord) {
        Iterator positions = pWord.iterator();
        while (positions.hasNext()) {
            Position position = (Position) positions.next();
            if (! hasTile(position)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasNeighbor(Position pPosition, Axis pAxis) {
        int x = pPosition.getX();
        int y = pPosition.getY();
        if (pAxis.equals(Axis.X)) {
            return isOnBoardAndHasTile(x - 1, y) || isOnBoardAndHasTile(x + 1, y);
        } else {
            return isOnBoardAndHasTile(x, y - 1) || isOnBoardAndHasTile(x, y + 1);
        }
    }

    private boolean isOnBoardAndHasTile(int pX, int pY) {
        return Position.isValid(pX, pY) && hasTile(pX, pY);
    }

    // Methods: overrides

    public boolean equals(Object pOther) {
        if (pOther == null || ! pOther.getClass().equals(getClass())) {
            return false;
        }
        Board board = (Board) pOther;
        return CollectionUtil.equals(mTiles, board.mTiles) &&
            mMove.equals(board.mMove);
    }

    public int hashCode() {
        return 3 * hashCode(mTiles) + mMove.hashCode();
    }

    private static int hashCode(Object[][] pObject) {
        int hashCode = 0;
        for (int i = 0; i < pObject.length; i++) {
            Object[] row = pObject[i];
            for (int j = 0; j < row.length; j++) {
                Object value = row[j];
                if (value != null) {
                    hashCode *= 3;
                    hashCode += value.hashCode();
                }
            }
        }
        return hashCode;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer("\n");
        for (int y = getSize() - 1; y >= 0; y--) {
            for (int x = 0; x < getSize(); x++) {
                if (hasTile(x, y)) {
                    char letter = getTile(x, y).getLetter();
                    if (mMove.contains(x, y)) {
                        letter = Character.toLowerCase(letter);
                    }
                    buf.append(" " + letter + ' ');
                } else {
                    buf.append(asString(getModifier(x, y)));
                }
                if (x < getSize() - 1) {
                    buf.append(" ");
                } else {
                    buf.append("\n");
                }
            }
        }
        return buf.toString();
    }

    private static String asString(Modifier modifier) {
        int letterModifier = modifier.getLetterMultiplier();
        int wordModifier = modifier.getWordMultiplier();
        if (letterModifier > 1) {
            return letterModifier + "LS";
        } else if (wordModifier > 1) {
            return wordModifier + "WS";
        } else {
            return "   ";
        }
    }

}
