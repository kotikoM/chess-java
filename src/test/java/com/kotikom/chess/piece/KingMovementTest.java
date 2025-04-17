package com.kotikom.chess.piece;

import com.kotikom.chess.model.core.Square;
import com.kotikom.chess.model.piece.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KingMovementTest extends PieceMovementFixture {
    @Test
    void kingInitialPosition_ShouldHaveNoLegalMoves() {
        Piece king = getSquare(4, 0).getOccupyingPiece();
        assertEquals(0, countLegalMoves(king),
                "King should have no legal moves from starting position");
    }

    @Test
    void kingMiddleOfEmptyBoard_ShouldHave8LegalMoves() {
        Piece king = getSquare(4, 0).getOccupyingPiece();
        Square newPosition = getSquare(3, 3);
        king.move(newPosition);

        assertEquals(8, countLegalMoves(king),
                "King in center of empty board should have 8 legal moves");
    }
}
