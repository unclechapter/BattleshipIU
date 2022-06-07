package com.battleship;

import com.badlogic.gdx.graphics.Texture;

import java.awt.*;

public class BoardPlayer extends Board{
    private Point m_ptCurPos;   //Hold onto the current position of the ship we're placing

    /**
     * Constructor for creating a Board class object
     *
     * @param txBg     Background board texture to use as the backdrop when drawing the board
     * @param txMiss   Texture used for drawing guesses that missed
     * @param txCenter
     * @param txEdge
     * @param offset
     */
    public BoardPlayer(Texture txBg, Texture txMiss, Texture txCenter, Texture txEdge, Point offset) {
        super(txBg, txMiss, txCenter, txEdge, offset);
    }

    public boolean playerPlace(ShipType type, Point point) {
        return this.placeShip(point, type, m_lShips.get(type).isHorizontal());
    }

    /**
     * Move the current ship we're going to place next
     * @param xPos  x board position to move the upper left of the ship to
     * @param yPos  y board position to move the upper left of the ship to
     */
    public Point previewShip(ShipType type, Point point) {
        m_ptCurPos = point;

        //Check and be sure we're not off the edge of the map.
        Ship sPlace = m_lShips.get(type);
        int size = sPlace.getType().size;

        if(point.x + sPlace.getOrientation().x * size > BOARD_SIZE)
            point.x = BOARD_SIZE - sPlace.getOrientation().x * size;

        if(point.y + sPlace.getOrientation().y * size > BOARD_SIZE)
            point.y = BOARD_SIZE - sPlace.getOrientation().y * size;


        sPlace.updatePosition(point, sPlace.isHorizontal());

        return point;
    }

    /**
     * Rotate the current ship we're placing (rotate horizontal if vertical & vice versa)
     */
    public void rotateShip(ShipType type) {
            Ship sPlace = m_lShips.get(type);
            sPlace.updatePosition(sPlace.getPosition(), !sPlace.isHorizontal());
            //Make sure we're not off the map after rotating by moving to the current position
            previewShip(type, m_ptCurPos);
    }
}