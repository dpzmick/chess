package tests;

import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;
import com.dpzmick.chess.model.pieces.King;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class KingTest {
    private Player p1;
    private Player p2;

    @Before
    public void init() {
        p1 = new Player(Player.PlayerColor.WHITE);
        p2 = new Player(Player.PlayerColor.BLACK);
    }

    @Test
    public void testEmptyMoves() {
        King myKing = new King(this.p1);

        Board b = Board.normalChessBoard(null);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myKing, loc);

        // written by hand (vim mode) to avoid making a stupid test
        assertEquals(true, myKing.canMove(loc, new BoardLocation(3, 3), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(3, 4), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(3, 5), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(4, 3), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(4, 5), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(5, 3), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(5, 4), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(5, 5), b));

        assertEquals(false, myKing.canMove(loc, new BoardLocation(4, 4), b));
    }

    @Test
    public void testBlocked() {
        King myKing = new King(this.p1);
        King blocker = new King(this.p1);

        Board b = Board.normalChessBoard(null);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myKing, loc);

        BoardLocation blockerLoc = new BoardLocation(4, 5);
        b.putPiece(blocker, blockerLoc);

        // written by hand (vim mode) to avoid making a stupid test
        assertEquals(true, myKing.canMove(loc, new BoardLocation(3, 3), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(3, 4), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(3, 5), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(4, 3), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(5, 3), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(5, 4), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(5, 5), b));

        assertEquals(myKing.canMove(loc, new BoardLocation(4, 5), b), false);
        assertEquals(myKing.canMove(loc, new BoardLocation(4, 4), b), false);
    }

    @Test
    public void moveOffBoard() {
        Board b = new Board(new BoardLocation(1, 1), null);

        King stuckKing = new King(this.p1);
        BoardLocation loc = new BoardLocation(0, 0);
        b.putPiece(stuckKing, loc);

        assertEquals(0, stuckKing.allPotentialMoves(loc, b).size());
    }

    @Test
    public void testCapture() {
        King myKing = new King(this.p1);
        King blocker = new King(this.p2);

        Board b = Board.normalChessBoard(null);

        BoardLocation loc = new BoardLocation(4, 4);
        b.putPiece(myKing, loc);

        BoardLocation blockerLoc = new BoardLocation(4, 5);
        b.putPiece(blocker, blockerLoc);

        // written by hand (vim mode) to avoid making a stupid test
        assertEquals(true, myKing.canMove(loc, new BoardLocation(3, 3), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(3, 4), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(3, 5), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(4, 3), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(5, 3), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(5, 4), b));
        assertEquals(true, myKing.canMove(loc, new BoardLocation(5, 5), b));

        // should still be able to move to this location
        assertEquals(true, myKing.canMove(loc, new BoardLocation(4, 5), b));

        assertEquals(false, myKing.canMove(loc, new BoardLocation(4, 4), b));
    }
}
