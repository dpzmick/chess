package com.dpzmick.chess.model.board;

/**
 * Represents a location on the game board
 * <p/>
 * These objects are immutable
 */
final public class BoardLocation {
    final private int x;
    final private int y;

    public BoardLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public BoardLocation(char chessFirst, int chessSecond) {
        this(Character.toLowerCase(chessFirst) - 'a', chessSecond - 1);
    }

    public BoardLocation(BoardLocation old, int x_off, int y_off) {
        this.x = old.x + x_off;
        this.y = old.y + y_off;
    }

    public BoardLocation(BoardLocation other) {
        this.x = other.x;
        this.y = other.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) throw new NullPointerException();
        if (!(o instanceof BoardLocation)) return false;

        BoardLocation other = (BoardLocation) o;
        return (this.getX() == other.getX()) && (this.getY() == other.getY());
    }

    @Override
    // TODO this might be a hack
    public int hashCode() {
        return this.getX() * 1000 + this.getY();
    }

    @Override
    public String toString() {
        return "BoardLocation(" + this.getX() + ", " + this.getY() + ")";
    }
}
