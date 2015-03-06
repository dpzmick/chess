package tests;

import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;
import com.dpzmick.chess.model.pieces.Knight;
import com.dpzmick.chess.model.pieces.Pawn;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class KnightTest {
    private Player p1;
    private Player p2;

    @Before
    public void init() {
        p1 = new Player(Player.PlayerColor.WHITE);
        p2 = new Player(Player.PlayerColor.BLACK);
    }

    @Test
    public void testEmptyMove() {
        Board b = Board.normalChessBoard(null);

        Knight myKnight = new Knight(this.p1);

        BoardLocation loc = new BoardLocation(3, 2);
        b.putPiece(myKnight, loc);

        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 0), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(1, 1), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(1, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(4, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 1), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(4, 0), b));
    }

    @Test
    public void testStuckKnight() {
        Board b = new Board(new BoardLocation(1, 1), null);

        Knight stuckKnight = new Knight(this.p1);
        BoardLocation loc = new BoardLocation(0, 0);
        b.putPiece(stuckKnight, loc);

        assertEquals(0, stuckKnight.allPotentialMoves(loc, b).size());
    }

    @Test
    public void testJumpOverMine() {
        Board b = Board.normalChessBoard(null);

        Knight myKnight = new Knight(this.p1);
        Pawn doesntMatter = new Pawn(this.p1);

        BoardLocation loc = new BoardLocation(3, 2);
        b.putPiece(myKnight, loc);
        b.putPiece(doesntMatter, new BoardLocation(3, 3));

        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 0), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(1, 1), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(1, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(4, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 1), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(4, 0), b));
    }

    @Test
    public void testJumpOverTheirs() {
        Board b = Board.normalChessBoard(null);

        Knight myKnight = new Knight(this.p1);
        Pawn doesntMatter = new Pawn(this.p2);

        BoardLocation loc = new BoardLocation(3, 2);
        b.putPiece(myKnight, loc);
        b.putPiece(doesntMatter, new BoardLocation(3, 3));

        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 0), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(1, 1), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(1, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(4, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 1), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(4, 0), b));
    }

    @Test
    public void testJumpBlockYourself() {
        Board b = Board.normalChessBoard(null);

        Knight myKnight = new Knight(this.p1);
        Pawn doesntMatter = new Pawn(this.p1);

        BoardLocation loc = new BoardLocation(3, 2);
        b.putPiece(myKnight, loc);
        b.putPiece(doesntMatter, new BoardLocation(2, 0));

        // can't addUntilBlocked here now
        assertFalse(myKnight.canMove(loc, new BoardLocation(2, 0), b));

        assertTrue(myKnight.canMove(loc, new BoardLocation(1, 1), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(1, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(4, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 1), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(4, 0), b));
    }

    @Test
    public void testJumpCapture() {
        Board b = Board.normalChessBoard(null);

        Knight myKnight = new Knight(this.p1);
        Pawn ded = new Pawn(this.p2);

        BoardLocation loc = new BoardLocation(3, 2);
        b.putPiece(myKnight, loc);
        b.putPiece(ded, new BoardLocation(2, 0));

        // *can* addUntilBlocked here now
        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 0), b));

        assertTrue(myKnight.canMove(loc, new BoardLocation(1, 1), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(1, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(2, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(4, 4), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 3), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(5, 1), b));
        assertTrue(myKnight.canMove(loc, new BoardLocation(4, 0), b));
    }
}
