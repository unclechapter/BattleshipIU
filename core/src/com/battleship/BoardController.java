package com.battleship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

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

    public ShotState fireAtPos(Point point) {
        return board.fireAtPos(point);
    }
}
