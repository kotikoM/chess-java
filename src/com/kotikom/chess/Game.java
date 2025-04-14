package com.kotikom.chess;

import com.kotikom.chess.controller.StartMenu;

import javax.swing.*;

public class Game implements Runnable {
    public void run() {
        SwingUtilities.invokeLater(new StartMenu());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
