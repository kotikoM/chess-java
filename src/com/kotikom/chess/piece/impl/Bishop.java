package com.kotikom.chess.piece.impl;

import com.kotikom.chess.view.Board;
import com.kotikom.chess.piece.Piece;
import com.kotikom.chess.view.Square;

import java.util.List;

public class Bishop extends Piece {

    public Bishop(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }
    
    @Override
    public List<Square> getLegalMoves(Board b) {
        Square[][] board = b.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        return getDiagonalOccupations(board, x, y);
    }
}
