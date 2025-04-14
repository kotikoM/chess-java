package com.kotikom.chess.model.piece;

import com.kotikom.chess.model.core.Board;
import com.kotikom.chess.model.core.Square;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class Piece {
    private final int color;
    private Square currentSquare;
    private BufferedImage img;

    public Piece(int color, Square initSq, String img_file) {
        this.color = color;
        this.currentSquare = initSq;

        try {
            this.img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/" + img_file)));
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

    public Square getPosition() {
        return currentSquare;
    }

    public void setPosition(Square sq) {
        this.currentSquare = sq;
    }

    public int getColor() {
        return color;
    }

    public Image getImage() {
        return img;
    }

    public void draw(Graphics g) {
        g.drawImage(this.img, currentSquare.getX(), currentSquare.getY(), null);
    }

    public abstract List<Square> getLegalMoves(Board b);
}
