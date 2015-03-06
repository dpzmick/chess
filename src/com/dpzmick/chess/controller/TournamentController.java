package com.dpzmick.chess.controller;

import com.dpzmick.chess.model.Tournament;
import com.dpzmick.chess.model.game.Game;
import com.dpzmick.chess.model.game.GameFactory;
import com.dpzmick.chess.view.GameView;

public class TournamentController {
    private int created = 0;
    private Tournament model;

    public TournamentController(Tournament model) {
        this.model = model;
    }

    public void makeNewGame(GameFactory.GameType type) {
        String gameName = "" + type + " game (number " + created + ")";

        GameController gameController = new GameController(model);
        GameView gameView = new GameView(gameController, gameName);
        Game chessModel = GameFactory.makeGame(model.getPlayer1(), model.getPlayer2(), gameView, type, gameName);
        gameController.setModel(chessModel);
        gameView.setVisible(true);

        model.addNewGame(chessModel);

        created++;
    }
}
