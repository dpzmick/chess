package tests;

import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;
import com.dpzmick.chess.model.pieces.Alfil;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class AlfilTest {
    private Player p1;
    private Player p2;

    @Before
    public void init() {
        p1 = new Player(Player.PlayerColor.WHITE);
        p2 = new Player(Player.PlayerColor.BLACK);
    }

    @Test
    public void testEmptyMoves() {
        Alfil myAlfil = new Alfil(this.p1);

        Board b = Board.normalChessBoard(null);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myAlfil, loc);

        assertTrue(myAlfil.canMove(loc, new BoardLocation(loc, -2, -2), b));
        assertTrue(myAlfil.canMove(loc, new BoardLocation(loc, -2, 2), b));
        assertTrue(myAlfil.canMove(loc, new BoardLocation(loc, 2, -2), b));
        assertTrue(myAlfil.canMove(loc, new BoardLocation(loc, 2, 2), b));

        assertFalse(myAlfil.canMove(loc, loc, b));
    }

    @Test
    public void testBlocked() {
        Alfil myAlfil = new Alfil(this.p1);
        Alfil blocker = new Alfil(this.p1);

        Board b = Board.normalChessBoard(null);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myAlfil, loc);

        BoardLocation blockerLoc = new BoardLocation(loc, 2, 2);
        b.putPiece(blocker, blockerLoc);

        assertTrue(myAlfil.canMove(loc, new BoardLocation(loc, -2, -2), b));
        assertTrue(myAlfil.canMove(loc, new BoardLocation(loc, -2, 2), b));
        assertTrue(myAlfil.canMove(loc, new BoardLocation(loc, 2, -2), b));

        assertFalse(myAlfil.canMove(loc, new BoardLocation(loc, 2, 2), b));
        assertFalse(myAlfil.canMove(loc, loc, b));
    }

    @Test
    public void moveOffBoard() {
        Board b = new Board(new BoardLocation(1, 1), null);

        Alfil stuckAlfil = new Alfil(this.p1);
        BoardLocation loc = new BoardLocation(0, 0);
        b.putPiece(stuckAlfil, loc);

        assertEquals(0, stuckAlfil.allPotentialMoves(loc, b).size());
    }

    @Test
    public void testCapture() {
        Alfil myAlfil = new Alfil(this.p1);
        Alfil blocker = new Alfil(this.p2);

        Board b = Board.normalChessBoard(null);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myAlfil, loc);

        BoardLocation blockerLoc = new BoardLocation(loc, -2, -2);
        b.putPiece(blocker, blockerLoc);

        assertTrue(myAlfil.canMove(loc, new BoardLocation(loc, -2, -2), b));
        assertTrue(myAlfil.canMove(loc, new BoardLocation(loc, -2, 2), b));
        assertTrue(myAlfil.canMove(loc, new BoardLocation(loc, 2, -2), b));
        assertTrue(myAlfil.canMove(loc, new BoardLocation(loc, 2, 2), b));

        assertFalse(myAlfil.canMove(loc, loc, b));
    }
}
