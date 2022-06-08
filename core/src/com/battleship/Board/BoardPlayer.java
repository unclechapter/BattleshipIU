package com.battleship.Board;

import com.badlogic.gdx.graphics.Texture;
import com.battleship.Ship;
import com.battleship.ShipType;
import com.battleship.specialAttack.Bomb;
import com.battleship.specialAttack.Shield;
import com.battleship.specialAttack.Sonar;

import java.awt.*;

public class BoardPlayer extends Board {
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
        int size = sPlace.getType().getSize();

        if(point.x + sPlace.getOrientation().x * size > BOARD_SIZE)
            point.x = BOARD_SIZE - sPlace.getOrientation().x * size;

        if(point.y + sPlace.getOrientation().y * size > BOARD_SIZE)
            point.y = BOARD_SIZE - sPlace.getOrientation().y * size;


        sPlace.updatePosition(point, sPlace.isHorizontal());

        return point;
    }

    public void previewShield(Point point){
        m_ptCurPos = point;
        if (point.x < BOARD_SIZE)
            point.x = 0;
        if (point.y < BOARD_SIZE)
            point.y = 0;
        if (point.x > BOARD_SIZE)
            point.x = BOARD_SIZE;
        if (point.y > BOARD_SIZE)
            point.y = BOARD_SIZE;
        shield.setPosition(point);
    }

    public void previewSonar(Point point){
        if (Sonar.SONAR_RADIUS + point.x > Board.BOARD_SIZE){
            point.x = Board.BOARD_SIZE - Sonar.SONAR_RADIUS;
        }
        if (Sonar.SONAR_RADIUS + point.y > Board.BOARD_SIZE){
            point.x = Board.BOARD_SIZE - Sonar.SONAR_RADIUS;
        }
        if (point.x - Sonar.SONAR_RADIUS < 0){
            point.x = Sonar.SONAR_RADIUS;
        }
        if (point.y - Sonar.SONAR_RADIUS < 0){
            point.y = Sonar.SONAR_RADIUS;
        }
        sonar.setPosition(point);
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

    public void previewBomb(Point point){
        if (point.x + Bomb.BOMB_SIZE > Board.BOARD_SIZE){
            point.x = Board.BOARD_SIZE - bomb.getOrientation().x*Bomb.BOMB_SIZE;
        }
        if (point.y + Bomb.BOMB_SIZE > Board.BOARD_SIZE){
            point.y = Board.BOARD_SIZE - bomb.getOrientation().y*Bomb.BOMB_SIZE;
        }
        bomb.updateBombPosition(point);
    }

    public void teleportRotate(){

    }
    public boolean playerPlaceShield(Point point){
        return this.placeShield(point);
    }
}
