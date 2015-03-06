package com.dpzmick.chess.model.board;

import com.dpzmick.chess.model.Piece;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.view.BoardPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A data structure to hold the state of a chess game
 * <p/>
 * Maps board locations to pieces, and provides a handful of functions to access the data
 */
public class Board {

    private HashMap<BoardLocation, Piece> data;
    private BoardLocation max;
    private BoardLocation min;
    private BoardPanel view;

    /**
     * Copies the given board.
     * <p/>
     * DOES NOT copy the pieces, uses the same instances as the ones stored in the existing board
     * DOES NOT keep a reference to the view
     *
     * @param board board to copy
     */
    public Board(Board board) {
        this.min = board.min;
        this.max = board.max;
        this.view = null;

        this.data = new HashMap<BoardLocation, Piece>(board.data);
    }

    /**
     * Creates a new board with maximum size
     * <p/>
     * anything with x or y greater than or equal to the given coord is not on the board
     * <p/>
     * Minimum size is set to the BoardLocation (0,0)
     *
     * @param maximalLocation far right boundary
     * @param view            the view to attach to the board (can be null)
     */
    public Board(BoardLocation maximalLocation, BoardPanel view) {
        this.data = new HashMap<BoardLocation, Piece>();
        this.max = maximalLocation;
        this.min = new BoardLocation(0, 0);
        this.view = view;
    }

    /**
     * Creates an 8x8 chess board
     *
     * @return new chess board
     */
    public static Board normalChessBoard(BoardPanel view) {
        return new Board(new BoardLocation(8, 8), view);
    }

    /**
     * @param loc location on board to query
     * @return null if nothing at this location (or if the location is off the board)
     */
    public Piece getPieceAt(BoardLocation loc) {
        return data.get(loc);
    }

    /**
     * removes the piece at loc from the board
     * <p/>
     * throws IllegalArgumentException if the location is not on the board
     *
     * @param loc piece to remove
     */
    public void removePieceAt(BoardLocation loc) throws IllegalArgumentException {
        if (!this.locationOnBoard(loc)) throw new IllegalArgumentException("the location is not on the board");
        data.put(loc, null);

        if (view != null) {
            view.removePieceAt(loc);
        }
    }

    /**
     * puts piece at loc on the board
     * <p/>
     * throws IllegalArgumentException if the location is not on the board
     *
     * @param piece piece to place
     * @param loc   location to place piece
     */
    public void putPiece(Piece piece, BoardLocation loc) throws IllegalArgumentException {
        if (!this.locationOnBoard(loc)) throw new IllegalArgumentException("the location is not on the board");
        if (this.getPieceAt(loc) != null) throw new IllegalArgumentException("the location already has a piece");
        data.put(loc, piece);

        if (view != null) {
            view.putPiece(piece, loc);
        }
    }

    /**
     * @return An array of all the pieces on the board
     */
    public Piece[] allPiecesOnBoard() {
        ArrayList<Piece> ret = new ArrayList<Piece>();
        for (Map.Entry<BoardLocation, Piece> el : this.data.entrySet()) {
            if (el.getValue() != null) {
                ret.add(el.getValue());
            }
        }

        return ret.toArray(new Piece[ret.size()]);
    }

    /**
     * @param player Player to find pieces for
     * @return Array of all pieces owned by this player on the board
     */
    public Piece[] getPiecesFor(Player player) {
        ArrayList<Piece> ret = new ArrayList<Piece>();
        for (Piece p : this.allPiecesOnBoard()) {
            if (p.getOwner().equals(player)) {
                ret.add(p);
            }
        }
        return ret.toArray(new Piece[ret.size()]);
        // I don't think the size actually matters, but intellij does
    }

    /**
     * Finds the locations of all pieces of a certain kind owned by a certain player
     *
     * @param kind Kind of piece to look for
     * @param who  PLayer pieces must be owned by
     * @return array of board locations
     */
    public BoardLocation[] findPieceLocations(Piece.Kind kind, Player who) {
        ArrayList<BoardLocation> ret = new ArrayList<BoardLocation>();
        for (Map.Entry<BoardLocation, Piece> el : this.data.entrySet()) {
            Piece p = el.getValue();
            if (p != null && p.getKind() == kind && p.getOwner().equals(who)) {
                ret.add(el.getKey());
            }
        }
        return ret.toArray(new BoardLocation[ret.size()]);
    }

    /**
     * Gets the BoardLocation of a piece
     *
     * @param piece piece to look for
     * @return BoardLocation the piece resides at (or null if the piece is not on the board)
     */
    public BoardLocation getLocationFor(Piece piece) {
        for (Map.Entry<BoardLocation, Piece> el : this.data.entrySet()) {
            Piece p = el.getValue();
            if (p != null && p.equals(piece)) {
                return el.getKey();
            }
        }
        return null;
    }

    /**
     * Checks if the given location is on the board
     *
     * @param loc location to check
     * @return boolean indicating whether or not this is on the board
     */
    public boolean locationOnBoard(BoardLocation loc) {
        // there is not a very good way to use compareTo for this :/
        boolean bigger = loc.getX() >= min.getX() && loc.getY() >= min.getY();
        boolean smaller = loc.getX() < max.getX() && loc.getY() < max.getY();
        return bigger && smaller;
    }
}
