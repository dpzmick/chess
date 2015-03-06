package com.dpzmick.chess.model;

import com.dpzmick.chess.model.game.Game;
import com.dpzmick.chess.view.TournamentView;

public class Tournament {
    private Player player1;
    private Player player2;
    private TournamentView view;

    public Tournament(Player p1, Player p2, TournamentView view) {
        this.player1 = p1;
        this.player2 = p2;
        this.view = view;
    }

    public void addNewGame(Game g) {
        view.addGame(g);
    }

    public void refreshScores() {
        view.refreshScores();
    }

    public TournamentView getView() {
        return view;
    }

    public void setView(TournamentView view) {
        this.view = view;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
