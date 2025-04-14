package com.kotikom.chess.model.piece.impl;

import com.kotikom.chess.model.internal.MoveUtils;
import com.kotikom.chess.model.piece.Piece;
import com.kotikom.chess.model.core.Board;
import com.kotikom.chess.model.core.Square;

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

        return MoveUtils.getDiagonalOccupations(board, x, y, getColor());
    }
}
