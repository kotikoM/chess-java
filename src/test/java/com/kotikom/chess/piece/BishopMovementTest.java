package com.kotikom.chess.piece;

import com.kotikom.chess.model.core.Square;
import com.kotikom.chess.model.piece.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BishopMovementTest extends PieceMovementFixture {


    @Test
    void bishopInitialPosition_ShouldHaveNoLegalMoves() {
        Piece bishop = getSquare(2, 0).getOccupyingPiece();
        assertEquals(0, countLegalMoves(bishop),
                "Bishop should have no legal moves from starting position");
    }

    @Test
    void bishopMiddleOfEmptyBoard_ShouldHave13LegalMoves() {
        Piece bishop = getSquare(2, 0).getOccupyingPiece();
        Square newPosition = getSquare(3, 3);
        bishop.move(newPosition);

        assertEquals(8, countLegalMoves(bishop),
                "Bishop in center of empty board should have 13 legal moves");
    }

    @Test
    void bishopShouldNotJumpOverPieces() {
        Piece bishop = getSquare(5, 7).getOccupyingPiece();

        Square blockedMove = getSquare(3, 5);
        assertFalse(isLegalMove(bishop, blockedMove),
                "Bishop should not be able to jump over pieces");
    }

    @Test
    void bishopCannotCaptureOwnPiece() {
        Piece bishop = getSquare(2, 7).getOccupyingPiece();
        Square newPosition = getSquare(3, 3);
        bishop.move(newPosition);

        Square ownPieceSquare = getSquare(4, 6);
        assertFalse(isLegalMove(bishop, ownPieceSquare),
                "Bishop should not be able to capture own piece");
    }
}
