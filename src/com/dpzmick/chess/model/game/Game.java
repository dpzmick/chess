package com.dpzmick.chess.model.game;

import com.dpzmick.chess.exceptions.GameNotFinishedException;
import com.dpzmick.chess.model.MoveResult;
import com.dpzmick.chess.model.Piece;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.board.*;
import com.dpzmick.chess.view.GameView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Allows interaction with the game via a couple of public methods
 */
public class Game {
    private Board board;
    private HashMap<Player, ArrayList<Piece>> graveyards;
    private Player[] players;
    private int currentPlayerIndex;
    private GameView view;
    private Player forfeitingPlayer = null;
    private String name;

    /**
     * Creates a new game with the players p1 and p2 and the given starting board
     * The given starting board is deep copied
     *
     * @param p1            Player 1 (will be copied)
     * @param p2            Player 2 (will also be copied)
     * @param startingBoard Starting board (copied)
     * @param view          a view to attach to the game (can be null)
     * @param gameName
     */
    public Game(Player p1, Player p2, Board startingBoard, GameView view, String gameName) {
        board = startingBoard;
        this.name = gameName;

        graveyards = new HashMap<Player, ArrayList<Piece>>();
        graveyards.put(p1, new ArrayList<Piece>());
        graveyards.put(p2, new ArrayList<Piece>());

        players = new Player[2];
        players[0] = p1;
        players[1] = p2;

        currentPlayerIndex = 0;

        this.view = view;

        if (view != null) {
            view.setCurrentPlayer(this.getCurrentPlayer().toString());
        }
    }

    /**
     * Moves to the next player's turn. Changes the game's state
     *
     * @return the player we've switched to
     */
    public Player moveToNextPlayer() {
        this.currentPlayerIndex = this.nextPlayerIndex();

        if (this.view != null) {
            this.view.setCurrentPlayer(this.getCurrentPlayer().toString());
        }

        return this.players[this.currentPlayerIndex];
    }

    /**
     * Moves to the previous player
     *
     * @return previous player
     */
    public Player moveToLastPlayer() {
        return moveToNextPlayer();
    }

    /**
     * gets the games current player
     *
     * @return player who's turn it is currently
     */
    public Player getCurrentPlayer() {
        return this.players[this.currentPlayerIndex];
    }

    /**
     * Gets the next player, without changing the state of the game
     *
     * @return next player
     */
    public Player nextPlayer() {
        return this.players[this.nextPlayerIndex()];
    }

    /**
     * index of the next player (changes no state)
     *
     * @return index of next player
     */
    private int nextPlayerIndex() {
        return (this.currentPlayerIndex + 1) % 2;
    }

    public HashMap<Player, ArrayList<Piece>> getGraveyards() {
        return graveyards;
    }

    /**
     * @param who
     * @return Whether or not the player who is in check
     */
    public boolean inCheck(Player who) {
        // if only we could curry!
        return this.inCheck(this.board, who);
    }

