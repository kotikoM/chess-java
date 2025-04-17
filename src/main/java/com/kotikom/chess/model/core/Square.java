package com.kotikom.chess.model.core;

import com.kotikom.chess.model.piece.Piece;
import com.kotikom.chess.view.SquareView;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.BorderFactory;

@Getter
@Setter
public class Square {
    private final Board board;
    private SquareView squareView;
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
    }

    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }


    public void put(Piece p) {
        this.occupyingPiece = p;
        p.setCurrentSquare(this);
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
}
