package com.kotikom.chess.model.piece.impl;

import com.kotikom.chess.model.core.Board;
import com.kotikom.chess.model.core.Square;
import com.kotikom.chess.model.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private boolean wasMoved;

    public Pawn(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
    }

    @Override
    public boolean move(Square fin) {
        boolean b = super.move(fin);
        wasMoved = true;
        return b;
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        List<Square> legalMoves = new ArrayList<>();
        Square[][] board = b.getSquareArray();

        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        int direction = (this.getColor() == 0) ? 1 : -1;

        int forwardY = y + direction;
        if (isInBounds(forwardY, x) && !board[forwardY][x].isOccupied()) {
            legalMoves.add(board[forwardY][x]);

            int doubleForwardY = y + 2 * direction;
            if (!wasMoved && isInBounds(doubleForwardY, x) && !board[doubleForwardY][x].isOccupied()) {
                legalMoves.add(board[doubleForwardY][x]);
            }
        }

        for (int dx : new int[]{-1, 1}) {
            int newX = x + dx;
            int newY = y + direction;
            if (isInBounds(newY, newX) && board[newY][newX].isOccupied()) {
                if (board[newY][newX].getOccupyingPiece().getColor() != this.getColor()) {
                    legalMoves.add(board[newY][newX]);
                }
            }
        }

        return legalMoves;
    }

    private boolean isInBounds(int y, int x) {
        return y >= 0 && y < 8 && x >= 0 && x < 8;
    }
}
