package com.kotikom.chess.model.internal;

import com.kotikom.chess.model.core.Square;

import java.util.ArrayList;
import java.util.List;

public class MoveUtils  {
    public static int[] getLinearOccupations(Square[][] board, int x, int y, int color) {
        int lastYabove = 0;
        int lastXright = 7;
        int lastYbelow = 7;
        int lastXleft = 0;

        for (int i = 0; i < y; i++) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != color) {
                    lastYabove = i;
                } else lastYabove = i + 1;
            }
        }

        for (int i = 7; i > y; i--) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != color) {
                    lastYbelow = i;
                } else lastYbelow = i - 1;
            }
        }

        for (int i = 0; i < x; i++) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().getColor() != color) {
                    lastXleft = i;
                } else lastXleft = i + 1;
            }
        }

        for (int i = 7; i > x; i--) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().getColor() != color) {
                    lastXright = i;
                } else lastXright = i - 1;
            }
        }

        return new int[]{lastYabove, lastYbelow, lastXleft, lastXright};
    }

    public static List<Square> getDiagonalOccupations(Square[][] board, int x, int y, int color) {
        List<Square> diagonalOccupy = new ArrayList<>();

        int xNW = x - 1;
        int xSW = x - 1;
        int xNE = x + 1;
        int xSE = x + 1;
        int yNW = y - 1;
        int ySW = y + 1;
        int yNE = y - 1;
        int ySE = y + 1;

        while (xNW >= 0 && yNW >= 0) {
            if (board[yNW][xNW].isOccupied()) {
                if (board[yNW][xNW].getOccupyingPiece().getColor() != color) {
                    diagonalOccupy.add(board[yNW][xNW]);
                }
                break;
            } else {
                diagonalOccupy.add(board[yNW][xNW]);
                yNW--;
                xNW--;
            }
        }

        while (xSW >= 0 && ySW < 8) {
            if (board[ySW][xSW].isOccupied()) {
                if (board[ySW][xSW].getOccupyingPiece().getColor() != color) {
                    diagonalOccupy.add(board[ySW][xSW]);
                }
                break;
            } else {
                diagonalOccupy.add(board[ySW][xSW]);
                ySW++;
                xSW--;
            }
        }

        while (xSE < 8 && ySE < 8) {
            if (board[ySE][xSE].isOccupied()) {
                if (board[ySE][xSE].getOccupyingPiece().getColor() != color) {
                    diagonalOccupy.add(board[ySE][xSE]);
                }
                break;
            } else {
                diagonalOccupy.add(board[ySE][xSE]);
                ySE++;
                xSE++;
            }
        }

        while (xNE < 8 && yNE >= 0) {
            if (board[yNE][xNE].isOccupied()) {
                if (board[yNE][xNE].getOccupyingPiece().getColor() != color) {
                    diagonalOccupy.add(board[yNE][xNE]);
                }
                break;
            } else {
                diagonalOccupy.add(board[yNE][xNE]);
                yNE--;
                xNE++;
            }
        }

        return diagonalOccupy;
    }
}
