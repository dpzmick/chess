package com.dpzmick.chess.model;

import com.dpzmick.chess.model.board.Board;
import com.dpzmick.chess.model.board.BoardLocation;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Piece implements Cloneable {

    protected int moveCounter;
    private Player owner;

    public Piece(Player owner) {
        this.moveCounter = 0;
        this.owner = owner;
    }

    /**
     * Gets the kind associated with a Piece
     *
     * @return Piece.Kind
     */
    public abstract Kind getKind();

    /**
     * Gets the unicode string for this piece
     *
     * @return string
     */
    public abstract Icon getPieceIcon();

    /**
     * Returns an ArrayList of all the moves this piece could make
     * <p/>
     * Assumes from is a valid location on the board
     * Assumes that the piece is actually at from on the given board
     *
     * @param from  the location to trying moving from
     * @param board the board to try moving on
     * @return ArrayList of BoardLocations this piece can reach following its rules
     */
    public abstract ArrayList<BoardLocation> allPotentialMoves(BoardLocation from, Board board);

    /**
     * Can this piece make this move?
     * <p/>
     * Assumes from is a valid location on the board
     * Assumes that the piece is actually at from on the given board
     *
     * @param from Where to move the piece from
     * @param to   Where to move the piece to
     * @param on   The board to attempt the move on
     * @return boolean indicating the possibility of making this move
     */
    public boolean canMove(BoardLocation from, BoardLocation to, Board on) {
        // all possible moves makes this easy, but hinders error reporting
        // also performance is probably not that great but I don't care unless I find that this is a problem.
        ArrayList<BoardLocation> allPossible = this.allPotentialMoves(from, on);
        return allPossible != null && this.allPotentialMoves(from, on).contains(to);
    }

    /**
     * returns ArrayList of all possible locations a piece can get to by going direction D
     * <p/>
     * Stops moving when it hits another piece,
     * if the piece is not owned by this piece's owner, count that space as a valid space to move to
     * else do not count the space
     * <p/>
     * Does not include the starting position
     *
     * @param d     direction to move
     * @param from  where to move from
     * @param board the board to move on
     * @param lst   lst to add locations to
     */
    protected void addUntilBlocked(Direction d, BoardLocation from, Board board, ArrayList<BoardLocation> lst) {
        int xoffset = 0;
        int yoffest = 0;
        switch (d) {
            case LEFT:
                xoffset = -1;
                yoffest = 0;
                break;
            case RIGHT:
                xoffset = 1;
                yoffest = 0;
                break;
            case UP:
                xoffset = 0;
                yoffest = 1;
                break;
            case DOWN:
                xoffset = 0;
                yoffest = -1;
                break;
            case LEFTUP:
                xoffset = -1;
                yoffest = 1;
                break;
            case RIGHTUP:
                xoffset = 1;
                yoffest = 1;
                break;
            case LEFTDOWN:
                xoffset = -1;
                yoffest = -1;
                break;
            case RIGHTDOWN:
                xoffset = 1;
                yoffest = -1;
                break;
        }

        while (true) {
            BoardLocation move = new BoardLocation(from, xoffset, yoffest);

            // break if we went off the board
            if (!board.locationOnBoard(move)) break;

            // check for a piece here
            Piece atMoveLoc = board.getPieceAt(move);
            if (atMoveLoc != null) {
                // we are done moving
                if (!atMoveLoc.getOwner().equals(this.getOwner())) {
                    lst.add(move);
                }
                break;
            }

            lst.add(move);

            switch (d) {
                case LEFT:
                    xoffset--;
                    break;
                case RIGHT:
                    xoffset++;
                    break;
                case UP:
                    yoffest++;
                    break;
                case DOWN:
                    yoffest--;
                    break;
                case LEFTUP:
                    xoffset--;
                    yoffest++;
                    break;
                case RIGHTUP:
                    xoffset++;
                    yoffest++;
                    break;
                case LEFTDOWN:
                    xoffset--;
                    yoffest--;
                    break;
                case RIGHTDOWN:
                    xoffset++;
                    yoffest--;
                    break;
            }
        }
    }

    public Player getOwner() {
        return owner;
    }

    /**
     * Tell the piece that it moved (the Game should call this any time a piece is moved on the board)
     */
    public void youMoved() {
        this.moveCounter++;
    }

    public void unmove() {
        this.moveCounter--;
    }

    /**
     * Create a new piece from this piece, copying the pieces owner with the copy constructor
     *
     * @return a new piece
     * @throws CloneNotSupportedException
     */
    public Piece clone() throws CloneNotSupportedException {
        Piece clone = (Piece) super.clone();
        clone.owner = new Player(this.owner);
        clone.moveCounter = this.moveCounter;

        return clone;
    }

    @Override
    public String toString() {
        return "{" + this.getClass() + ": " + this.getOwner() + "}";
    }


    public static enum Kind {KING, QUEEN, ROOK, BISHOP, KNIGHT, ALFIL, KNIGHTRIDER, BEROLINA, PAWN}

    protected enum Direction {LEFT, RIGHT, UP, DOWN, LEFTUP, LEFTDOWN, RIGHTUP, RIGHTDOWN}
}
