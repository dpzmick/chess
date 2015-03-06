package com.dpzmick.chess.model.game;

import com.dpzmick.chess.exceptions.ActionCannotBeUndoneException;
import com.dpzmick.chess.model.Piece;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.BoardAction;
import com.dpzmick.chess.model.board.BoardAdd;
import com.dpzmick.chess.model.board.BoardLocation;
import com.dpzmick.chess.model.board.BoardRemove;

import java.util.ArrayList;
import java.util.Collections;

public class GameMove {
    protected boolean applied = false;
    private Player activeBeforeMove;
    private Game g;
    private ArrayList<BoardAction> boardActions;
    private ArrayList<Piece> addedToPlayersGraveyard;
    private Piece p;

    public GameMove(BoardLocation from, BoardLocation to, Game g) {
        this.g = g;

        boardActions = new ArrayList<BoardAction>();
        addedToPlayersGraveyard = new ArrayList<Piece>();

        if (g.getBoard().getPieceAt(to) != null) {
            addedToPlayersGraveyard.add(g.getBoard().getPieceAt(to));
            boardActions.add(new BoardRemove(g.getBoard(), to));
        }

        p = g.getBoard().getPieceAt(from);

        boardActions.add(new BoardRemove(g.getBoard(), from));
        boardActions.add(new BoardAdd(g.getBoard(), p, to));

        activeBeforeMove = g.getCurrentPlayer();
    }

    public static void undoAllMoves(ArrayList<GameMove> moves) throws ActionCannotBeUndoneException {
        Collections.reverse(moves);
        for (GameMove m : moves) {
            m.undo();
        }
        Collections.reverse(moves);
    }

    public void apply() {
        applied = true;
        BoardAction.applyActionsInOrder(boardActions);
        for (Piece p : addedToPlayersGraveyard) {
            g.getGraveyards().get(activeBeforeMove).add(p);
        }
        p.youMoved();
        g.moveToNextPlayer();
    }

    public void undo() throws ActionCannotBeUndoneException {
        if (!applied) throw new ActionCannotBeUndoneException("action has not been applied");
        BoardAction.undoAllActionsInReverseOrder(boardActions);
        for (Piece p : addedToPlayersGraveyard) {
            g.getGraveyards().get(activeBeforeMove).remove(p);
        }
        p.unmove();
        g.moveToLastPlayer(); // TODO this is a hack

        applied = false;
    }
}
