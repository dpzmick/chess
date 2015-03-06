package tests;

import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;
import com.dpzmick.chess.model.pieces.BerolinaPawn;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class BerolinaPawnTest {
    private Player p1;
    private Player p2;

    @Before
    public void init() {
        p1 = new Player(Player.PlayerColor.WHITE);
        p2 = new Player(Player.PlayerColor.BLACK);
    }

    @Test
    public void testFirstMove() {
        Board b = Board.normalChessBoard(null);

        BerolinaPawn myBerolinaPawn = new BerolinaPawn(this.p1);
        BerolinaPawn myOtherBerolinaPawn = new BerolinaPawn(this.p2);

        BoardLocation loc = new BoardLocation(2, 4);
        b.putPiece(myBerolinaPawn, loc);

        BoardLocation loc2 = new BoardLocation(2, 6);
        b.putPiece(myOtherBerolinaPawn, loc2);

        System.out.println(myBerolinaPawn.allPotentialMoves(loc, b));

        assertTrue(myBerolinaPawn.canMove(loc, new BoardLocation(loc, 1, 1), b));
        assertTrue(myBerolinaPawn.canMove(loc, new BoardLocation(loc, 2, 2), b));
        assertTrue(myBerolinaPawn.canMove(loc, new BoardLocation(loc, -1, 1), b));
        assertTrue(myBerolinaPawn.canMove(loc, new BoardLocation(loc, -2, 2), b));

        assertTrue(myOtherBerolinaPawn.canMove(loc2, new BoardLocation(loc2, 1, -1), b));
        assertTrue(myOtherBerolinaPawn.canMove(loc2, new BoardLocation(loc2, 2, -2), b));
        assertTrue(myOtherBerolinaPawn.canMove(loc2, new BoardLocation(loc2, -1, -1), b));
        assertTrue(myOtherBerolinaPawn.canMove(loc2, new BoardLocation(loc2, -2, -2), b));
    }

    @Test
    public void testStuckBerolinaPawn() {
        Board b = new Board(new BoardLocation(1, 1), null);

        BerolinaPawn stuckBerolinaPawn = new BerolinaPawn(this.p1);
        BoardLocation loc = new BoardLocation(0, 0);
        b.putPiece(stuckBerolinaPawn, loc);

        assertEquals(0, stuckBerolinaPawn.allPotentialMoves(loc, b).size());
    }

    @Test
    public void testBlockedYourselfFirstMove1() {
        Board b = Board.normalChessBoard(null);

        BerolinaPawn myBerolinaPawn = new BerolinaPawn(this.p1);

        BerolinaPawn blocker = new BerolinaPawn(this.p1);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myBerolinaPawn, loc);

        BoardLocation blockerLocation = new BoardLocation(loc, 2, 2);
        b.putPiece(blocker, blockerLocation);

        assertTrue(myBerolinaPawn.canMove(loc, new BoardLocation(loc, 1, 1), b));
        assertTrue(myBerolinaPawn.canMove(loc, new BoardLocation(loc, -1, 1), b));
        assertTrue(myBerolinaPawn.canMove(loc, new BoardLocation(loc, -2, 2), b));
        assertFalse(myBerolinaPawn.canMove(loc, new BoardLocation(loc, 2, -2), b));
    }

    @Test
    public void testBlockedYourselfFirstMove2() {
        Board b = Board.normalChessBoard(null);

        BerolinaPawn myBerolinaPawn = new BerolinaPawn(this.p1);
        BerolinaPawn blocker = new BerolinaPawn(this.p1);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myBerolinaPawn, loc);

        BoardLocation blockerLocation = new BoardLocation(loc, 1, 1);
        b.putPiece(blocker, blockerLocation);

        assertFalse(myBerolinaPawn.canMove(loc, new BoardLocation(loc, 1, 1), b));
        assertFalse(myBerolinaPawn.canMove(loc, new BoardLocation(loc, 2, 2), b));
    }

    @Test
    public void testBlockedFirstMove1() {
        Board b = Board.normalChessBoard(null);

        BerolinaPawn myBerolinaPawn = new BerolinaPawn(this.p1);
        BerolinaPawn blocker = new BerolinaPawn(this.p2);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myBerolinaPawn, loc);

        BoardLocation blockerLocation = new BoardLocation(loc, 2, 2);
        b.putPiece(blocker, blockerLocation);

        assertTrue(myBerolinaPawn.canMove(loc, new BoardLocation(loc, 1, 1), b));
        assertTrue(myBerolinaPawn.canMove(loc, new BoardLocation(loc, -1, 1), b));
        assertTrue(myBerolinaPawn.canMove(loc, new BoardLocation(loc, -2, 2), b));
        assertFalse(myBerolinaPawn.canMove(loc, new BoardLocation(loc, 2, 2), b));

        assertTrue(blocker.canMove(blockerLocation, new BoardLocation(blockerLocation, -1, -1), b));
        assertTrue(blocker.canMove(blockerLocation, new BoardLocation(blockerLocation, 1, -1), b));
        assertFalse(blocker.canMove(blockerLocation, new BoardLocation(blockerLocation, -2, -2), b));
    }

    @Test
    public void testBlockedFirstMove2() {
        Board b = Board.normalChessBoard(null);

        BerolinaPawn myBerolinaPawn = new BerolinaPawn(this.p1);
        BerolinaPawn blocker = new BerolinaPawn(this.p2);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myBerolinaPawn, loc);

        BoardLocation blockerLocation = new BoardLocation(loc, 1, 1);
        b.putPiece(blocker, blockerLocation);

        assertFalse(myBerolinaPawn.canMove(loc, new BoardLocation(loc, 1, 1), b));
        assertFalse(myBerolinaPawn.canMove(loc, new BoardLocation(loc, 2, 2), b));
        assertTrue(myBerolinaPawn.canMove(loc, new BoardLocation(loc, -1, 1), b));
        assertTrue(myBerolinaPawn.canMove(loc, new BoardLocation(loc, -2, 2), b));

        assertFalse(blocker.canMove(blockerLocation, new BoardLocation(loc, 1, -1), b));
        assertFalse(blocker.canMove(blockerLocation, new BoardLocation(loc, -1, -1), b));
        assertFalse(blocker.canMove(blockerLocation, new BoardLocation(loc, 2, -2), b));
        assertFalse(blocker.canMove(blockerLocation, new BoardLocation(loc, -2, -2), b));
    }

    @Test
    public void testBerolinaPawnsGoRight() {
        Board b = Board.normalChessBoard(null);
        BerolinaPawn p = new BerolinaPawn(p1);

        BoardLocation from = new BoardLocation(2, 1);
        b.putPiece(p, from);

        BoardLocation right = new BoardLocation(3, 1);

        assertFalse(p.canMove(from, right, b));
    }

    @Test
    public void testTakePiece() {
        Board b = Board.normalChessBoard(null);

        BerolinaPawn myBerolinaPawn = new BerolinaPawn(this.p1);
        BerolinaPawn enemy = new BerolinaPawn(this.p2);

        BoardLocation loc = new BoardLocation(4, 4);
        BoardLocation enemyLoc = new BoardLocation(loc, 0, 1);
        b.putPiece(myBerolinaPawn, loc);

        assertFalse(myBerolinaPawn.canMove(loc, enemyLoc, b));

        b.putPiece(enemy, enemyLoc);
        assertTrue(myBerolinaPawn.canMove(loc, enemyLoc, b));
        assertTrue(enemy.canMove(enemyLoc, loc, b)); // check that the enemy can get to us as well
    }
}
