package com.kotikom.chess.model.piece;

import com.kotikom.chess.model.core.Board;
import com.kotikom.chess.model.core.Square;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public abstract class Piece {
    private final int color;
    private Square currentSquare;
    private BufferedImage image;

    public Piece(int color, Square initSq, String img_file) {
        this.color = color;
        this.currentSquare = initSq;

        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/" + img_file)));
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public boolean move(Square fin) {
        Piece occupyingPiece = fin.getOccupyingPiece();

        if (occupyingPiece != null) {
            if (occupyingPiece.getColor() == this.color) {
                return false;
            } else {
                fin.capture(this);
            }
        }

        currentSquare.removePiece();
        this.currentSquare = fin;
        currentSquare.put(this);
        return true;
    }


    public void draw(Graphics g) {
        g.drawImage(this.image, currentSquare.getXNum(), currentSquare.getYNum(), null);
    }

    public abstract List<Square> getLegalMoves(Board b);
}
