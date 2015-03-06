package com.dpzmick.chess.model.pieces;

import com.dpzmick.chess.model.Piece;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;

import javax.swing.*;
import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(Player owner) {
        super(owner);
    }

    @Override
    public Kind getKind() {
        return Kind.BISHOP;
    }

    @Override
    public Icon getPieceIcon() {
        if (this.getOwner().getPlayerColor() == Player.PlayerColor.WHITE) {
            return new ImageIcon("icons/white/bishop.png");
        } else {
            return new ImageIcon("icons/black/bishop.png");
        }
    }

    @Override
    public ArrayList<BoardLocation> allPotentialMoves(BoardLocation from, Board board) {
        ArrayList<BoardLocation> ret = new ArrayList<BoardLocation>();
        addUntilBlocked(Direction.RIGHTUP, from, board, ret);
        addUntilBlocked(Direction.RIGHTDOWN, from, board, ret);
        addUntilBlocked(Direction.LEFTUP, from, board, ret);
        addUntilBlocked(Direction.LEFTDOWN, from, board, ret);
        return ret;
    }
}
