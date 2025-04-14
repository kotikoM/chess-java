package com.kotikom.chess.model.piece.impl;

import com.kotikom.chess.model.core.Board;
import com.kotikom.chess.model.core.Square;
import com.kotikom.chess.model.internal.MoveUtils;
import com.kotikom.chess.model.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        List<Square> legalMoves = new ArrayList<>();
        Square[][] board = b.getBoardSquares();

        int x = this.getCurrentSquare().getXNum();
        int y = this.getCurrentSquare().getYNum();

        int[] linearOccupations = MoveUtils.getLinearOccupations(board, x, y, getColor());

        for (int i = linearOccupations[0]; i <= linearOccupations[1]; i++) {
            if (i != y) {
                legalMoves.add(board[i][x]);
            }
        }

        for (int i = linearOccupations[2]; i <= linearOccupations[3]; i++) {
            if (i != x) {
                legalMoves.add(board[y][i]);
            }
        }

        legalMoves.addAll(MoveUtils.getDiagonalOccupations(board, x, y, getColor()));

        return legalMoves;
    }
}
