package com.kotikom.chess.piece;

import com.kotikom.chess.view.Square;
import com.kotikom.chess.view.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class Piece {
    private final int color;
    private Square currentSquare;
    private BufferedImage img;

    public Piece(int color, Square initSq, String img_file) {
        this.color = color;
        this.currentSquare = initSq;

        try {
            this.img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/" + img_file)));
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public boolean move(Square fin) {
        Piece occupyingPiece = fin.getOccupyingPiece();

        if (occupyingPiece != null) {
            if (occupyingPiece.getColor() == this.color) return false;
            else fin.capture(this);
        }

        currentSquare.removePiece();
        this.currentSquare = fin;
        currentSquare.put(this);
        return true;
    }

    public Square getPosition() {
        return currentSquare;
    }

    public void setPosition(Square sq) {
        this.currentSquare = sq;
    }

    public int getColor() {
        return color;
    }

    public Image getImage() {
        return img;
    }

    public void draw(Graphics g) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();

        g.drawImage(this.img, x, y, null);
    }

    public int[] getLinearOccupations(Square[][] board, int x, int y) {
        int lastYabove = 0;
        int lastXright = 7;
        int lastYbelow = 7;
        int lastXleft = 0;

        for (int i = 0; i < y; i++) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != this.color) {
                    lastYabove = i;
                } else lastYabove = i + 1;
            }
        }

        for (int i = 7; i > y; i--) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != this.color) {
                    lastYbelow = i;
                } else lastYbelow = i - 1;
            }
        }

        for (int i = 0; i < x; i++) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().getColor() != this.color) {
                    lastXleft = i;
                } else lastXleft = i + 1;
            }
        }

        for (int i = 7; i > x; i--) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().getColor() != this.color) {
                    lastXright = i;
                } else lastXright = i - 1;
            }
        }

        return new int[]{lastYabove, lastYbelow, lastXleft, lastXright};
    }

    public List<Square> getDiagonalOccupations(Square[][] board, int x, int y) {
        LinkedList<Square> diagonalOccupy = new LinkedList<>();

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
                if (board[yNW][xNW].getOccupyingPiece().getColor() != this.color) {
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
                if (board[ySW][xSW].getOccupyingPiece().getColor() != this.color) {
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
                if (board[ySE][xSE].getOccupyingPiece().getColor() != this.color) {
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
                if (board[yNE][xNE].getOccupyingPiece().getColor() != this.color) {
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

    public abstract List<Square> getLegalMoves(Board b);
}
