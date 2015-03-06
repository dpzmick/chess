package tests;

import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;
import com.dpzmick.chess.model.pieces.Pawn;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class PawnTest {
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

        Pawn myPawn = new Pawn(this.p1);
        Pawn myOtherPawn = new Pawn(this.p2);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myPawn, loc);

        BoardLocation loc2 = new BoardLocation(6, 6);

        assertTrue(myPawn.canMove(loc, new BoardLocation(loc, 0, 1), b));
        assertTrue(myPawn.canMove(loc, new BoardLocation(loc, 0, 2), b));

        assertTrue(myOtherPawn.canMove(loc2, new BoardLocation(loc2, 0, -1), b));
        assertTrue(myOtherPawn.canMove(loc2, new BoardLocation(loc2, 0, -2), b));
    }

    @Test
    public void testStuckPawn() {
        Board b = new Board(new BoardLocation(1, 1), null);

        Pawn stuckPawn = new Pawn(this.p1);
        BoardLocation loc = new BoardLocation(0, 0);
        b.putPiece(stuckPawn, loc);

        assertEquals(0, stuckPawn.allPotentialMoves(loc, b).size());
    }

    @Test
    public void testBlockedYourselfFirstMove1() {
        Board b = Board.normalChessBoard(null);

        Pawn myPawn = new Pawn(this.p1);

        Pawn blocker = new Pawn(this.p1);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myPawn, loc);

        BoardLocation blockerLocation = new BoardLocation(loc, 0, 2);
        b.putPiece(blocker, blockerLocation);

        assertEquals(true, myPawn.canMove(loc, new BoardLocation(loc, 0, 1), b));
        assertEquals(false, myPawn.canMove(loc, new BoardLocation(loc, 0, 2), b));
    }

    @Test
    public void testBlockedYourselfFirstMove2() {
        Board b = Board.normalChessBoard(null);

        Pawn myPawn = new Pawn(this.p1);
        Pawn blocker = new Pawn(this.p1);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myPawn, loc);

        BoardLocation blockerLocation = new BoardLocation(loc, 0, 1);
        b.putPiece(blocker, blockerLocation);

        assertFalse(myPawn.canMove(loc, new BoardLocation(loc, 0, 1), b));
        assertFalse(myPawn.canMove(loc, new BoardLocation(loc, 0, 2), b));
    }

    @Test
    public void testBlockedFirstMove1() {
        Board b = Board.normalChessBoard(null);

        Pawn myPawn = new Pawn(this.p1);
        Pawn blocker = new Pawn(this.p2);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myPawn, loc);

        BoardLocation blockerLocation = new BoardLocation(loc, 0, 2);
        b.putPiece(blocker, blockerLocation);

        assertTrue(myPawn.canMove(loc, new BoardLocation(loc, 0, 1), b));
        assertFalse(myPawn.canMove(loc, new BoardLocation(loc, 0, 2), b));

        assertTrue(blocker.canMove(blockerLocation, new BoardLocation(blockerLocation, 0, -1), b));
        assertFalse(blocker.canMove(blockerLocation, new BoardLocation(blockerLocation, 0, -2), b));
    }

    @Test
    public void testBlockedFirstMove2() {
        Board b = Board.normalChessBoard(null);

        Pawn myPawn = new Pawn(this.p1);
        Pawn blocker = new Pawn(this.p2);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myPawn, loc);

        BoardLocation blockerLocation = new BoardLocation(loc, 0, 1);
        b.putPiece(blocker, blockerLocation);

        assertFalse(myPawn.canMove(loc, new BoardLocation(loc, 0, 1), b));
        assertFalse(myPawn.canMove(loc, new BoardLocation(loc, 0, 2), b));
        assertFalse(blocker.canMove(blockerLocation, new BoardLocation(loc, 0, -1), b));
        assertFalse(blocker.canMove(blockerLocation, new BoardLocation(loc, 0, -2), b));
    }

    @Test
    public void testPawnsGoRight() {
        Board b = Board.normalChessBoard(null);
        Pawn p = new Pawn(p1);

        BoardLocation from = new BoardLocation(2, 1);
        b.putPiece(p, from);

        BoardLocation right = new BoardLocation(3, 1);

        assertFalse(p.canMove(from, right, b));
    }

    @Test
    public void testTakePieceRight() {
        Board b = Board.normalChessBoard(null);

        Pawn myPawn = new Pawn(this.p1);
        Pawn enemy = new Pawn(this.p2);

        BoardLocation loc = new BoardLocation(4, 4);
        BoardLocation enemyLoc = new BoardLocation(loc, 1, 1);
        b.putPiece(myPawn, loc);

        assertFalse(myPawn.canMove(loc, enemyLoc, b));

        b.putPiece(enemy, enemyLoc);
        assertTrue(myPawn.canMove(loc, enemyLoc, b));
        assertTrue(enemy.canMove(enemyLoc, loc, b)); // check that the enemy can get to us as well
    }

    @Test
    public void testTakePieceLeft() {
        Board b = Board.normalChessBoard(null);

        Pawn myPawn = new Pawn(this.p1);
        Pawn enemy = new Pawn(this.p2);

        BoardLocation loc = new BoardLocation(4, 4);
        BoardLocation enemyLoc = new BoardLocation(loc, -1, 1);
        b.putPiece(myPawn, loc);

        assertFalse(myPawn.canMove(loc, enemyLoc, b));

        b.putPiece(enemy, enemyLoc);
        assertTrue(myPawn.canMove(loc, enemyLoc, b));
        assertTrue(enemy.canMove(enemyLoc, loc, b));
    }
}
