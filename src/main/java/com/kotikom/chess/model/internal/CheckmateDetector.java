package com.kotikom.chess.model.internal;

import com.kotikom.chess.model.core.Board;
import com.kotikom.chess.model.core.Square;
import com.kotikom.chess.model.piece.Piece;
import com.kotikom.chess.model.piece.impl.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CheckmateDetector {
    private final Board board;
    private final King whiteKing;
    private final King blackKing;
    private final List<Piece> whitePieces;
    private final List<Piece> blackPieces;
    private final Map<Square, List<Piece>> whiteThreats = new HashMap<>();
    private final Map<Square, List<Piece>> blackThreats = new HashMap<>();
    private final List<Square> movableSquares = new ArrayList<>();

    public CheckmateDetector(Board board, List<Piece> whitePieces, List<Piece> blackPieces,
                             King whiteKing, King blackKing) {
        this.board = board;
        this.whitePieces = new ArrayList<>(whitePieces);
        this.blackPieces = new ArrayList<>(blackPieces);
        this.whiteKing = whiteKing;
        this.blackKing = blackKing;
        initializeThreatMaps();
    }

    private void initializeThreatMaps() {
        Square[][] squares = board.getBoardSquares();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                whiteThreats.put(squares[y][x], new ArrayList<>());
                blackThreats.put(squares[y][x], new ArrayList<>());
            }
        }
        update();
    }

    public void update() {
        clearThreatMaps();
        updateThreats(whitePieces, whiteThreats);
        updateThreats(blackPieces, blackThreats);
    }

    private void clearThreatMaps() {
        whiteThreats.values().forEach(List::clear);
        blackThreats.values().forEach(List::clear);
    }

    private void updateThreats(List<Piece> pieces, Map<Square, List<Piece>> threatMap) {
        pieces.removeIf(p -> p.getCurrentSquare() == null);

        for (Piece piece : pieces) {
            if (piece instanceof King) continue;

            for (Square move : piece.getLegalMoves(board)) {
                threatMap.get(move).add(piece);
            }
        }
    }

    public boolean isWhiteInCheck() {
        return !blackThreats.get(whiteKing.getCurrentSquare()).isEmpty();
    }

    public boolean isBlackInCheck() {
        return !whiteThreats.get(blackKing.getCurrentSquare()).isEmpty();
    }

    public boolean whiteCheckMated() {
        if (!isWhiteInCheck()) return false;

        return !canKingMove(whiteKing, blackThreats) &&
                !canCaptureThreat(whitePieces, blackThreats.get(whiteKing.getCurrentSquare())) &&
                !canBlockThreat(whitePieces, blackThreats.get(whiteKing.getCurrentSquare()));
    }

    public boolean blackCheckMated() {
        if (!isBlackInCheck()) return false;

        return !canKingMove(blackKing, whiteThreats) &&
                !canCaptureThreat(blackPieces, whiteThreats.get(blackKing.getCurrentSquare())) &&
                !canBlockThreat(blackPieces, whiteThreats.get(blackKing.getCurrentSquare()));
    }

    private boolean canKingMove(King king, Map<Square, List<Piece>> enemyThreats) {
        movableSquares.clear();
        boolean canMove = false;

        for (Square move : king.getLegalMoves(board)) {
            if (enemyThreats.get(move).isEmpty() && testMove(king, move)) {
                movableSquares.add(move);
                canMove = true;
            }
        }
        return canMove;
    }

    private boolean canCaptureThreat(List<Piece> allies, List<Piece> threats) {
        if (threats.size() != 1) return false;

        Square threatSquare = threats.get(0).getCurrentSquare();
        movableSquares.clear();

        // Can king capture?
        if (allies.contains(whiteKing) && whiteKing.getLegalMoves(board).contains(threatSquare) &&
                testMove(whiteKing, threatSquare)) {
            movableSquares.add(threatSquare);
            return true;
        }

        if (allies.contains(blackKing) && blackKing.getLegalMoves(board).contains(threatSquare) &&
                testMove(blackKing, threatSquare)) {
            movableSquares.add(threatSquare);
            return true;
        }

        // Can other pieces capture?
        for (Piece ally : allies) {
            if (ally.getLegalMoves(board).contains(threatSquare)) {
                if (testMove(ally, threatSquare)) {
                    movableSquares.add(threatSquare);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canBlockThreat(List<Piece> allies, List<Piece> threats) {
        if (threats.size() != 1) return false;

        Piece threat = threats.getFirst();
        Square kingSquare = allies.contains(whiteKing) ? whiteKing.getCurrentSquare() : blackKing.getCurrentSquare();
        Square threatSquare = threat.getCurrentSquare();

        // Get squares between king and threat
        List<Square> blockingSquares = getSquaresBetween(kingSquare, threatSquare);
        if (blockingSquares.isEmpty()) return false;

        movableSquares.clear();
        boolean canBlock = false;

        for (Square blockSquare : blockingSquares) {
            for (Piece ally : allies) {
                if (ally instanceof King) continue;

                if (ally.getLegalMoves(board).contains(blockSquare) && testMove(ally, blockSquare)) {
                    movableSquares.add(blockSquare);
                    canBlock = true;
                }
            }
        }

        return canBlock;
    }

    private List<Square> getSquaresBetween(Square s1, Square s2) {
        List<Square> squares = new ArrayList<>();

        // Same file
        if (s1.getXNum() == s2.getXNum()) {
            int minY = Math.min(s1.getYNum(), s2.getYNum());
            int maxY = Math.max(s1.getYNum(), s2.getYNum());
            for (int y = minY + 1; y < maxY; y++) {
                squares.add(board.getBoardSquares()[y][s1.getXNum()]);
            }
        }
        // Same rank
        else if (s1.getYNum() == s2.getYNum()) {
            int minX = Math.min(s1.getXNum(), s2.getXNum());
            int maxX = Math.max(s1.getXNum(), s2.getXNum());
            squares.addAll(Arrays.asList(board.getBoardSquares()[s1.getYNum()]).subList(minX + 1, maxX));
        }
        // Diagonal
        else if (Math.abs(s1.getXNum() - s2.getXNum()) == Math.abs(s1.getYNum() - s2.getYNum())) {
            int xStep = s1.getXNum() < s2.getXNum() ? 1 : -1;
            int yStep = s1.getYNum() < s2.getYNum() ? 1 : -1;

            int x = s1.getXNum() + xStep;
            int y = s1.getYNum() + yStep;

            while (x != s2.getXNum() && y != s2.getYNum()) {
                squares.add(board.getBoardSquares()[y][x]);
                x += xStep;
                y += yStep;
            }
        }

        return squares;
    }

    public List<Square> getAllowableSquares() {
        movableSquares.clear();

        if (isWhiteInCheck()) {
            if (!whiteCheckMated()) {
                return movableSquares;
            }
        } else if (isBlackInCheck()) {
            if (!blackCheckMated()) {
                return movableSquares;
            }
        }

        // If not in check, all legal moves are allowed
        return Arrays.stream(board.getBoardSquares())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

    public boolean testMove(Piece piece, Square target) {
        Square originalSquare = piece.getCurrentSquare();
        Piece capturedPiece = target.getOccupyingPiece();

        // Make the move
        piece.move(target);
        update();

        boolean isValid;
        if (piece.getColor() == 0) { // Black
            isValid = !isBlackInCheck();
        } else { // White
            isValid = !isWhiteInCheck();
        }

        // Undo the move
        piece.move(originalSquare);
        if (capturedPiece != null) {
            target.put(capturedPiece);
        }
        update();

        if (isValid) {
            movableSquares.add(target);
        }

        return isValid;
    }
}
