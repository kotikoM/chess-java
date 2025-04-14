package com.kotikom.chess.model;

import com.kotikom.chess.model.piece.Piece;
import com.kotikom.chess.model.piece.impl.*;
import com.kotikom.chess.model.utils.CheckmateDetector;

import java.util.LinkedList;

public class Board {
    private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
    private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
    private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
    private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
    private static final String RESOURCES_WROOK_PNG = "wrook.png";
    private static final String RESOURCES_BROOK_PNG = "brook.png";
    private static final String RESOURCES_WKING_PNG = "wking.png";
    private static final String RESOURCES_BKING_PNG = "bking.png";
    private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
    private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
    private static final String RESOURCES_WPAWN_PNG = "wpawn.png";
    private static final String RESOURCES_BPAWN_PNG = "bpawn.png";
    public final LinkedList<Piece> bPieces;
    public final LinkedList<Piece> wPieces;
    private final Square[][] boardSquares;
    private CheckmateDetector cmd;

    public Board() {
        boardSquares = new Square[8][8];
        bPieces = new LinkedList<>();
        wPieces = new LinkedList<>();

        initializeSquares();
        initializePieces();
    }

    private void initializeSquares() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int color = (row + col) % 2 == 0 ? 1 : 0;
                boardSquares[row][col] = new Square(this, color, col, row);
            }
        }
    }

    private void initializePieces() {
        for (int x = 0; x < 8; x++) {
            boardSquares[1][x].put(new Pawn(0, boardSquares[1][x], RESOURCES_BPAWN_PNG));
            boardSquares[6][x].put(new Pawn(1, boardSquares[6][x], RESOURCES_WPAWN_PNG));
        }

        boardSquares[7][3].put(new Queen(1, boardSquares[7][3], RESOURCES_WQUEEN_PNG));
        boardSquares[0][3].put(new Queen(0, boardSquares[0][3], RESOURCES_BQUEEN_PNG));

        King bk = new King(0, boardSquares[0][4], RESOURCES_BKING_PNG);
        King wk = new King(1, boardSquares[7][4], RESOURCES_WKING_PNG);
        boardSquares[0][4].put(bk);
        boardSquares[7][4].put(wk);

        boardSquares[0][0].put(new Rook(0, boardSquares[0][0], RESOURCES_BROOK_PNG));
        boardSquares[0][7].put(new Rook(0, boardSquares[0][7], RESOURCES_BROOK_PNG));
        boardSquares[7][0].put(new Rook(1, boardSquares[7][0], RESOURCES_WROOK_PNG));
        boardSquares[7][7].put(new Rook(1, boardSquares[7][7], RESOURCES_WROOK_PNG));

        boardSquares[0][1].put(new Knight(0, boardSquares[0][1], RESOURCES_BKNIGHT_PNG));
        boardSquares[0][6].put(new Knight(0, boardSquares[0][6], RESOURCES_BKNIGHT_PNG));
        boardSquares[7][1].put(new Knight(1, boardSquares[7][1], RESOURCES_WKNIGHT_PNG));
        boardSquares[7][6].put(new Knight(1, boardSquares[7][6], RESOURCES_WKNIGHT_PNG));

        boardSquares[0][2].put(new Bishop(0, boardSquares[0][2], RESOURCES_BBISHOP_PNG));
        boardSquares[0][5].put(new Bishop(0, boardSquares[0][5], RESOURCES_BBISHOP_PNG));
        boardSquares[7][2].put(new Bishop(1, boardSquares[7][2], RESOURCES_WBISHOP_PNG));
        boardSquares[7][5].put(new Bishop(1, boardSquares[7][5], RESOURCES_WBISHOP_PNG));


        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                bPieces.add(boardSquares[y][x].getOccupyingPiece());
                wPieces.add(boardSquares[7 - y][x].getOccupyingPiece());
            }
        }

        cmd = new CheckmateDetector(this, wPieces, bPieces, wk, bk);
    }

    public Square[][] getSquareArray() {
        return this.boardSquares;
    }

    public CheckmateDetector getCmd() {
        return cmd;
    }
}
