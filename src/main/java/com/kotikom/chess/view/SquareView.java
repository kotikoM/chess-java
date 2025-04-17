package com.kotikom.chess.view;


import com.kotikom.chess.model.core.Square;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class SquareView extends JComponent {
    private final Square square;

    public SquareView(Square square) {
        this.square = square;
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(square.getColor() == 1 ?
                new Color(233, 233, 206) :
                new Color(110, 150, 80));
        g.fillRect(this.getX(), this.getY(), getWidth(), getHeight());

        if (square.isOccupied() && square.isDisplayPiece()) {
            Image pieceImage = square.getOccupyingPiece().getImage();
            int x = (getWidth() - pieceImage.getWidth(null)) / 2;
            int y = (getHeight() - pieceImage.getHeight(null)) / 2;
            g.drawImage(pieceImage, x, y, null);
        }
    }
}
