package com.kotikom.chess.controller;

import com.kotikom.chess.view.GameWindow;
import com.kotikom.chess.view.StartMenuView;

import javax.swing.*;
import java.util.Objects;


public class StartMenuController {
    private final StartMenuView view;

    public StartMenuController() {
        view = new StartMenuView();
        setupEventHandlers();
        view.show();
    }

    private void setupEventHandlers() {
        // Quit button closes window
        view.getQuitButton().addActionListener(e -> view.close());

        // Instructions button shows dialog
        view.getInstrButton().addActionListener(e ->
                JOptionPane.showMessageDialog(
                        view.getStartWindow(),
                        "To begin a new game, input player names\n" +
                                "next to the pieces. Set the clocks and\n" +
                                "click \"Start\". Setting the timer to all\n" +
                                "zeroes begins a new untimed game.",
                        "How to play",
                        JOptionPane.PLAIN_MESSAGE)
        );

        // Start button begins game
        view.getStartButton().addActionListener(e -> startNewGame());
    }

    private void startNewGame() {
        try {
            String blackName = view.getBlackInput().getText();
            String whiteName = view.getWhiteInput().getText();
            int hours = Integer.parseInt(
                    (String) Objects.requireNonNull(view.getHours().getSelectedItem()));
            int minutes = Integer.parseInt(
                    (String) Objects.requireNonNull(view.getMinutes().getSelectedItem()));
            int seconds = Integer.parseInt(
                    (String) Objects.requireNonNull(view.getSeconds().getSelectedItem()));

            // Launch game window
            new GameWindow(blackName, whiteName, hours, minutes, seconds);
            view.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    view.getStartWindow(),
                    "Invalid game settings",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
