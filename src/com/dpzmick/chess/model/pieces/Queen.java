package com.dpzmick.chess.model.pieces;

import com.dpzmick.chess.model.Piece;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;

import javax.swing.*;
import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(Player owner) {
        super(owner);
    }

    @Override
    public Kind getKind() {
        return Kind.QUEEN;
    }

    @Override
    public Icon getPieceIcon() {
        if (this.getOwner().getPlayerColor() == Player.PlayerColor.WHITE) {
            return new ImageIcon("icons/white/queen.png");
        } else {
            return new ImageIcon("icons/black/queen.png");
        }
    }

    @Override
    public ArrayList<BoardLocation> allPotentialMoves(BoardLocation from, Board board) {
        ArrayList<BoardLocation> ret = new ArrayList<BoardLocation>();
        addUntilBlocked(Direction.UP, from, board, ret);
        addUntilBlocked(Direction.DOWN, from, board, ret);
        addUntilBlocked(Direction.LEFT, from, board, ret);
        addUntilBlocked(Direction.RIGHT, from, board, ret);
        addUntilBlocked(Direction.LEFTUP, from, board, ret);
        addUntilBlocked(Direction.LEFTDOWN, from, board, ret);
        addUntilBlocked(Direction.RIGHTUP, from, board, ret);
        addUntilBlocked(Direction.RIGHTDOWN, from, board, ret);
        return ret;
    }
}
