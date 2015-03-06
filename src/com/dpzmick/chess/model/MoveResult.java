package com.dpzmick.chess.model;

/**
 * enum specifying the results of an attempt to move a piece
 */
public enum MoveResult {
    NO_PIECE_HERE, PIECE_CANNOT_MOVE, PLAYER_STILL_IN_CHECK, PLAYER_MOVED_TO_CHECK, VALID, FROM_NOT_ON_BOARD, TO_NOT_ON_BOARD, NOT_PLAYERS_PIECE;

    public static String moveResultErrorString(MoveResult res) {
        switch (res) {
            case NO_PIECE_HERE:
                return "There isn't a piece here!";
            case PIECE_CANNOT_MOVE:
                return "This piece can't make this move.";
            case PLAYER_STILL_IN_CHECK:
                return "You need to move out of check!";
            case PLAYER_MOVED_TO_CHECK:
                return "You can't move into check!";
            case VALID:
                return "valid move";
            case FROM_NOT_ON_BOARD:
                return "You can't move from a location not on the board!";
            case TO_NOT_ON_BOARD:
                return "You can't move off the board!";
            case NOT_PLAYERS_PIECE:
                return "You are trying to move a piece that isn't yours!";
        }

        return null;
    }
}
