package com.kotikom.chess.piece;

import com.kotikom.chess.model.core.Square;
import com.kotikom.chess.model.piece.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PawnMovementTest extends PieceMovementFixture {
    @Test
    void pawnInitialPosition_ShouldHaveTwoLegalMoves() {
        Piece pawn = getSquare(0, 6).getOccupyingPiece();
        assertEquals(2, countLegalMoves(pawn),
                "Pawn at starting position should have 2 legal moves");
    }

    @Test
    void pawnNotInitialPosition_ShouldHaveOneLegalMove() {
        Piece pawn = getSquare(0, 6).getOccupyingPiece();
        Square newPosition = getSquare(0, 5);
        pawn.move(newPosition);

        assertEquals(1, countLegalMoves(pawn),
                "Pawn not at starting position should have 1 legal move");
    }

    @Test
    void pawnCannotMoveBackwards() {
        Piece pawn = getSquare(0, 6).getOccupyingPiece();

        Square invalidMove = getSquare(0, 7);
        assertFalse(isLegalMove(pawn, invalidMove),
                "Pawn should not be able to move backwards");
    }
}
