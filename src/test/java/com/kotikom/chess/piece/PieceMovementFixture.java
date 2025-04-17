package com.kotikom.chess.piece;

import com.kotikom.chess.model.core.Board;
import com.kotikom.chess.model.core.Square;
import com.kotikom.chess.model.piece.Piece;
import org.junit.jupiter.api.BeforeEach;


class PieceMovementFixture {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    public Square getSquare(int x, int y) {
        return board.getBoardSquares()[y][x];
    }

    public int countLegalMoves(Piece piece) {
        return piece.getLegalMoves(board).size();
    }

    public boolean isLegalMove(Piece piece, Square target) {
        return piece.getLegalMoves(board).contains(target);
    }
}
