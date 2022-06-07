package com.meh2481.battleship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.meh2481.battleship.Ship;

public abstract class BoardController {
    protected Array<Ship> m_lShips;   //Ships on this board
    protected Board board;
    protected int boardSize = Board.BOARD_SIZE;

    public BoardController(Texture txBg, Texture txMiss, Texture txCenter, Texture txEdge) {
        m_lShips = new Array();
        board = new Board(txBg, txMiss, m_lShips);

        //Create ships and add them to our list
        for(int i = 0; i < 5; i ++)
            m_lShips.add(new Ship(new Sprite(txCenter), new Sprite(txEdge), ShipType.values()[i]));
    }

    public Board getBoard() {
        return board;
    }

    public Ship fireAtPos(int xPos, int yPos) {
        return board.fireAtPos(xPos, yPos);
    }
}
