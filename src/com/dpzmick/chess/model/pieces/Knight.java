package com.dpzmick.chess.model.pieces;

import com.dpzmick.chess.model.Piece;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;

import javax.swing.*;
import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(Player owner) {
        super(owner);
    }

    @Override
    public Kind getKind() {
        return Kind.KNIGHT;
    }

    @Override
    public Icon getPieceIcon() {
        if (this.getOwner().getPlayerColor() == Player.PlayerColor.WHITE) {
            return new ImageIcon("icons/white/knight.png");
        } else {
            return new ImageIcon("icons/black/knight.png");
        }
    }

    @Override
    public ArrayList<BoardLocation> allPotentialMoves(BoardLocation from, Board board) {
        ArrayList<BoardLocation> ret = new ArrayList<BoardLocation>();

        for (int x_off : new int[]{-2, -1, 1, 2}) {
            for (int y_off : new int[]{-2, -1, 1, 2}) {
                if (Math.abs(x_off) == Math.abs(y_off)) continue;

                BoardLocation newLoc = new BoardLocation(from, x_off, y_off);

                if (!board.locationOnBoard(newLoc)) continue;

                Piece atLoc = board.getPieceAt(newLoc);
                if (atLoc != null && atLoc.getOwner().equals(this.getOwner())) continue;

                ret.add(newLoc);
            }
        }

        return ret;
    }

}
