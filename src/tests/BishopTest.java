package tests;

import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;
import com.dpzmick.chess.model.pieces.Bishop;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class BishopTest {
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

        Bishop myBishop = new Bishop(this.p1);
        BoardLocation loc = new BoardLocation(3, 3);

        b.putPiece(myBishop, loc);

        // rightup and leftdown
        for (int i = 0; i < 8; i++) {
            if (i != 3) {
                BoardLocation newLoc = new BoardLocation(i, i);
                assertTrue(myBishop.canMove(loc, newLoc, b));
            }
        }

        //rightdown and leftup
        for (int i = 0; i < 7; i++) {
            if (i != 3) {
                BoardLocation newLoc = new BoardLocation(i, 6 - i); // 6 b there is no center of 8x8 board
                assertTrue(myBishop.canMove(loc, newLoc, b));
            }
        }

        // right and left
        for (int i = 0; i < 8; i++) {
            if (i == 3) continue;
            BoardLocation newLoc = new BoardLocation(i, 3);
            assertFalse(myBishop.canMove(loc, newLoc, b));
        }

        // up and down
        for (int i = 0; i < 8; i++) {
            if (i == 3) continue;
            BoardLocation newLoc = new BoardLocation(3, i);
            assertFalse(myBishop.canMove(loc, newLoc, b));
        }
    }

    @Test
    public void testStuckBishop() {
        Board b = new Board(new BoardLocation(1, 1), null);

        Bishop stuckBishop = new Bishop(this.p1);
        BoardLocation loc = new BoardLocation(0, 0);
        b.putPiece(stuckBishop, loc);

        assertEquals(0, stuckBishop.allPotentialMoves(loc, b).size());
    }
}
