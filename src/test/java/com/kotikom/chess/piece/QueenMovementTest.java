package com.kotikom.chess.piece;

import com.kotikom.chess.model.core.Square;
import com.kotikom.chess.model.piece.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueenMovementTest extends PieceMovementFixture {
    @Test
    void queenInitialPosition_ShouldHaveNoLegalMoves() {
        Piece queen = getSquare(3, 0).getOccupyingPiece();
        assertEquals(0, countLegalMoves(queen),
                "Queen should have no legal moves from starting position");
    }

    @Test
    void queenMiddleOfEmptyBoard_ShouldHave19LegalMoves() {
        Piece queen = getSquare(3, 0).getOccupyingPiece();
        Square newPosition = getSquare(3, 3);
        queen.move(newPosition);

        assertEquals(19, countLegalMoves(queen),
                "Queen in center of empty board should have 27 legal moves");
    }
}
