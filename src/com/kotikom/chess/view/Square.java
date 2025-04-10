package com.kotikom.chess.view;

import com.kotikom.chess.piece.Piece;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.BorderFactory;

public class Square extends JComponent {
    private final Board board;
    private final int color;
    private final int xNum;
    private final int yNum;
    private Piece occupyingPiece;
    private boolean displayPiece;

    public Square(Board b, int color, int xNum, int yNum) {
        this.board = b;
        this.color = color;
        this.displayPiece = true;
        this.xNum = xNum;
        this.yNum = yNum;

        this.setBorder(BorderFactory.createEmptyBorder());
    }

    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }

    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }

    public int getXNum() {
        return this.xNum;
    }

    public int getYNum() {
        return this.yNum;
    }

    public void setDisplay(boolean v) {
        this.displayPiece = v;
    }

    public void put(Piece p) {
        this.occupyingPiece = p;
        p.setPosition(this);
    }

    public void removePiece() {
        this.occupyingPiece = null;
    }

    public void capture(Piece p) {
        Piece k = getOccupyingPiece();
        if (k.getColor() == 0) board.bPieces.remove(k);
        if (k.getColor() == 1) board.wPieces.remove(k);
        this.occupyingPiece = p;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (this.color == 1) {
            g.setColor(new Color(221, 192, 127));
        } else {
            g.setColor(new Color(101, 67, 33));
        }

        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        if (occupyingPiece != null && displayPiece) {
            occupyingPiece.draw(g);
        }
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + xNum;
        result = prime * result + yNum;
        return result;
    }
}
