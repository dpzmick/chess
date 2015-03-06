package com.dpzmick.chess.model.board;

import com.dpzmick.chess.exceptions.ActionCannotBeUndoneException;
import com.dpzmick.chess.model.Piece;

public class BoardRemove extends BoardAction {
    private final BoardLocation removalLocation;
    private Piece removedPiece;

    public BoardRemove(Board to, BoardLocation removalLocation) {
        super(to);
        this.removalLocation = removalLocation;
    }

    @Override
    public void apply() {
        removedPiece = to.getPieceAt(removalLocation);
        to.removePieceAt(removalLocation);
        applied = true;
    }

    @Override
    public void undo() throws ActionCannotBeUndoneException {
        if (!applied) throw new ActionCannotBeUndoneException("action has not been applied");

        to.putPiece(removedPiece, removalLocation);
        applied = false;
    }
}
