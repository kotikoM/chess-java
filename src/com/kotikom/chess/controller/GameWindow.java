package com.kotikom.chess.controller;

import com.kotikom.chess.model.utils.Clock;
import com.kotikom.chess.view.BoardView;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.*;


public class GameWindow {
    private final JFrame gameWindow;
    private final BoardView boardView;
    private final Clock blackClock;
    private final Clock whiteClock;
    private Timer timer;


    public GameWindow(String blackName, String whiteName, int hh, int mm, int ss) {
        blackClock = new Clock(hh, ss, mm);
        whiteClock = new Clock(hh, ss, mm);
        gameWindow = new JFrame("Chess");

        try {
            Image whiteImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/wp.png")));
            gameWindow.setIconImage(whiteImg);
        } catch (Exception e) {
            System.out.println("model.Game file wp.png not found");
        }

        gameWindow.setLocation(100, 100);
        gameWindow.setLayout(new BorderLayout(20, 20));

        // model.Game Data window
        JPanel gameData = gameDataPanel(blackName, whiteName, hh, mm, ss);
        gameData.setSize(gameData.getPreferredSize());
        gameWindow.add(gameData, BorderLayout.NORTH);

        this.boardView = new BoardView(this);

        gameWindow.add(boardView, BorderLayout.CENTER);
        gameWindow.add(buttons(), BorderLayout.SOUTH);
        gameWindow.setMinimumSize(gameWindow.getPreferredSize());
        gameWindow.setSize(gameWindow.getPreferredSize());
        gameWindow.setResizable(false);
        gameWindow.pack();
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    private JPanel gameDataPanel(final String bn, final String wn, final int hh, final int mm, final int ss) {
        JPanel gameData = new JPanel();
        gameData.setLayout(new GridLayout(3, 2, 0, 0));

        JLabel w = new JLabel(wn);
        JLabel b = new JLabel(bn);

        w.setHorizontalAlignment(JLabel.CENTER);
        w.setVerticalAlignment(JLabel.CENTER);
        b.setHorizontalAlignment(JLabel.CENTER);
        b.setVerticalAlignment(JLabel.CENTER);

        w.setSize(w.getMinimumSize());
        b.setSize(b.getMinimumSize());

        gameData.add(w);
        gameData.add(b);

        final JLabel bTime = new JLabel(blackClock.getTime());
        final JLabel wTime = new JLabel(whiteClock.getTime());

        bTime.setHorizontalAlignment(JLabel.CENTER);
        bTime.setVerticalAlignment(JLabel.CENTER);
        wTime.setHorizontalAlignment(JLabel.CENTER);
        wTime.setVerticalAlignment(JLabel.CENTER);

        if (!(hh == 0 && mm == 0 && ss == 0)) {
            timer = new Timer(1000, null);
            timer.addActionListener(e -> {
                boolean turn = boardView.getTurn();

                if (turn) {
                    whiteClock.decrementSeconds();
                    wTime.setText(whiteClock.getTime());

                    if (whiteClock.outOfTime()) {
                        timer.stop();
                        int n = JOptionPane.showConfirmDialog(
                                gameWindow,
                                bn + " wins by time! Play a new game? \n" +
                                        "Choosing \"No\" quits the game.",
                                bn + " wins!",
                                JOptionPane.YES_NO_OPTION);

                        if (n == JOptionPane.YES_OPTION) {
                            new GameWindow(bn, wn, hh, mm, ss);
                            gameWindow.dispose();
                        } else gameWindow.dispose();
                    }
                } else {
                    blackClock.decrementSeconds();
                    bTime.setText(blackClock.getTime());

                    if (blackClock.outOfTime()) {
                        timer.stop();
                        int n = JOptionPane.showConfirmDialog(
                                gameWindow,
                                wn + " wins by time! Play a new game? \n" +
                                        "Choosing \"No\" quits the game.",
                                wn + " wins!",
                                JOptionPane.YES_NO_OPTION);

                        if (n == JOptionPane.YES_OPTION) {
                            new GameWindow(bn, wn, hh, mm, ss);
                            gameWindow.dispose();
                        } else gameWindow.dispose();
                    }
                }
            });
            timer.start();
        } else {
            wTime.setText("Untimed game");
            bTime.setText("Untimed game");
        }

        gameData.add(wTime);
        gameData.add(bTime);

        gameData.setPreferredSize(gameData.getMinimumSize());

        return gameData;
    }

    private JPanel buttons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 3, 10, 0));

        final JButton quitButton = getQuitButton();
        final JButton newGameButton = getNewGameButton();
        final JButton instr = getInstrButton();

        buttons.add(instr);
        buttons.add(newGameButton);
        buttons.add(quitButton);

        buttons.setPreferredSize(buttons.getMinimumSize());

        return buttons;
    }

    private JButton getInstrButton() {
        final JButton instr = new JButton("How to play");

        instr.addActionListener(e -> JOptionPane.showMessageDialog(gameWindow,
                """
                        Move the chess pieces on the board by clicking
                        and dragging. The game will watch out for illegal
                        moves. You can win either by your opponent running
                        out of time or by checkmating your opponent.

                        Good luck, hope you enjoy the game!""",
                "How to play",
                JOptionPane.PLAIN_MESSAGE));
        return instr;
    }

    private JButton getNewGameButton() {
        final JButton nGame = new JButton("New game");

        nGame.addActionListener(e -> {
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "Are you sure you want to begin a new game?",
                    "Confirm new game", JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        });
        return nGame;
    }

    private JButton getQuitButton() {
        final JButton quit = new JButton("Quit");

        quit.addActionListener(e -> {
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "Are you sure you want to quit?",
                    "Confirm quit", JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                if (timer != null) timer.stop();
                gameWindow.dispose();
            }
        });
        return quit;
    }

    public void checkmateOccurred(int c) {
        if (c == 0) {
            if (timer != null) timer.stop();
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "White wins by checkmate! Set up a new game? \n" +
                            "Choosing \"No\" lets you look at the final situation.",
                    "White wins!",
                    JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        } else {
            if (timer != null) timer.stop();
            int n = JOptionPane.showConfirmDialog(
                    gameWindow,
                    "Black wins by checkmate! Set up a new game? \n" +
                            "Choosing \"No\" lets you look at the final situation.",
                    "Black wins!",
                    JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                SwingUtilities.invokeLater(new StartMenu());
                gameWindow.dispose();
            }
        }
    }
}
