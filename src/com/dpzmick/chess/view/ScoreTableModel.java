package com.dpzmick.chess.view;

import com.dpzmick.chess.exceptions.GameNotFinishedException;
import com.dpzmick.chess.model.Player;
import com.dpzmick.chess.model.game.Game;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Map;

public class ScoreTableModel extends AbstractTableModel {
    private ArrayList<Game> games;

    public ScoreTableModel() {
        super();
        games = new ArrayList<Game>();
    }

    public void addGame(Game g) {
        games.add(g);
    }

    @Override
    public int getRowCount() {
        return games.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Game g = games.get(rowIndex);

        if (columnIndex == 0) {
            return g.getName();
        } else {
            try {
                Map<Player, Double> scores = g.getPlayerScores();
                if (columnIndex == 1) return scores.get(g.getPlayerN(1));
                if (columnIndex == 2) return scores.get(g.getPlayerN(2));

            } catch (GameNotFinishedException ignore) {
                return "game not finished";
            }
        }

        return null;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Game Name";
            case 1:
                return "White Score";
            case 2:
                return "Black Score";
        }

        return null;
    }
}
