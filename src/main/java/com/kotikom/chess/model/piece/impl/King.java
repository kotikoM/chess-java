package com.kotikom.chess.model.piece.impl;

import com.kotikom.chess.model.piece.Piece;
import com.kotikom.chess.model.core.Board;
import com.kotikom.chess.model.core.Square;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        List<Square> legalMoves = new ArrayList<>();
        Square[][] board = b.getBoardSquares();

        int x = this.getCurrentSquare().getXNum();
        int y = this.getCurrentSquare().getYNum();

        int[][] kingMoves = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] move : kingMoves) {
            int newX = x + move[1];
            int newY = y + move[0];

            if (newY >= 0 && newY < board.length && newX >= 0 && newX < board[0].length) {
                Square target = board[newY][newX];
                if (!target.isOccupied() || target.getOccupyingPiece().getColor() != this.getColor()) {
                    legalMoves.add(target);
                }
            }
        }

        return legalMoves;
    }
}
