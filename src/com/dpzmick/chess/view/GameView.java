package com.dpzmick.chess.view;

import com.dpzmick.chess.controller.GameController;
import com.dpzmick.chess.model.MoveResult;
import com.dpzmick.chess.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class GameView implements ActionListener {
    private BoardPanel boardView;
    private GameController controller;

    // swing things
    private JFrame mainWindow;
    private JPanel mainPanel;
    private JLabel currentPlayerLabel;
    private JButton undoButton;
    private JButton redoButton;
    private JButton forfeitButton;
    private JButton restartButton;

    public GameView(GameController controller, String gameTitle) {
        this.controller = controller;
        controller.setView(this);

        mainWindow = new JFrame(gameTitle);
        mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainWindow.setLocationByPlatform(true);

        mainPanel = new JPanel(new BorderLayout());

        boardView = new BoardPanel(controller);
        mainPanel.add(boardView, BorderLayout.CENTER);

        addToolbar();

        mainWindow.add(mainPanel);
        mainWindow.pack();
        mainWindow.setMinimumSize(mainWindow.getSize());
    }

    private void addToolbar() {
        JToolBar toolbar = new JToolBar();

        undoButton = new JButton("undo");
        undoButton.setEnabled(false);
        undoButton.setActionCommand("undo");
        undoButton.addActionListener(this);
        toolbar.add(undoButton);

        redoButton = new JButton("redo");
        redoButton.setEnabled(false);
        redoButton.setActionCommand("redo");
        redoButton.addActionListener(this);
        toolbar.add(redoButton);

        forfeitButton = new JButton("forfeit");
        forfeitButton.setActionCommand("forfeit");
        forfeitButton.addActionListener(this);
        toolbar.add(forfeitButton);

        restartButton = new JButton("Restart Game");
        restartButton.setActionCommand("restart");
        restartButton.addActionListener(this);
        toolbar.add(restartButton);

        toolbar.add(Box.createHorizontalGlue()); // addUntilBlocked the right side of the toolbar

        currentPlayerLabel = new JLabel("current player");
        toolbar.add(currentPlayerLabel);

        mainPanel.add(toolbar, BorderLayout.NORTH);
    }

    public void setCurrentPlayer(String playerName) {
        currentPlayerLabel.setText("Current Player: " + playerName);
        forfeitButton.setText(playerName + " forfeit");
    }

    public void setVisible(boolean visible) {
        this.mainWindow.setVisible(visible);
    }

    public BoardPanel getBoardView() {
        return boardView;
    }

    public void enableUndo() {
        undoButton.setEnabled(true);
    }

    public void disableUndo() {
        undoButton.setEnabled(false);
    }

    public void enableRedo() {
        redoButton.setEnabled(true);
    }

    public void disableRedo() {
        redoButton.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("undo")) {
            controller.undo();
        } else if (e.getActionCommand().equals("redo")) {
            controller.redo();
        } else if (e.getActionCommand().equals("forfeit")) {
            controller.forfeit();
        } else if (e.getActionCommand().equals("restart")) {
            controller.restart();
        }
    }

    public void alertInStaleMate() {
        JOptionPane.showMessageDialog(this.mainWindow,
                "Game is in stalemate",
                "Game over",
                JOptionPane.INFORMATION_MESSAGE);

        this.mainWindow.dispatchEvent(new WindowEvent(this.mainWindow, WindowEvent.WINDOW_CLOSING));
    }

    public void alertInCheckMate(Player player) {
        JOptionPane.showMessageDialog(this.mainWindow,
                "Player " + player.toString() + " is in checkmate!",
                "Game over",
                JOptionPane.INFORMATION_MESSAGE);

        this.mainWindow.dispatchEvent(new WindowEvent(this.mainWindow, WindowEvent.WINDOW_CLOSING));
    }

    public void alertInCheck(Player player) {
        JOptionPane.showMessageDialog(this.mainWindow,
                "Player " + player.toString() + " is in check!",
                "Game over",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void alertForfeit(Player who) {
        JOptionPane.showMessageDialog(this.mainWindow,
                "Game was forfeit by: " + who.toString(),
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE);

        this.mainWindow.dispatchEvent(new WindowEvent(this.mainWindow, WindowEvent.WINDOW_CLOSING));
    }

    public void displayMoveError(MoveResult res) {
        JOptionPane.showMessageDialog(this.mainWindow,
                MoveResult.moveResultErrorString(res),
                "Move error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void delete() {
        this.mainWindow.dispatchEvent(new WindowEvent(this.mainWindow, WindowEvent.WINDOW_CLOSING));
    }
}
