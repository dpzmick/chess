package com.dpzmick.chess.model.board;

import com.dpzmick.chess.exceptions.ActionCannotBeUndoneException;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Abstract class representing a change made to the board
 */
public abstract class BoardAction {
    protected Board to;
    protected boolean applied;

    public BoardAction(Board to) {
        this.to = to;
    }

    // TODO clean up boilerplate somehow

    /**
     * Applies all the actions in the given array list, in the order of the list
     *
     * @param actions actions to apply
     */
    public static void applyActionsInOrder(ArrayList<BoardAction> actions) {
        for (BoardAction act : actions) {
            act.apply();
        }
    }

    /**
     * undoes all the actions in the array list, reverse order in the list
     *
     * @param actions actions to undo
     */
    public static void undoAllActionsInReverseOrder(ArrayList<BoardAction> actions) throws ActionCannotBeUndoneException {
        Collections.reverse(actions);
        for (BoardAction act : actions) {
            act.undo();
        }
        Collections.reverse(actions);
    }

    public abstract void apply();

    public abstract void undo() throws ActionCannotBeUndoneException;
}
