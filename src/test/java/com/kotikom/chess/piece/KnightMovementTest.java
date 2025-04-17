package com.kotikom.chess.piece;

import com.kotikom.chess.model.core.Square;
import com.kotikom.chess.model.piece.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KnightMovementTest extends PieceMovementFixture {
    @Test
    void knightInitialPosition_ShouldHaveTwoLegalMoves() {
        Piece knight = getSquare(1, 0).getOccupyingPiece();
        assertEquals(2, countLegalMoves(knight),
                "Knight at starting position should have 2 legal moves");
    }

    @Test
    void knightCanJumpOverPieces() {
        Piece knight = getSquare(6, 7).getOccupyingPiece();

        Square validMove = getSquare(5, 5);
        assertTrue(isLegalMove(knight, validMove),
                "Knight should be able to jump over pieces");
    }
}
