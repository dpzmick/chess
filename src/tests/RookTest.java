package tests;

import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;
import com.dpzmick.chess.model.pieces.Rook;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RookTest {
    private Player p1;
    private Player p2;

    @Before
    public void init() {
        p1 = new Player(Player.PlayerColor.WHITE);
        p2 = new Player(Player.PlayerColor.BLACK);
    }

    @Test
    public void testEmptyMoves() {
        Board b = Board.normalChessBoard(null);

        Rook myRook = new Rook(this.p1);
        BoardLocation loc = new BoardLocation(4, 4);

        b.putPiece(myRook, loc);

        assertEquals(true, myRook.canMove(loc, new BoardLocation(0, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(1, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(2, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(3, 4), b));

        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(5, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(6, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(7, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 0), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 1), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 2), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 3), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 5), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 6), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 7), b));
    }

    @Test
    public void testStuckRook() {
        Board b = new Board(new BoardLocation(1, 1), null);

        Rook stuckRook = new Rook(this.p1);
        BoardLocation loc = new BoardLocation(0, 0);
        b.putPiece(stuckRook, loc);

        assertEquals(0, stuckRook.allPotentialMoves(loc, b).size());
    }

    @Test
    public void testBlockedYourselfLeft() {
        Board b = Board.normalChessBoard(null);

        Rook myRook = new Rook(this.p1);
        BoardLocation loc = new BoardLocation(4, 4);

        Rook blocker = new Rook(this.p1);
        BoardLocation blockerLoc = new BoardLocation(2, 4);

        b.putPiece(blocker, blockerLoc);
        b.putPiece(myRook, loc);

        // blocked
        assertEquals(false, myRook.canMove(loc, new BoardLocation(0, 4), b));
        assertEquals(false, myRook.canMove(loc, new BoardLocation(1, 4), b));
        assertEquals(false, myRook.canMove(loc, new BoardLocation(2, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(3, 4), b));

        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(5, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(6, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(7, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 0), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 1), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 2), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 3), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 5), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 6), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 7), b));
    }

    @Test
    public void testBlockedYourselfRight() {
        Board b = Board.normalChessBoard(null);

        Rook myRook = new Rook(this.p1);
        BoardLocation loc = new BoardLocation(4, 4);

        Rook blocker = new Rook(this.p1);
        BoardLocation blockerLoc = new BoardLocation(6, 4);

        b.putPiece(blocker, blockerLoc);
        b.putPiece(myRook, loc);

        assertEquals(true, myRook.canMove(loc, new BoardLocation(0, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(1, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(2, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(3, 4), b));

        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(5, 4), b));

        // blocked
        assertEquals(false, myRook.canMove(loc, new BoardLocation(6, 4), b));
        assertEquals(false, myRook.canMove(loc, new BoardLocation(7, 4), b));

        // not blocked
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 0), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 1), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 2), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 3), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 5), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 6), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 7), b));
    }

    @Test
    public void testBlockedYourselfUp() {
        Board b = Board.normalChessBoard(null);

        Rook myRook = new Rook(this.p1);
        BoardLocation loc = new BoardLocation(4, 4);

        Rook blocker = new Rook(this.p1);
        BoardLocation blockerLoc = new BoardLocation(4, 6);

        b.putPiece(blocker, blockerLoc);
        b.putPiece(myRook, loc);

        assertEquals(true, myRook.canMove(loc, new BoardLocation(0, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(1, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(2, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(3, 4), b));

        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(5, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(6, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(7, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 0), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 1), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 2), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 3), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 5), b));

        // blocked
        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 6), b));
        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 7), b));
    }

    @Test
    public void testBlockedYourselfDown() {
        Board b = Board.normalChessBoard(null);

        Rook myRook = new Rook(this.p1);
        BoardLocation loc = new BoardLocation(4, 4);

        Rook blocker = new Rook(this.p1);
        BoardLocation blockerLoc = new BoardLocation(4, 2);

        b.putPiece(blocker, blockerLoc);
        b.putPiece(myRook, loc);

        assertEquals(true, myRook.canMove(loc, new BoardLocation(0, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(1, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(2, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(3, 4), b));

        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(5, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(6, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(7, 4), b));

        // blocked
        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 0), b));
        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 1), b));
        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 2), b));

        // unblocked
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 3), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 5), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 6), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 7), b));
    }

    @Test
    public void testCaptureLeft() {
        Board b = Board.normalChessBoard(null);

        Rook myRook = new Rook(this.p1);
        BoardLocation loc = new BoardLocation(4, 4);

        Rook blocker = new Rook(this.p2);
        BoardLocation blockerLoc = new BoardLocation(2, 4);

        b.putPiece(blocker, blockerLoc);
        b.putPiece(myRook, loc);

        // blocked
        assertEquals(false, myRook.canMove(loc, new BoardLocation(0, 4), b));
        assertEquals(false, myRook.canMove(loc, new BoardLocation(1, 4), b));

        // but can capture this piece
        assertEquals(true, myRook.canMove(loc, new BoardLocation(2, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(3, 4), b));

        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(5, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(6, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(7, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 0), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 1), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 2), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 3), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 5), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 6), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 7), b));
    }

    @Test
    public void testCaptureRight() {
        Board b = Board.normalChessBoard(null);

        Rook myRook = new Rook(this.p1);
        BoardLocation loc = new BoardLocation(4, 4);

        Rook blocker = new Rook(this.p2);
        BoardLocation blockerLoc = new BoardLocation(6, 4);

        b.putPiece(blocker, blockerLoc);
        b.putPiece(myRook, loc);

        assertEquals(true, myRook.canMove(loc, new BoardLocation(0, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(1, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(2, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(3, 4), b));

        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(5, 4), b));

        // capture
        assertEquals(true, myRook.canMove(loc, new BoardLocation(6, 4), b));

        // blocked
        assertEquals(false, myRook.canMove(loc, new BoardLocation(7, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 0), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 1), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 2), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 3), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 5), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 6), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 7), b));
    }

    @Test
    public void testCaptureUp() {
        Board b = Board.normalChessBoard(null);

        Rook myRook = new Rook(this.p1);
        BoardLocation loc = new BoardLocation(4, 4);

        Rook blocker = new Rook(this.p2);
        BoardLocation blockerLoc = new BoardLocation(4, 6);

        b.putPiece(blocker, blockerLoc);
        b.putPiece(myRook, loc);

        assertEquals(true, myRook.canMove(loc, new BoardLocation(0, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(1, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(2, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(3, 4), b));

        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(5, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(6, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(7, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 0), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 1), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 2), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 3), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 5), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 6), b)); // can capture

        // blocked
        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 7), b));
    }

    @Test
    public void testCaptureDown() {
        Board b = Board.normalChessBoard(null);

        Rook myRook = new Rook(this.p1);
        BoardLocation loc = new BoardLocation(4, 4);

        Rook blocker = new Rook(this.p2);
        BoardLocation blockerLoc = new BoardLocation(4, 2);

        b.putPiece(blocker, blockerLoc);
        b.putPiece(myRook, loc);

        assertEquals(true, myRook.canMove(loc, new BoardLocation(0, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(1, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(2, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(3, 4), b));

        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 4), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(5, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(6, 4), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(7, 4), b));

        // blocked
        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 0), b));
        assertEquals(false, myRook.canMove(loc, new BoardLocation(4, 1), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 2), b)); // can capture
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 3), b));

        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 5), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 6), b));
        assertEquals(true, myRook.canMove(loc, new BoardLocation(4, 7), b));
    }
}
