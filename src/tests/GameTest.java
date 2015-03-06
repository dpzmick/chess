package tests;

import com.dpzmick.chess.model.MoveResult;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;
import com.dpzmick.chess.model.game.Game;
import com.dpzmick.chess.model.game.GameFactory;
import com.dpzmick.chess.model.pieces.*;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class GameTest {
    private Player p1;
    private Player p2;

    @Before
    public void init() {
        p1 = new Player(Player.PlayerColor.WHITE);
        p2 = new Player(Player.PlayerColor.BLACK);
    }

    @Test
    public void testPlayerSwap() {
        Game g = GameFactory.makeNormalChess(new Player(Player.PlayerColor.WHITE), new Player(Player.PlayerColor.BLACK), null, "");

        assertEquals(p1, g.getCurrentPlayer());

        g.moveToNextPlayer();
        assertEquals(p2, g.getCurrentPlayer());

        g.moveToNextPlayer();
        assertEquals(p1, g.getCurrentPlayer());
    }

    @Test
    public void testInCheckSimple() {
        Board b = Board.normalChessBoard(null);

        King toDie = new King(p1);
        Pawn toKill = new Pawn(p2);

        BoardLocation kingLoc = new BoardLocation(4, 4);
        BoardLocation killerLoc = new BoardLocation(3, 5);

        b.putPiece(toDie, kingLoc);
        b.putPiece(toKill, killerLoc);

        Game g = new Game(p1, p2, b, null, "");

        assertTrue(g.inCheck(p1));

        // we aren't in checkmate though
        assertFalse(g.inCheckMate(p1));
    }

    @Test
    public void testNotInCheck() {
        Board b = Board.normalChessBoard(null);
        King neverDie = new King(p1);
        b.putPiece(neverDie, new BoardLocation(4, 4));

        Game g = new Game(p1, p2, b, null, "");

        assertFalse(g.inCheck(p1));
        assertFalse(g.inCheckMate(p1));
    }

    @Test
    public void testInCheckMateRook() {
        King myKing = new King(p1);
        Rook myRook = new Rook(p1);
        King theirKing = new King(p2);

        BoardLocation myKingLoc = new BoardLocation('F', 5);
        BoardLocation myRookLoc = new BoardLocation('H', 1);
        BoardLocation theirKingLoc = new BoardLocation('H', 5);

        Board b = Board.normalChessBoard(null);
        b.putPiece(myKing, myKingLoc);
        b.putPiece(myRook, myRookLoc);
        b.putPiece(theirKing, theirKingLoc);

        Game g = new Game(p1, p2, b, null, "");
        assertTrue(g.inCheckMate(p2));
    }

    @Test
    public void testRightTriangleMate() {
        King whiteKing = new King(p1);
        BoardLocation whiteKingLoc = new BoardLocation('f', 6);

        King blackKing = new King(p2);
        BoardLocation blackKingLoc = new BoardLocation('h', 6);

        Queen whiteQueen = new Queen(p1);
        BoardLocation whiteQueenLoc = new BoardLocation('h', 1);

        Board b = Board.normalChessBoard(null);
        b.putPiece(whiteKing, whiteKingLoc);
        b.putPiece(blackKing, blackKingLoc);
        b.putPiece(whiteQueen, whiteQueenLoc);

        Game g = new Game(p1, p2, b, null, "");

        assertTrue(g.inCheckMate(p2));
    }

    @Test
    public void testCheckNotMate() {
        // added in discussion
        Board b = Board.normalChessBoard(null);
        King neverDie = new King(p1);
        b.putPiece(neverDie, new BoardLocation(4, 4));

        Queen killMe = new Queen(p2);
        b.putPiece(killMe, new BoardLocation(5, 4));

        Game g = new Game(p1, p2, b, null, "");

        assertTrue(g.inCheck(p1));
        assertFalse(g.inCheckMate(p1));
    }

    @Test
    public void testStalemate() {
        // Korchnoi vs Karpov, 1978
        Pawn whitePawn = new Pawn(p1);
        BoardLocation whitePawnLoc = new BoardLocation('A', 3);

        Pawn blackPawn = new Pawn(p2);
        BoardLocation blackPawnLoc = new BoardLocation('A', 4);

        King whiteKing = new King(p1);
        BoardLocation whiteKingLoc = new BoardLocation('F', 7);

        Bishop whiteBishop = new Bishop(p1);
        BoardLocation whiteBishopLocation = new BoardLocation('G', 7);

        King blackKing = new King(p2);
        BoardLocation blackKingLocation = new BoardLocation('H', 7);

        Board start = Board.normalChessBoard(null);
        start.putPiece(whitePawn, whitePawnLoc);
        start.putPiece(blackPawn, blackPawnLoc);
        start.putPiece(whiteKing, whiteKingLoc);
        start.putPiece(whiteBishop, whiteBishopLocation);
        start.putPiece(blackKing, blackKingLocation);

        assertFalse(blackPawn.canMove(blackPawnLoc, new BoardLocation(0, 4), start));

        Game g = new Game(p1, p2, start, null, "");

        g.moveToNextPlayer(); // make sure p2 is the current player

        assertTrue(g.inStalemate());

    }

    @Test
    public void testSimpleMoves() {
        BoardLocation ploc = new BoardLocation('A', 2);
        Pawn p = new Pawn(p1);

        Board b = Board.normalChessBoard(null);
        b.putPiece(p, ploc);

        Game g = new Game(p1, p2, b, null, "");

        BoardLocation to = new BoardLocation('A', 4);
        assertEquals(MoveResult.VALID, g.canMove(g.getBoard(), p1, ploc, to)); // first move
    }

    @Test
    public void testNoPieceHere() {
        BoardLocation ploc = new BoardLocation('A', 2);
        Pawn p = new Pawn(p1);

        Board b = Board.normalChessBoard(null);
        b.putPiece(p, ploc);

        Game g = new Game(p1, p2, b, null, "");

        BoardLocation from = new BoardLocation('C', 5);
        BoardLocation to = new BoardLocation('A', 4);
        assertEquals(MoveResult.NO_PIECE_HERE, g.canMove(g.getBoard(), p1, from, to)); // first move
    }
}
