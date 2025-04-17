package com.kotikom.chess.piece;

import com.kotikom.chess.model.core.Square;
import com.kotikom.chess.model.piece.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RookMovementTest extends PieceMovementFixture {
    @Test
    void rookInitialPosition_ShouldHaveNoLegalMoves() {
        Piece rook = getSquare(0, 0).getOccupyingPiece();
        assertEquals(0, countLegalMoves(rook),
                "Rook should have no legal moves from starting position");
    }

    @Test
    void rookMiddleOfEmptyBoard_ShouldHave11LegalMoves() {
        Piece rook = getSquare(0, 0).getOccupyingPiece();
        Square newPosition = getSquare(3, 3);
        rook.move(newPosition);

        assertEquals(11, countLegalMoves(rook),
                "Rook in center of empty board should have 14 legal moves");
    }

    @Test
    void rookShouldNotJumpOverPieces() {
        Piece rook = getSquare(0, 7).getOccupyingPiece();

        Square blockedMove = getSquare(0, 5);
        assertFalse(isLegalMove(rook, blockedMove),
                "Rook should not be able to jump over pieces");
    }

    @Test
    void rookCanCaptureOpponentPiece() {
        Piece rook = getSquare(0, 7).getOccupyingPiece();
        Square newPosition = getSquare(3, 3);
        rook.move(newPosition);

        Square captureSquare = getSquare(3, 1);
        assertTrue(isLegalMove(rook, captureSquare),
                "Rook should be able to capture opponent's piece");
    }
}
