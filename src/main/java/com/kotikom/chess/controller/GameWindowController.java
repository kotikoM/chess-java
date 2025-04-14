package com.kotikom.chess.controller;

import com.kotikom.chess.model.internal.Clock;
import com.kotikom.chess.view.*;
import javax.swing.*;

public class GameWindowController {
    private final GameWindowView view;
    private final Clock whiteClock;
    private final Clock blackClock;
    private Timer timer;

    public GameWindowController(String blackName, String whiteName, int hh, int mm, int ss) {
        this.view = new GameWindowView(blackName, whiteName);
        this.view.setController(this);
        this.whiteClock = new Clock(hh, mm, ss);
        this.blackClock = new Clock(hh, mm, ss);

        initializeTimer();
        setupEventHandlers();
        view.show();
    }

    private void initializeTimer() {
        if (!(whiteClock.isZero() && blackClock.isZero())) {
            timer = new Timer(1000, e -> updateGameClock());
            timer.start();
        }
    }

    private void updateGameClock() {
        boolean isWhiteTurn = view.getBoardView().getTurn();

        if (isWhiteTurn) {
            whiteClock.decrementSeconds();
            view.getWhiteTimeLabel().setText(whiteClock.getTime());
            checkTimeOut(whiteClock, "Black");
        } else {
            blackClock.decrementSeconds();
            view.getBlackTimeLabel().setText(blackClock.getTime());
            checkTimeOut(blackClock, "White");
        }
    }

    private void checkTimeOut(Clock clock, String winnerName) {
        if (clock.isZero()) {
            timer.stop();
            promptGameEnd(winnerName + " wins by time!", winnerName);
        }
    }

    private void setupEventHandlers() {
        view.getQuitButton().addActionListener(e -> confirmQuit());
        view.getNewGameButton().addActionListener(e -> confirmNewGame());
        view.getInstrButton().addActionListener(e -> showInstructions());
    }

    private void confirmQuit() {
        int choice = JOptionPane.showConfirmDialog(
                view.getGameWindow(),
                "Are you sure you want to quit?",
                "Confirm quit",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            if (timer != null) timer.stop();
            view.close();
        }
    }

    private void confirmNewGame() {
        int choice = JOptionPane.showConfirmDialog(
                view.getGameWindow(),
                "Are you sure you want to begin a new game?",
                "Confirm new game",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            new StartMenuController();
            view.close();
        }
    }

    private void showInstructions() {
        JOptionPane.showMessageDialog(
                view.getGameWindow(),
                "Move the chess pieces on the board by clicking...",
                "How to play",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    public void handleCheckmate(int winnerColor) {
        if (timer != null) timer.stop();
        String winner = (winnerColor == 0) ? "White" : "Black";
        promptGameEnd(winner + " wins by checkmate!", winner);
    }

    private void promptGameEnd(String message, String winner) {
        int choice = JOptionPane.showConfirmDialog(
                view.getGameWindow(),
                message + " Play a new game?",
                winner + " wins!",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            new StartMenuController();
            view.close();
        }
    }
}