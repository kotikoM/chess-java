package com.kotikom.chess.view;

import com.kotikom.chess.controller.GameWindowController;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class GameWindowView {
    private final JFrame gameWindow;
    private final BoardView boardView;
    private GameWindowController controller;
    private JLabel whiteTimeLabel;
    private JLabel blackTimeLabel;
    private JButton quitButton;
    private JButton newGameButton;
    private JButton instrButton;

    public GameWindowView(String blackName, String whiteName) {
        gameWindow = new JFrame("Chess");
        gameWindow.setLocation(100, 100);
        gameWindow.setLayout(new BorderLayout(20, 20));

        // Initialize UI components
        initializeGameDataPanel(blackName, whiteName);
        this.boardView = new BoardView(this);
        gameWindow.add(boardView, BorderLayout.CENTER);
        gameWindow.add(createButtonPanel(), BorderLayout.SOUTH);

        gameWindow.pack();
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeGameDataPanel(String blackName, String whiteName) {
        JPanel gameData = new JPanel(new GridLayout(3, 2));

        JLabel whiteLabel = createCenterAlignedLabel(whiteName);
        JLabel blackLabel = createCenterAlignedLabel(blackName);
        whiteTimeLabel = createCenterAlignedLabel("Untimed game");
        blackTimeLabel = createCenterAlignedLabel("Untimed game");

        gameData.add(whiteLabel);
        gameData.add(blackLabel);
        gameData.add(whiteTimeLabel);
        gameData.add(blackTimeLabel);

        gameWindow.add(gameData, BorderLayout.NORTH);
    }

    private JLabel createCenterAlignedLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        return label;
    }

    private JPanel createButtonPanel() {
        JPanel buttons = new JPanel(new GridLayout(1, 3, 10, 0));
        instrButton = new JButton("How to play");
        newGameButton = new JButton("New game");
        quitButton = new JButton("Quit");

        buttons.add(instrButton);
        buttons.add(newGameButton);
        buttons.add(quitButton);
        return buttons;
    }

    public void show() {
        gameWindow.setVisible(true);
    }

    public void close() {
        gameWindow.dispose();
    }

    public void handleCheckmate(int c) {
        controller.handleCheckmate(c);
    }
}
