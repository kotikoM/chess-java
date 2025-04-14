package com.kotikom.chess;

import com.kotikom.chess.controller.StartMenuController;

import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartMenuController::new);
    }

}
