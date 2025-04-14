package com.kotikom.chess.view;

import com.kotikom.chess.model.Square;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;


public class SquareView extends JComponent {
    private final Square square;

    public SquareView(Square square) {
        this.square = square;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (square.getColor() == 1) {
            g.setColor(new Color(221, 192, 127));
        } else {
            g.setColor(new Color(101, 67, 33));
        }

        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        if (square.getOccupyingPiece() != null && square.isDisplayPiece()) {
            square.getOccupyingPiece().draw(g);
        }
    }
}
