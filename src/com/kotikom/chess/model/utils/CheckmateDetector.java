package com.kotikom.chess.model.utils;

import com.kotikom.chess.model.Board;
import com.kotikom.chess.model.Square;
import com.kotikom.chess.model.piece.Piece;
import com.kotikom.chess.model.piece.impl.Bishop;
import com.kotikom.chess.model.piece.impl.King;
import com.kotikom.chess.model.piece.impl.Queen;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;


/**
 * Component of the Chess game that detects check mates in the game.
 *
 * @author Jussi Lundstedt
 */
public class CheckmateDetector {
    private final Board board;
    private final List<Piece> wPieces;
    private final List<Piece> bPieces;
    private final List<Square> movableSquares;
    private final List<Square> squares;
    private final King bk;
    private final King wk;
    private final HashMap<Square, List<Piece>> wMoves;
    private final HashMap<Square, List<Piece>> bMoves;

    /**
     * Constructs a new instance of model.CheckmateDetector on a given board. By
     * convention should be called when the board is in its initial state.
     *
     * @param board       The board which the detector monitors
     * @param wPieces White pieces on the board.
     * @param bPieces Black pieces on the board.
     * @param wk      model.Piece object representing the white king
     * @param bk      model.Piece object representing the black king
     */
    public CheckmateDetector(Board board, List<Piece> wPieces, List<Piece> bPieces, King wk, King bk) {
        this.board = board;
        this.wPieces = wPieces;
        this.bPieces = bPieces;
        this.bk = bk;
        this.wk = wk;

        squares = new ArrayList<>();
        movableSquares = new ArrayList<>();
        wMoves = new HashMap<>();
        bMoves = new HashMap<>();

        Square[][] brd = board.getSquareArray();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                squares.add(brd[y][x]);
                wMoves.put(brd[y][x], new ArrayList<>());
                bMoves.put(brd[y][x], new ArrayList<>());
            }
        }

        update();
    }

    /**
     * Updates the object with the current situation of the game.
     */
    public void update() {
        wMoves.values().forEach(List::clear);
        bMoves.values().forEach(List::clear);
        movableSquares.clear();

        updateColorPieces(wPieces, wMoves);
        updateColorPieces(bPieces, bMoves);
    }

    private void updateColorPieces(List<Piece> pieces, Map<Square, List<Piece>> moveMap) {
        Iterator<Piece> iter = pieces.iterator();

        while (iter.hasNext()) {
            Piece p = iter.next();

            if (p instanceof King) {
                continue;
            }

            if (p.getPosition() == null) {
                iter.remove();
                continue;
            }

            for (Square move : p.getLegalMoves(board)) {
                moveMap.computeIfAbsent(move, k -> new ArrayList<>()).add(p);
            }
        }
    }

    /**
     * Checks if the black king is threatened
     *
     * @return boolean representing whether the black king is in check.
     */
    public boolean isBlackInCheck() {
        update();
        Square sq = bk.getPosition();
        if (wMoves.get(sq).isEmpty()) {
            movableSquares.addAll(squares);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if the white king is threatened
     *
     * @return boolean representing whether the white king is in check.
     */
    public boolean isWhiteInCheck() {
        update();
        Square sq = wk.getPosition();
        if (bMoves.get(sq).isEmpty()) {
            movableSquares.addAll(squares);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks whether black is in checkmate.
     *
     * @return boolean representing if black player is checkmated.
     */
    public boolean blackCheckMated() {
        boolean checkmate = true;
        // Check if black is in check
        if (!this.isBlackInCheck()) return false;

        // If yes, check if king can evade
        if (canEvade(wMoves, bk)) checkmate = false;

        // If no, check if threat can be captured
        List<Piece> threats = wMoves.get(bk.getPosition());
        if (canCapture(bMoves, threats, bk)) checkmate = false;

        // If no, check if threat can be blocked
        if (canBlock(threats, bMoves, bk)) checkmate = false;

        // If no possible ways of removing check, checkmate occurred
        return checkmate;
    }

    /**
     * Checks whether white is in checkmate.
     *
     * @return boolean representing if white player is checkmated.
     */
    public boolean whiteCheckMated() {
        boolean checkmate = true;
        // Check if white is in check
        if (!this.isWhiteInCheck()) return false;

        // If yes, check if king can evade
        if (canEvade(bMoves, wk)) checkmate = false;

        // If no, check if threat can be captured
        List<Piece> threats = bMoves.get(wk.getPosition());
        if (canCapture(wMoves, threats, wk)) checkmate = false;

        // If no, check if threat can be blocked
        if (canBlock(threats, wMoves, wk)) checkmate = false;

        // If no possible ways of removing check, checkmate occurred
        return checkmate;
    }

    private boolean canEvade(Map<Square, List<Piece>> tMoves, King tKing) {
        boolean evade = false;
        List<Square> kingsMoves = tKing.getLegalMoves(board);

        // If king is not threatened at some square, it can evade
        for (Square sq : kingsMoves) {
            if (!testMove(tKing, sq)) continue;
            if (tMoves.get(sq).isEmpty()) {
                movableSquares.add(sq);
                evade = true;
            }
        }

        return evade;
    }

    private boolean canCapture(Map<Square, List<Piece>> poss, List<Piece> threats, King k) {

        boolean capture = false;
        if (threats.size() == 1) {
            Square sq = threats.getFirst().getPosition();

            if (k.getLegalMoves(board).contains(sq)) {
                movableSquares.add(sq);
                if (testMove(k, sq)) {
                    capture = true;
                }
            }

            List<Piece> caps = poss.get(sq);
            ConcurrentLinkedDeque<Piece> capturers = new ConcurrentLinkedDeque<>(caps);

            if (!capturers.isEmpty()) {
                movableSquares.add(sq);
                for (Piece p : capturers) {
                    if (testMove(p, sq)) {
                        capture = true;
                    }
                }
            }
        }

        return capture;
    }

    private boolean canBlock(List<Piece> threats, Map<Square, List<Piece>> blockMoves, King k) {
        boolean canBeBlocked = false;

        if (threats.size() == 1) {
            Square ts = threats.getFirst().getPosition();
            Square ks = k.getPosition();
            Square[][] brdArray = board.getSquareArray();

            if (ks.getXNum() == ts.getXNum()) {
                int max = Math.max(ks.getYNum(), ts.getYNum());
                int min = Math.min(ks.getYNum(), ts.getYNum());

                for (int i = min + 1; i < max; i++) {
                    List<Piece> blks =
                            blockMoves.get(brdArray[i][ks.getXNum()]);
                    ConcurrentLinkedDeque<Piece> blockers = new ConcurrentLinkedDeque<>(blks);

                    if (!blockers.isEmpty()) {
                        movableSquares.add(brdArray[i][ks.getXNum()]);

                        for (Piece p : blockers) {
                            if (testMove(p, brdArray[i][ks.getXNum()])) {
                                canBeBlocked = true;
                            }
                        }

                    }
                }
            }

            if (ks.getYNum() == ts.getYNum()) {
                int max = Math.max(ks.getXNum(), ts.getXNum());
                int min = Math.min(ks.getXNum(), ts.getXNum());

                for (int i = min + 1; i < max; i++) {
                    List<Piece> blks =
                            blockMoves.get(brdArray[ks.getYNum()][i]);
                    ConcurrentLinkedDeque<Piece> blockers =
                            new ConcurrentLinkedDeque<>(blks);

                    if (!blockers.isEmpty()) {

                        movableSquares.add(brdArray[ks.getYNum()][i]);

                        for (Piece p : blockers) {
                            if (testMove(p, brdArray[ks.getYNum()][i])) {
                                canBeBlocked = true;
                            }
                        }

                    }
                }
            }

            Class<? extends Piece> tC = threats.getFirst().getClass();

            if (tC.equals(Queen.class) || tC.equals(Bishop.class)) {
                int kX = ks.getXNum();
                int kY = ks.getYNum();
                int tX = ts.getXNum();
                int tY = ts.getYNum();

                if (kX > tX && kY > tY) {
                    for (int i = tX + 1; i < kX; i++) {
                        tY++;
                        List<Piece> blks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Piece> blockers = new ConcurrentLinkedDeque<>(blks);

                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);

                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    canBeBlocked = true;
                                }
                            }
                        }
                    }
                }

                if (kX > tX && tY > kY) {
                    for (int i = tX + 1; i < kX; i++) {
                        tY--;
                        List<Piece> blks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Piece> blockers = new ConcurrentLinkedDeque<>(blks);

                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);

                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    canBeBlocked = true;
                                }
                            }
                        }
                    }
                }

                if (tX > kX && kY > tY) {
                    for (int i = tX - 1; i > kX; i--) {
                        tY++;
                        List<Piece> blks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Piece> blockers = new ConcurrentLinkedDeque<>(blks);

                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);

                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    canBeBlocked = true;
                                }
                            }
                        }
                    }
                }

                if (tX > kX && tY > kY) {
                    for (int i = tX - 1; i > kX; i--) {
                        tY--;
                        List<Piece> blks =
                                blockMoves.get(brdArray[tY][i]);
                        ConcurrentLinkedDeque<Piece> blockers = new ConcurrentLinkedDeque<>(blks);

                        if (!blockers.isEmpty()) {
                            movableSquares.add(brdArray[tY][i]);

                            for (Piece p : blockers) {
                                if (testMove(p, brdArray[tY][i])) {
                                    canBeBlocked = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return canBeBlocked;
    }

    /**
     * Method to get a list of allowable squares that the player can move.
     * Defaults to all squares, but limits available squares if player is in
     * check.
     *
     * @return List of squares that the player can move into.
     */
    public List<Square> getAllowableSquares() {
        movableSquares.clear();
        if (isWhiteInCheck()) {
            whiteCheckMated();
        } else if (isBlackInCheck()) {
            blackCheckMated();
        }
        return movableSquares;
    }

    /**
     * Tests a move a player is about to make to prevent making an illegal move
     * that puts the player in check.
     *
     * @param p  model.Piece moved
     * @param sq model.Square to which p is about to move
     * @return false if move would cause a check
     */
    public boolean testMove(Piece p, Square sq) {
        Piece c = sq.getOccupyingPiece();

        boolean movetest = true;
        Square init = p.getPosition();

        p.move(sq);
        update();

        if (p.getColor() == 0 && isBlackInCheck()) movetest = false;
        else if (p.getColor() == 1 && isWhiteInCheck()) movetest = false;

        p.move(init);
        if (c != null) sq.put(c);

        update();

        movableSquares.addAll(squares);
        return movetest;
    }
}
