package com.kotikom.chess;

import com.kotikom.chess.view.BoardView;
import com.kotikom.chess.view.GameWindowView;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {
    private BoardView boardView;

    @BeforeEach
    void setUp() {
        StubGameWindowView gameWindowStub = new StubGameWindowView();
        boardView = new BoardView(gameWindowStub);
    }

    @Test
    void testInitialTurnIsWhite() {
        assertTrue(boardView.getTurn(), "Initial turn should be white");
    }

    @Getter
    static class StubGameWindowView extends GameWindowView {
        private boolean checkmateCalled;
        private int winnerColor;

        public StubGameWindowView() {
            super("black", "white");
        }

        @Override
        public void handleCheckmate(int color) {
            this.checkmateCalled = true;
            this.winnerColor = color;
        }

    }
}
