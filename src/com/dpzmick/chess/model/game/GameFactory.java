package com.dpzmick.chess.model.game;

import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;
import com.dpzmick.chess.model.pieces.*;
import com.dpzmick.chess.view.GameView;

/**
 * Holds static methods for creating a new chess game
 */
public class GameFactory {
    public static Game makeGame(Player p1, Player p2, GameView view, GameType type, String gameName) {
        switch (type) {
            case NORMAL:
                return GameFactory.makeNormalChess(p1, p2, view, gameName);
            case WEIRD:
                return GameFactory.makeWeirdChess(p1, p2, view, gameName);
        }

        return null;
    }

    /**
     * Creates a normal chess game (8x8, regulation chess rules, two players) with the given players
     *
     * @param p1
     * @param p2
     * @param gameView view to attach to game (can be null)  @return a new instance of a Game
     * @param gameName
     */
    public static Game makeNormalChess(Player p1, Player p2, GameView gameView, String gameName) {
        Board board;
        if (gameView != null) {
            board = Board.normalChessBoard(gameView.getBoardView());
        } else {
            board = Board.normalChessBoard(null);
        }

        // add all the pawns
        int pawnsP1Y = 1;
        int pawnsP2Y = 6;
        for (int i = 0; i < 8; i++) {
            BoardLocation p1PawnLoc = new BoardLocation(i, pawnsP1Y);
            BoardLocation p2PawnLoc = new BoardLocation(i, pawnsP2Y);

            Pawn p1Pawn = new Pawn(p1);
            Pawn p2Pawn = new Pawn(p2);

            board.putPiece(p1Pawn, p1PawnLoc);
            board.putPiece(p2Pawn, p2PawnLoc);
        }

        // y locations for things
        int p1y = 0;
        int p2y = 7;

        // add the rooks
        for (int x : new int[]{0, 7}) {
            BoardLocation p1RookLoc = new BoardLocation(x, p1y);
            BoardLocation p2RookLoc = new BoardLocation(x, p2y);

            Rook p1Rook = new Rook(p1);
            Rook p2Rook = new Rook(p2);

            board.putPiece(p1Rook, p1RookLoc);
            board.putPiece(p2Rook, p2RookLoc);
        }

        // add the horsey
        for (int x : new int[]{1, 6}) {
            BoardLocation p1KnightLoc = new BoardLocation(x, p1y);
            BoardLocation p2KnightLoc = new BoardLocation(x, p2y);

            Knight p1Knight = new Knight(p1);
            Knight p2Knight = new Knight(p2);

            board.putPiece(p1Knight, p1KnightLoc);
            board.putPiece(p2Knight, p2KnightLoc);
        }

        // add the bishops
        for (int x : new int[]{2, 5}) {
            BoardLocation p1BishopLoc = new BoardLocation(x, p1y);
            BoardLocation p2BishopLoc = new BoardLocation(x, p2y);

            Bishop p1Bishop = new Bishop(p1);
            Bishop p2Bishop = new Bishop(p2);

            board.putPiece(p1Bishop, p1BishopLoc);
            board.putPiece(p2Bishop, p2BishopLoc);
        }

        // add king and queen
        King p1King = new King(p1);
        BoardLocation p1KingLoc = new BoardLocation('E', 1);
        board.putPiece(p1King, p1KingLoc);

        Queen p1Queen = new Queen(p1);
        BoardLocation p1QueenLoc = new BoardLocation('D', 1);
        board.putPiece(p1Queen, p1QueenLoc);

        King p2King = new King(p2);
        BoardLocation p2KingLoc = new BoardLocation('E', 8);
        board.putPiece(p2King, p2KingLoc);

        Queen p2Queen = new Queen(p2);
        BoardLocation p2QueenLoc = new BoardLocation('D', 8);
        board.putPiece(p2Queen, p2QueenLoc);

        return new Game(p1, p2, board, gameView, gameName);
    }

    /**
     * Creates a weird chess game, all pawns replaced with BernoliaPawns and bishops changed to elephants
     *
     * @param p1
     * @param p2
     * @param gameView view to attach to game (can be null)  @return new instance of the Game
     * @param gameName
     */
    public static Game makeWeirdChess(Player p1, Player p2, GameView gameView, String gameName) {
        Board board;
        if (gameView != null) {
            board = Board.normalChessBoard(gameView.getBoardView());
        } else {
            board = Board.normalChessBoard(null);
        }

        // add all the BerolinaPawns
        int pawnsP1Y = 1;
        int pawnsP2Y = 6;
        for (int i = 0; i < 8; i++) {
            BoardLocation p1PawnLoc = new BoardLocation(i, pawnsP1Y);
            BoardLocation p2PawnLoc = new BoardLocation(i, pawnsP2Y);

            BerolinaPawn p1Pawn = new BerolinaPawn(p1);
            BerolinaPawn p2Pawn = new BerolinaPawn(p2);

            board.putPiece(p1Pawn, p1PawnLoc);
            board.putPiece(p2Pawn, p2PawnLoc);
        }

        // y locations for things
        int p1y = 0;
        int p2y = 7;

        // add the rooks
        for (int x : new int[]{0, 7}) {
            BoardLocation p1RookLoc = new BoardLocation(x, p1y);
            BoardLocation p2RookLoc = new BoardLocation(x, p2y);

            Rook p1Rook = new Rook(p1);
            Rook p2Rook = new Rook(p2);

            board.putPiece(p1Rook, p1RookLoc);
            board.putPiece(p2Rook, p2RookLoc);
        }

        // add the horsey
        for (int x : new int[]{1, 6}) {
            BoardLocation p1KnightLoc = new BoardLocation(x, p1y);
            BoardLocation p2KnightLoc = new BoardLocation(x, p2y);

            Knight p1Knight = new Knight(p1);
            Knight p2Knight = new Knight(p2);

            board.putPiece(p1Knight, p1KnightLoc);
            board.putPiece(p2Knight, p2KnightLoc);
        }

        // add the Elephants
        for (int x : new int[]{2, 5}) {
            BoardLocation p1AlfilLoc = new BoardLocation(x, p1y);
            BoardLocation p2AlfilLoc = new BoardLocation(x, p2y);

            Alfil p1Alfil = new Alfil(p1);
            Alfil p2Alfil = new Alfil(p2);

            board.putPiece(p1Alfil, p1AlfilLoc);
            board.putPiece(p2Alfil, p2AlfilLoc);
        }

        // add king and queen
        King p1King = new King(p1);
        BoardLocation p1KingLoc = new BoardLocation('E', 1);
        board.putPiece(p1King, p1KingLoc);

        Queen p1Queen = new Queen(p1);
        BoardLocation p1QueenLoc = new BoardLocation('D', 1);
        board.putPiece(p1Queen, p1QueenLoc);

        King p2King = new King(p2);
        BoardLocation p2KingLoc = new BoardLocation('E', 8);
        board.putPiece(p2King, p2KingLoc);

        Queen p2Queen = new Queen(p2);
        BoardLocation p2QueenLoc = new BoardLocation('D', 8);
        board.putPiece(p2Queen, p2QueenLoc);

        return new Game(p1, p2, board, gameView, gameName);
    }

    public enum GameType {NORMAL, WEIRD}
}
