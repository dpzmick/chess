package tests;

import com.dpzmick.chess.model.Piece;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;
import com.dpzmick.chess.model.pieces.Bishop;
import com.dpzmick.chess.model.pieces.Pawn;
import com.dpzmick.chess.model.pieces.Queen;
import com.dpzmick.chess.model.pieces.Rook;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.*;
import static junit.framework.TestCase.assertTrue;

public class BoardTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testBounds() {
        Board b = new Board(new BoardLocation(8, 8), null);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                assertTrue(b.locationOnBoard(new BoardLocation(x, y)));
            }
        }

        // test along the left edge
        for (int y = 0; y < 8; y++) {
            assertFalse(b.locationOnBoard(new BoardLocation(-1, y)));
        }

        // test along the right edge
        for (int y = 0; y < 8; y++) {
            assertFalse(b.locationOnBoard(new BoardLocation(8, y)));
        }

        // test along the bottom edge
        for (int x = 0; x < 8; x++) {
            assertFalse(b.locationOnBoard(new BoardLocation(x, -1)));
        }

        // test along the top edge
        for (int x = 0; x < 8; x++) {
            assertFalse(b.locationOnBoard(new BoardLocation(x, 8)));
        }
    }

    @Test
    public void testAddAndGet() {
        Board b = new Board(new BoardLocation(8, 8), null);
        Piece p = new Pawn(new Player(Player.PlayerColor.BLACK));

        BoardLocation loc = new BoardLocation(1, 1);
        b.putPiece(p, loc);

        assertEquals(p, b.getPieceAt(loc));
        assertEquals(p, b.getPieceAt(new BoardLocation(1, 1))); // should work for new location as well

        Piece overlap = new Pawn(new Player(Player.PlayerColor.BLACK));

        exception.expect(IllegalArgumentException.class);
        b.putPiece(overlap, new BoardLocation(1, 1));
    }

    @Test
    public void testRemovePiece() {
        Board b = new Board(new BoardLocation(8, 8), null);
        Piece p = new Pawn(new Player(Player.PlayerColor.BLACK));

        b.putPiece(p, new BoardLocation(4, 4));

        assertEquals(p, b.getPieceAt(new BoardLocation(4, 4)));

        b.removePieceAt(new BoardLocation(4, 4));

        assertEquals(null, b.getPieceAt(new BoardLocation(4, 4)));

    }

    @Test
    public void testGetOffBoard() {
        Board b = new Board(new BoardLocation(8, 8), null);

        assertNull(b.getPieceAt(new BoardLocation(10, 10)));
    }

    @Test
    public void testRemoveOffBoard() {
        Board b = new Board(new BoardLocation(8, 8), null);

        exception.expect(IllegalArgumentException.class);
        b.removePieceAt(new BoardLocation(10, 10));
    }

    @Test
    public void testPlaceOffBoard() {
        Board b = new Board(new BoardLocation(8, 8), null);

        exception.expect(IllegalArgumentException.class);
        b.putPiece(new Pawn(new Player(Player.PlayerColor.BLACK)), new BoardLocation(100, 100));
    }

    @Test
    public void testGetByPlayer() {
        Board b = new Board(new BoardLocation(8, 8), null);

        Player p1 = new Player(Player.PlayerColor.BLACK);
        Player p2 = new Player(Player.PlayerColor.WHITE);

        Piece w = new Pawn(p1);
        b.putPiece(w, new BoardLocation(1, 0));
        Piece x = new Rook(p1);
        b.putPiece(x, new BoardLocation(2, 0));

        Piece y = new Queen(p2);
        b.putPiece(y, new BoardLocation(3, 0));
        Piece z = new Bishop(p2);
        b.putPiece(z, new BoardLocation(4, 0));

        Piece[] p1s = b.getPiecesFor(p1);
        ArrayList<Piece> p1sa = new ArrayList<Piece>(Arrays.asList(p1s));
        assertTrue(p1sa.contains(w));
        assertTrue(p1sa.contains(x));
        assertFalse(p1sa.contains(y));
        assertFalse(p1sa.contains(z));

        Piece[] p2s = b.getPiecesFor(p2);
        ArrayList<Piece> p2sa = new ArrayList<Piece>(Arrays.asList(p2s));
        assertFalse(p2sa.contains(w));
        assertFalse(p2sa.contains(x));
        assertTrue(p2sa.contains(y));
        assertTrue(p2sa.contains(z));
    }

    @Test
    public void findByKindAndPlayer() {
        Board b = new Board(new BoardLocation(8, 8), null);

        Player p1 = new Player(Player.PlayerColor.BLACK);
        Player p2 = new Player(Player.PlayerColor.WHITE);

        Piece w = new Pawn(p1);
        b.putPiece(w, new BoardLocation(1, 0));
        Piece x = new Rook(p1);
        b.putPiece(x, new BoardLocation(2, 0));

        Piece y = new Queen(p2);
        b.putPiece(y, new BoardLocation(3, 0));
        Piece z = new Bishop(p2);
        b.putPiece(z, new BoardLocation(4, 0));

        BoardLocation[] p1_pawn = b.findPieceLocations(Piece.Kind.PAWN, p1);
        assertEquals(1, p1_pawn.length);
        assertEquals(new BoardLocation(1, 0), p1_pawn[0]);
    }

    @Test
    public void testGetLocationFor() {
        Board b = new Board(new BoardLocation(8, 8), null);
        BoardLocation bloc = new BoardLocation(1, 1);

        Player play = new Player(Player.PlayerColor.BLACK);
        Piece p1 = new Pawn(play);
        Piece p2 = new Pawn(play);

        b.putPiece(p1, bloc);

        assertEquals(bloc, b.getLocationFor(p1));
        assertNull(b.getLocationFor(p2));
    }

    @Test
    public void testCopy() {
        Piece p = new Pawn(new Player(Player.PlayerColor.BLACK));

        Board a = new Board(new BoardLocation(8, 8), null);
        BoardLocation loc = new BoardLocation(1, 1);
        a.putPiece(p, loc);

        Board b = new Board(a);

        assertTrue(b.getPieceAt(loc) == p); // should be the same object
    }
}
