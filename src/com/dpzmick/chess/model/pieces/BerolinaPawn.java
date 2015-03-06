package com.dpzmick.chess.model.pieces;

import com.dpzmick.chess.model.Piece;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The Berolina pawn moves, without capturing, one square diagonally forward.
 * It captures one square straight forward
 */
public class BerolinaPawn extends Piece {
    private int offsetMultiplier;

    public BerolinaPawn(Player owner) {
        super(owner);

        if (this.getOwner().getPlayerColor() == Player.PlayerColor.WHITE) {
            offsetMultiplier = 1;
        } else if (this.getOwner().getPlayerColor() == Player.PlayerColor.BLACK) {
            offsetMultiplier = -1;
        }
    }

    @Override
    public Kind getKind() {
        return Kind.BEROLINA;
    }

    @Override
    public Icon getPieceIcon() {
        if (this.getOwner().getPlayerColor() == Player.PlayerColor.WHITE) {
            return new ImageIcon("icons/white/berolina.png");
        } else {
            return new ImageIcon("icons/black/berolina.png");
        }
    }

    @Override
    public ArrayList<BoardLocation> allPotentialMoves(BoardLocation from, Board board) {
        ArrayList<BoardLocation> ret = new ArrayList<BoardLocation>();

        tryDiagonal(1, from, board, ret);
        tryDiagonal(-1, from, board, ret);

        attack(new BoardLocation(from, 0, offsetMultiplier), board, ret);
        return ret;
    }

    private void tryDiagonal(int direction, BoardLocation from, Board board, ArrayList<BoardLocation> ret) {
        BoardLocation loc1 = new BoardLocation(from, direction * offsetMultiplier, offsetMultiplier);
        if (board.locationOnBoard(loc1) && board.getPieceAt(loc1) == null) {
            ret.add(loc1);
        }

        BoardLocation loc2 = new BoardLocation(from, direction * 2 * offsetMultiplier, 2 * offsetMultiplier);
        if (board.locationOnBoard(loc2)
                && board.getPieceAt(loc1) == null // IMPORTANT: a pawn cannot jump!
                && board.getPieceAt(loc2) == null && this.moveCounter == 0) {
            ret.add(loc2);
        }

    }

    private void attack(BoardLocation boardLocation, Board board, ArrayList<BoardLocation> ret) {
        if (board.locationOnBoard(boardLocation)) {
            Piece otherPiece = board.getPieceAt(boardLocation);
            if (otherPiece != null && !otherPiece.getOwner().equals(this.getOwner()))
                ret.add(boardLocation);
        }
    }
}
