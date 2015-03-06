package com.dpzmick.chess.model.pieces;

import com.dpzmick.chess.model.Piece;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;

import javax.swing.*;
import java.util.ArrayList;

/**
 * An alfil (or elephant) is a xiangqi piece and fairy chess piece that jumps two squares diagonally.
 * it can leap over intermediate pieces.
 */
public class Alfil extends Piece {
    public Alfil(Player owner) {
        super(owner);
    }

    @Override
    public Kind getKind() {
        return Kind.ALFIL;
    }

    @Override
    public Icon getPieceIcon() {
        if (this.getOwner().getPlayerColor() == Player.PlayerColor.WHITE) {
            return new ImageIcon("icons/white/alfil.png");
        } else {
            return new ImageIcon("icons/black/alfil.png");
        }
    }


    @Override
    public ArrayList<BoardLocation> allPotentialMoves(BoardLocation from, Board board) {
        ArrayList<BoardLocation> ret = new ArrayList<BoardLocation>();

        for (int x : new int[]{-2, 2}) {
            for (int y : new int[]{-2, 2}) {
                BoardLocation dest = new BoardLocation(from, x, y);
                if (!board.locationOnBoard(dest)) continue;

                Piece atLoc = board.getPieceAt(dest);

                if (atLoc == null) {
                    ret.add(dest);
                } else {
                    if (!atLoc.getOwner().equals(this.getOwner())) {
                        ret.add(dest);
                    }
                }
            }
        }


        return ret;
    }
}