    /**
     * @param who player to check
     * @return Whether or not the player who is in check mate
     */
    public boolean inCheckMate(Player who) {
        if (!inCheck(who))
            return false;

        // for all possible moves of all the player's pieces, check if the player is still in check
        for (Piece p : board.getPiecesFor(who)) {
            BoardLocation pLoc = board.getLocationFor(p);
            for (BoardLocation possible : p.allPotentialMoves(pLoc, board)) {
                if (canMove(this.board, who, pLoc, possible) == MoveResult.VALID) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * If the player who's turn it currently is is not in check and cannot move, return true
     *
     * @return is the game in stalemate
     */
    public boolean inStalemate() {
        if (inCheck(this.getCurrentPlayer())) {
            return false;
        }

        for (Piece p : this.board.getPiecesFor(this.getCurrentPlayer())) {
            BoardLocation currentLoc = this.board.getLocationFor(p);
            ArrayList<BoardLocation> ret = p.allPotentialMoves(currentLoc, this.board);
            for (BoardLocation to : ret) {
                if (canMove(this.board, this.getCurrentPlayer(), currentLoc, to) == MoveResult.VALID) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param board board to check players status on
     * @param who   player to check the status of
     * @return Whether the player who is in check on the board given
     */
    private boolean inCheck(Board board, Player who) {
        // there will always be one king unless something really weird happens
        BoardLocation[] kingLocs = board.findPieceLocations(Piece.Kind.KING, who);
        if (kingLocs.length == 0) return false;

        BoardLocation kingLoc = kingLocs[0];

        for (Player other : this.players) {
            if (other.equals(who)) {
                continue;
            }
            for (Piece enemyPiece : board.getPiecesFor(other)) {
                if (enemyPiece.canMove(board.getLocationFor(enemyPiece), kingLoc, board)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks if a specified move can be made on a given board by a given player
     *
     * @param on   Board to attempt the move on
     * @param who  Who wants to make the move
     * @param from where to move a piece from
     * @param to   where to move a piece to
     * @return A MoveResult indicating how possible it is to move the piece
     */
    public MoveResult canMove(Board on, Player who, BoardLocation from, BoardLocation to) {
        if (!on.locationOnBoard(from)) return MoveResult.FROM_NOT_ON_BOARD;
        if (!on.locationOnBoard(from)) return MoveResult.TO_NOT_ON_BOARD;

        Piece sourcePiece = on.getPieceAt(from);
        if (sourcePiece == null) {
            return MoveResult.NO_PIECE_HERE;
        }

        if (!sourcePiece.getOwner().equals(who)) {
            return MoveResult.NOT_PLAYERS_PIECE;
        }

        if (!sourcePiece.canMove(from, to, on)) {
            return MoveResult.PIECE_CANNOT_MOVE;
        }

        Board newBoard = new Board(on);

        ArrayList<BoardAction> actions = new ArrayList<BoardAction>();

        Piece destinationPiece = on.getPieceAt(to);
        if (destinationPiece != null) {
            actions.add(new BoardRemove(newBoard, to));
        }

        // move piece into new location on new board
        actions.add(new BoardRemove(newBoard, from));
        actions.add(new BoardAdd(newBoard, sourcePiece, to));

        BoardAction.applyActionsInOrder(actions);

        if (this.inCheck(newBoard, who)) {
            if (this.inCheck(on, who)) {
                return MoveResult.PLAYER_STILL_IN_CHECK;
            } else {
                return MoveResult.PLAYER_MOVED_TO_CHECK;
            }
        }

        return MoveResult.VALID;
    }

    /**
     * Changing the state of the returned board will not affect the Game
     *
     * @return the current board
     */
    public Board getBoard() {
        return board;
    }

    public void highlightPossibleMoves(BoardLocation from) {
        if (view != null) {
            Piece source = board.getPieceAt(from);
            if (source != null) {
                for (BoardLocation possible : source.allPotentialMoves(from, board)) {
                    if (canMove(board, getCurrentPlayer(), from, possible) == MoveResult.VALID) {
                        view.getBoardView().highlightSpace(possible);
                    }
                }
            }
        }
    }

    public GameStatus getGameStatus() {
        if (forfeitingPlayer != null) {
            return GameStatus.FORFEIT;
        }

        for (Player p : players) {
            if (inCheckMate(p)) {
                return GameStatus.CHECKMATE;
            }
        }
        return GameStatus.RUNNING;
    }

    public Map<Player, Double> getPlayerScores() throws GameNotFinishedException {
        if (getGameStatus() == GameStatus.RUNNING)
            throw new GameNotFinishedException("this game has not been finished");
        HashMap<Player, Double> scores = new HashMap<Player, Double>();

        if (inStalemate()) {
            for (Player p : players) {
                scores.put(p, 0.5);
            }
            return scores;
        }

        if (forfeitingPlayer != null) {
            for (Player p : players) {
                if (!p.equals(forfeitingPlayer)) {
                    scores.put(p, 1.0);
                } else {
                    scores.put(p, 0.0);
                }
            }
            return scores;
        }

        // otherwise in checkmate
        for (Player p : players) {
            if (inCheckMate(p)) {
                scores.put(p, 0.0);
            } else {
                scores.put(p, 1.0);
            }
        }

        return scores;
    }

    public void forfeit(Player who) {
        forfeitingPlayer = who;

        if (view != null) {
            view.alertForfeit(who);
        }
    }

    public String getName() {
        return name;
    }

    public Player getPlayerN(int n) {
        return players[n - 1];
    }

    public void endGame() {
        if (view != null) {
            view.delete();
        }
    }
}
