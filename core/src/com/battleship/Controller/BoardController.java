package com.battleship.Controller;

import com.badlogic.gdx.graphics.Texture;
import com.battleship.Board.Board;
import com.battleship.ShotState;

import java.awt.*;

public abstract class BoardController {
    protected Board board;
    protected int boardSize = Board.BOARD_SIZE;

    public BoardController(Texture txBg, Texture txMiss, Texture txCenter, Texture txEdge, Point boardOffset) {
        board = new Board(txBg, txMiss, txCenter, txEdge, boardOffset);
    }

    public Board getBoard() {
        return board;
    }

    protected ShotState fireAtPos(Point point) {
        return board.fireAtPos(point);
    }

    public void reset() {
        board.reset();
    }
}
