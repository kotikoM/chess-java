package com.kotikom.chess.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class StartMenuView {
    private final JFrame startWindow;
    private final JTextField blackInput;
    private final JTextField whiteInput;
    private final JComboBox<String> hours;
    private final JComboBox<String> minutes;
    private final JComboBox<String> seconds;
    private final JButton startButton;
    private final JButton instrButton;
    private final JButton quitButton;

    public StartMenuView() {
        // Main window setup
        startWindow = new JFrame("Chess");
        startWindow.setLocation(300, 100);
        startWindow.setResizable(true);
        startWindow.setSize(300, 320);
        startWindow.getContentPane().setBackground(new Color(240, 240, 240));

        Box components = Box.createVerticalBox();
        startWindow.add(components);

        // Title
        JPanel titlePanel = new JPanel();
        components.add(titlePanel);
        titlePanel.add(new JLabel("Chess"));

        // Black player section
        JPanel blackPanel = new JPanel();
        components.add(blackPanel);
        blackInput = new JTextField("Black", 10);

        try {
            Image blackImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/bp.png")));
            blackPanel.add(new JLabel(new ImageIcon(blackImg)));
        } catch (Exception e) {
            System.out.println("Missing black pawn image");
        }
        blackPanel.add(blackInput);

        // White player section
        JPanel whitePanel = new JPanel();
        components.add(whitePanel);
        whiteInput = new JTextField("White", 10);

        try {
            Image whiteImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/wp.png")));
            whitePanel.add(new JLabel(new ImageIcon(whiteImg)));
            startWindow.setIconImage(whiteImg);
        } catch (Exception e) {
            System.out.println("Missing white pawn image");
        }
        whitePanel.add(whiteInput);

        // Timer dropdowns
        String[] minSecOptions = new String[60];
        for (int i = 0; i < 60; i++) {
            minSecOptions[i] = String.format("%02d", i);
        }

        hours = new JComboBox<>(new String[]{"0", "1", "2", "3"});
        minutes = new JComboBox<>(minSecOptions);
        seconds = new JComboBox<>(minSecOptions);

        JComboBox<?>[] dropdowns = {hours, minutes, seconds};
        for (JComboBox<?> box : dropdowns) {
            box.setBackground(Color.WHITE);
        }

        Box timerBox = Box.createHorizontalBox();
        timerBox.add(new JLabel("Time: "));
        timerBox.add(hours);
        timerBox.add(new JLabel(" h "));
        timerBox.add(minutes);
        timerBox.add(new JLabel(" m "));
        timerBox.add(seconds);
        timerBox.add(new JLabel(" s"));
        components.add(timerBox);
        components.add(Box.createVerticalStrut(20));

        // Buttons
        Box buttonBox = Box.createHorizontalBox();
        startButton = new JButton("Start");
        instrButton = new JButton("Instructions");
        quitButton = new JButton("Quit");

        buttonBox.add(startButton);
        buttonBox.add(Box.createHorizontalStrut(10));
        buttonBox.add(instrButton);
        buttonBox.add(Box.createHorizontalStrut(10));
        buttonBox.add(quitButton);
        components.add(buttonBox);

        startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JFrame getWindow() {
        return startWindow;
    }

    public JTextField getBlackInput() {
        return blackInput;
    }

    public JTextField getWhiteInput() {
        return whiteInput;
    }

    public JComboBox<String> getHours() {
        return hours;
    }

    public JComboBox<String> getMinutes() {
        return minutes;
    }

    public JComboBox<String> getSeconds() {
        return seconds;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getInstrButton() {
        return instrButton;
    }

    public JButton getQuitButton() {
        return quitButton;
    }

    public void show() {
        startWindow.setVisible(true);
    }

    public void close() {
        startWindow.dispose();
    }
}
