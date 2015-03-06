package com.dpzmick.chess.exceptions;

public class GameNotFinishedException extends Exception {
    public GameNotFinishedException(String error) {
        super(error);
    }
}
