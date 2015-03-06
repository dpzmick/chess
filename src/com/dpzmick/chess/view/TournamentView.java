package com.dpzmick.chess.view;

import com.dpzmick.chess.controller.TournamentController;
import com.dpzmick.chess.model.game.Game;
import com.dpzmick.chess.model.game.GameFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TournamentView implements ActionListener {
    private final JPanel contentPanel;
    private ButtonGroup group;
    private JFrame myFrame;
    private TournamentController controller;
    private ScoreTableModel tableModel;

    public TournamentView(TournamentController controller) {
        this.controller = controller;
        tableModel = new ScoreTableModel();

        myFrame = new JFrame("Chess Game Creator");
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.setLocationRelativeTo(null);

        contentPanel = new JPanel(new BorderLayout());

        addScorePane();
        addMakerPane();

        myFrame.setContentPane(contentPanel);
        myFrame.pack();
        myFrame.setMinimumSize(myFrame.getSize());

    }

    private void addScorePane() {
        JTable scores = new JTable(null, new String[]{"Game Name", "White Score", "Black Score"});
        scores.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(scores);
        scores.setFillsViewportHeight(true);

        contentPanel.add(scrollPane, BorderLayout.NORTH);
    }

    private void addMakerPane() {
        JPanel makerPanel = new JPanel(new GridLayout(2, 1));

        JPanel radioPanel = new JPanel(new GridLayout(0, 1));

        JRadioButton normalButton = new JRadioButton("Normal Game");
        normalButton.setActionCommand("normal");
        normalButton.setSelected(true);
        radioPanel.add(normalButton);

        JRadioButton weirdButton = new JRadioButton("Weird Game");
        weirdButton.setActionCommand("weird");
        radioPanel.add(weirdButton);

        group = new ButtonGroup();
        group.add(normalButton);
        group.add(weirdButton);

        makerPanel.add(radioPanel);

        JButton goButton = new JButton("Make new Game");
        goButton.setActionCommand("make_game");
        goButton.addActionListener(this);
        makerPanel.add(goButton);

        contentPanel.add(makerPanel, BorderLayout.PAGE_END);
    }

    public void setVisible(boolean visible) {
        myFrame.setVisible(visible);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("make_game")) {
            String s = group.getSelection().getActionCommand();

            GameFactory.GameType type = GameFactory.GameType.NORMAL;
            if (s.equals("weird")) {
                type = GameFactory.GameType.WEIRD;
            }
            controller.makeNewGame(type);
        }
    }

    public void addGame(Game g) {
        this.tableModel.addGame(g);
    }

    public void refreshScores() {
        tableModel.fireTableDataChanged();
    }
}
