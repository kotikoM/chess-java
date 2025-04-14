package com.kotikom.chess.view;

import com.kotikom.chess.model.core.Board;
import com.kotikom.chess.model.core.Square;
import com.kotikom.chess.model.piece.Piece;
import com.kotikom.chess.model.internal.CheckmateDetector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class BoardView extends JPanel implements MouseListener, MouseMotionListener {
    private final GameWindowView g;
    private final Board board;
    private final CheckmateDetector cmd;
    private boolean whiteTurn;
    private Piece currPiece;
    private int currX;
    private int currY;

    public BoardView(GameWindowView g) {
        this.g = g;
        this.board = new Board();
        this.cmd = board.getCmd();
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        addListenersToBoardSquares();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;
    }

    private void addListenersToBoardSquares() {
        Square[][] boardSquares = board.getSquareArray();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if ((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    this.add(boardSquares[x][y]);
                } else {
                    this.add(boardSquares[x][y]);
                }
            }
        }
    }


    public boolean getTurn() {
        return whiteTurn;
    }

    @Override
    public void paintComponent(Graphics g) {
        Square[][] squareArray = board.getSquareArray();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = squareArray[y][x];
                sq.paintComponent(g);
            }
        }

        if (currPiece != null) {
            if ((currPiece.getColor() == 1 && whiteTurn)
                    || (currPiece.getColor() == 0 && !whiteTurn)) {
                final Image i = currPiece.getImage();
                g.drawImage(i, currX, currY, null);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            if (currPiece.getColor() == 0 && whiteTurn)
                return;
            if (currPiece.getColor() == 1 && !whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (currPiece == null)
            return;
        if (currPiece.getColor() == 0 && whiteTurn)
            return;
        if (currPiece.getColor() == 1 && !whiteTurn)
            return;

        List<Square> legalMoves = currPiece.getLegalMoves(board);
        List<Square> movable = cmd.getAllowableSquares();

        if (legalMoves.contains(sq) &&
                movable.contains(sq) &&
                cmd.testMove(currPiece, sq)) {
            sq.setDisplay(true);
            currPiece.move(sq);
            cmd.update();

            if (cmd.blackCheckMated()) {
                currPiece = null;
                repaint();
                this.removeMouseListener(this);
                this.removeMouseMotionListener(this);
                g.handleCheckmate(0);
            } else if (cmd.whiteCheckMated()) {
                currPiece = null;
                repaint();
                this.removeMouseListener(this);
                this.removeMouseMotionListener(this);
                g.handleCheckmate(1);
            } else {
                currPiece = null;
                whiteTurn = !whiteTurn;
            }

        } else {
            currPiece.getPosition().setDisplay(true);
            currPiece = null;
        }

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
