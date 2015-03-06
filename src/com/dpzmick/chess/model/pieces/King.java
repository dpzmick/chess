package com.dpzmick.chess.model.pieces;

import com.dpzmick.chess.model.Piece;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;

import javax.swing.*;
import java.util.ArrayList;

public class King extends Piece {
    public King(Player owner) {
        super(owner);
    }

    @Override
    public Kind getKind() {
        return Kind.KING;
    }

    @Override
    public Icon getPieceIcon() {
        if (this.getOwner().getPlayerColor() == Player.PlayerColor.WHITE) {
            return new ImageIcon("icons/white/king.png");
        } else {
            return new ImageIcon("icons/black/king.png");
        }
    }

    @Override
    public ArrayList<BoardLocation> allPotentialMoves(BoardLocation from, Board board) {
        ArrayList<BoardLocation> ret = new ArrayList<BoardLocation>();

        for (int x_off : new int[]{-1, 0, 1}) {
            for (int y_off : new int[]{-1, 0, 1}) {
                if (x_off == 0 && y_off == 0) continue;

                BoardLocation dest = new BoardLocation(from, x_off, y_off);
                if (!board.locationOnBoard(dest)) continue;

                Piece atDest = board.getPieceAt(dest);
                if (atDest == null) {
                    ret.add(dest);
                } else {
                    if (!(atDest.getOwner().equals(this.getOwner()))) {
                        ret.add(dest);
                    }
                }
            }
        }

        return ret;
    }
}
