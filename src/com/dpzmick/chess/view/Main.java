package com.dpzmick.chess.view;

import com.dpzmick.chess.controller.TournamentController;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.Tournament;

import javax.swing.*;

/**
 * The entry point for the gui application
 */
public class Main {
    public static void main(String args[]) {
        String player1Name = JOptionPane.showInputDialog(null,
                "White Player Name?",
                "Input Dialog Box", JOptionPane.INFORMATION_MESSAGE);
        String player2Name = JOptionPane.showInputDialog(null,
                "Black Player Name?",
                "Input Dialog Box", JOptionPane.INFORMATION_MESSAGE);

        Tournament model = new Tournament(
                new Player(Player.PlayerColor.WHITE, player1Name),
                new Player(Player.PlayerColor.BLACK, player2Name), null);

        TournamentController cont = new TournamentController(model);
        TournamentView v = new TournamentView(cont);
        model.setView(v);

        v.setVisible(true);
    }
}