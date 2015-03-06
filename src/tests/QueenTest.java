package tests;

import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;
import com.dpzmick.chess.model.pieces.Queen;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class QueenTest {
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

        Queen myQueen = new Queen(this.p1);
        BoardLocation loc = new BoardLocation(3, 3);

        b.putPiece(myQueen, loc);

        // rightup and leftdown
        for (int i = 0; i < 8; i++) {
            if (i != 3) {
                BoardLocation newLoc = new BoardLocation(i, i);
                assertTrue(myQueen.canMove(loc, newLoc, b));
            }
        }

        //rightdown and leftup
        for (int i = 0; i < 7; i++) {
            if (i != 3) {
                BoardLocation newLoc = new BoardLocation(i, 6 - i); // 6 b there is no center of 8x8 board
                assertTrue(myQueen.canMove(loc, newLoc, b));
            }
        }

        // right and left
        for (int i = 0; i < 8; i++) {
            if (i == 3) continue;
            BoardLocation newLoc = new BoardLocation(i, 3);
            TestCase.assertTrue(myQueen.canMove(loc, newLoc, b));
        }

        // up and down
        for (int i = 0; i < 8; i++) {
            if (i == 3) continue;
            BoardLocation newLoc = new BoardLocation(3, i);
            TestCase.assertTrue(myQueen.canMove(loc, newLoc, b));
        }
    }

    @Test
    public void testStuckQueen() {
        Board b = new Board(new BoardLocation(1, 1), null);

        Queen stuckQueen = new Queen(this.p1);
        BoardLocation loc = new BoardLocation(0, 0);
        b.putPiece(stuckQueen, loc);

        assertEquals(0, stuckQueen.allPotentialMoves(loc, b).size());
    }
}
