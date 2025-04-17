package com.kotikom.chess;

import com.kotikom.chess.controller.StartMenuController;
import com.kotikom.chess.view.StartMenuView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class StartMenuTest {
    private StartMenuView view;
    private StartMenuController controller;

    @BeforeEach
    void setUp() {
        view = new StartMenuView();
        controller = new StartMenuController();
    }

    @Test
    void startWindow_ShouldHaveCorrectTitle() {
        assertEquals("Chess", view.getStartWindow().getTitle(),
                "Window title should be 'Chess'");
    }

    @Test
    void startWindow_ShouldHaveCorrectSize() {
        Dimension expectedSize = new Dimension(300, 320);
        assertEquals(expectedSize, view.getStartWindow().getSize(),
                "Window size should be 300x320");
    }

    @Test
    void startWindow_ShouldBeResizable() {
        assertTrue(view.getStartWindow().isResizable(),
                "Window should be resizable");
    }

    @Test
    void startWindow_ShouldHaveCorrectBackgroundColor() {
        Color expectedColor = new Color(240, 240, 240);
        assertEquals(expectedColor, view.getStartWindow().getContentPane().getBackground(),
                "Window background should be light gray");
    }

    @Test
    void playerInputFields_ShouldHaveDefaultValues() {
        assertEquals("Black", view.getBlackInput().getText(),
                "Black player input should default to 'Black'");
        assertEquals("White", view.getWhiteInput().getText(),
                "White player input should default to 'White'");
    }

    @Test
    void timerDropdowns_ShouldHaveCorrectOptions() {
        // Test hours dropdown
        JComboBox<String> hours = view.getHours();
        assertEquals(4, hours.getItemCount(),
                "Hours dropdown should have 4 options (0-3)");
        assertEquals("0", hours.getItemAt(0),
                "First hours option should be '0'");
        assertEquals("3", hours.getItemAt(3),
                "Last hours option should be '3'");

        // Test minutes dropdown
        JComboBox<String> minutes = view.getMinutes();
        assertEquals(60, minutes.getItemCount(),
                "Minutes dropdown should have 60 options");
        assertEquals("00", minutes.getItemAt(0),
                "First minutes option should be '00'");
        assertEquals("59", minutes.getItemAt(59),
                "Last minutes option should be '59'");

        // Test seconds dropdown (same as minutes)
        JComboBox<String> seconds = view.getSeconds();
        assertEquals(60, seconds.getItemCount(),
                "Seconds dropdown should have 60 options");
    }

    @Test
    void buttons_ShouldBePresent() {
        assertNotNull(view.getStartButton(),
                "Start button should exist");
        assertNotNull(view.getInstrButton(),
                "Instructions button should exist");
        assertNotNull(view.getQuitButton(),
                "Quit button should exist");
    }
}
