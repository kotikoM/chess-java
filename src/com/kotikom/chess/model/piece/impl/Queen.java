package com.kotikom.chess.model.piece.impl;

import com.kotikom.chess.model.Board;
import com.kotikom.chess.model.Square;
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
        Square[][] board = b.getSquareArray();

        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();

        int[] linearOccupations = getLinearOccupations(board, x, y);

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

        legalMoves.addAll(getDiagonalOccupations(board, x, y));

        return legalMoves;
    }
}
