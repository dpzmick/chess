package com.dpzmick.chess.model.board;

import com.dpzmick.chess.exceptions.ActionCannotBeUndoneException;
import com.dpzmick.chess.model.Piece;

public class BoardAdd extends BoardAction {
    private Piece toAdd;
    private BoardLocation location;

    public BoardAdd(Board to, Piece toAdd, BoardLocation loc) {
        super(to);
        this.toAdd = toAdd;
        this.location = loc;
    }

    @Override
    public void apply() {
        to.putPiece(toAdd, location);
        applied = true;
    }

    @Override
    public void undo() throws ActionCannotBeUndoneException {
        if (!applied) throw new ActionCannotBeUndoneException("action has not been applied");

        to.removePieceAt(location);
        applied = false;
    }
}
