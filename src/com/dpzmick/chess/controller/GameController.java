package com.dpzmick.chess.controller;

import com.dpzmick.chess.exceptions.ActionCannotBeUndoneException;
import com.dpzmick.chess.model.MoveResult;
import com.dpzmick.chess.model.Tournament;
import com.dpzmick.chess.model.board.BoardLocation;
import com.dpzmick.chess.model.game.Game;
import com.dpzmick.chess.model.game.GameMove;
import com.dpzmick.chess.view.GameView;

import java.util.ArrayList;

public class GameController {
    private GameView view;
    private Game model;
    private Tournament tournament;
    private ArrayList<GameMove> lastActions;
    private GameMove lastUndone;

    public GameController(Tournament t) {
        tournament = t;
        lastActions = new ArrayList<GameMove>();
    }

    public void setModel(Game model) {
        this.model = model;
    }

    public void setView(GameView view) {
        this.view = view;
    }

    /**
     * Attempt to move the piece at the from location to the to location, for the game's current player
     *
     * @param from location to move a piece from
     * @param to   location to move a piece to
     */
    public void move(BoardLocation from, BoardLocation to) {
        MoveResult ret = model.canMove(
                model.getBoard(),
                model.getCurrentPlayer(),
                from, to);

        if (ret == MoveResult.VALID) {
            GameMove move = new GameMove(from, to, model);
            move.apply();

            lastActions.add(move);
            lastUndone = null;

            if (view != null) {
                view.enableUndo();
                view.disableRedo();
            }

            checkStatus();
        } else {
            if (view != null) {
                view.displayMoveError(ret);
            }
        }
    }

    public void undo() {
        if (lastActions.size() > 0) {
            try {
                GameMove lastAction = lastActions.get(lastActions.size() - 1);
                lastActions.remove(lastAction);
                lastAction.undo();
                lastUndone = lastAction;
            } catch (ActionCannotBeUndoneException e) {
                throw new Error("this shouldn't have happened");
            }

            if (view != null) {
                // to allow multiple undo, change this
                view.disableUndo();
                view.enableRedo();
            }
        }
    }

    public void redo() {
        if (lastUndone != null) {
            lastUndone.apply();

            if (view != null) {
                view.enableUndo();
                view.disableRedo();
            }
        }
    }

    public void highlightPossibleMoves(BoardLocation from) {
        model.highlightPossibleMoves(from);
    }

    private void checkStatus() {
        if (view != null) {
            if (model.inCheckMate(model.getCurrentPlayer())) {
                view.alertInCheckMate(model.getCurrentPlayer());
                tournament.refreshScores();
            }

            if (model.inStalemate()) {
                view.alertInStaleMate();
                tournament.refreshScores();
            }

            if (!model.inCheckMate(model.getCurrentPlayer()) && model.inCheck(model.getCurrentPlayer())) {
                view.alertInCheck(model.getCurrentPlayer());
            }
        }
    }

    public void forfeit() {
        model.forfeit(model.getCurrentPlayer());
        tournament.refreshScores();
    }

    public void restart() {
        try {
            GameMove.undoAllMoves(lastActions);
        } catch (ActionCannotBeUndoneException e) {
            throw new Error("this should not have happened");
        }

        lastActions = new ArrayList<GameMove>();
        view.disableUndo();
        view.disableRedo();
    }
}
