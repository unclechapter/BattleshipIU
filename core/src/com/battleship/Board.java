package com.battleship;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.battleship.specialAttack.Sonar;
import com.battleship.specialAttack.Bomb;
import com.battleship.specialAttack.Shield;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by Mark on 1/15/2016.
 *
 * Defines a class for handling how ships are placed on the Battleship board, and for causing interactions
 * (such as firing at a ship, placing ships randomly on the board, etc)
 */
public class Board
{
    public static final int BOARD_SIZE = 12;    //X and Y size of the board, in tiles 12
    public static final int TILE_SIZE = 75;     //Size of each tile, in pixels 64
    public final Point offset;

    private Texture m_txBoardBg;    //Texture for the board background
    private Texture m_txMissImage;  //Image to draw when we've guessed somewhere and missed
    protected HashMap<ShipType, Ship> m_lShips;   //Ships on this board
    private Array<Point> guessPos;   //Places on the map that have been guessed already, and were misses
    public Array<Array<Ship>> shipPositions;
    public Shield shield;
    public Sonar sonar;
    public Bomb bomb;

    /**
     * Constructor for creating a Board class object
     * @param offset
     * @param txBg     Background board texture to use as the backdrop when drawing the board
     * @param txMiss   Texture used for drawing guesses that missed
     */
    public Board(Texture txBg, Texture txMiss, Texture txCenter, Texture txEdge, Point offset) {
        this.offset = offset;
        //Hang onto the board background and miss tile textures
        m_txBoardBg = txBg;
        m_txMissImage = txMiss;

        //Create arrays
        guessPos = new Array<Point>();
        m_lShips = new HashMap<>();

        shipPositions = new Array<>();
        for (int i = 0; i < BOARD_SIZE; i++){
            Array<Ship> temp = new Array();
            temp.setSize(BOARD_SIZE);
            shipPositions.add(temp);
        }

        //Create ships and add them to our list
        for(int i = 0; i < 5; i ++)
            m_lShips.put(ShipType.values()[i], new Ship(new Sprite(txCenter), new Sprite(txEdge), ShipType.values()[i]));
    }

    /**To place a ship at a certain position. It checks if the position is placeable then save the position of the ship
     * @param ship ship to place
     * @param horizontal ship orientation
     */
    public boolean placeShip(Point point, ShipType type, boolean horizontal){
        Ship ship = m_lShips.get(type);
        if (checkOK(ship, point)){
            ship.updatePosition(point, horizontal);

            for(int i = 0; i < ship.type.size; i++)
                shipPositions.get(point.x + ship.getOrientation().x * i).set(point.y + ship.getOrientation().y * i, ship);

            return true;
        }

        return false;
    }

    /** Checks if ship at certain position is outside the border or overlaps with other ships
     * @param ship ship to check
     */
    public boolean checkOK(Ship ship, Point point){
        if (point.x < 0 || point.x + ship.getOrientation().x * ship.getType().size > BOARD_SIZE
                || point.x < 0 || point.y + ship.getOrientation().y * ship.getType().size > BOARD_SIZE){
            System.out.println("Out of BOund " + point);
            return false;
        } else for (int i = 0; i < ship.type.size; i++)
            if (shipPositions.get(point.x + ship.getOrientation().x * i).get(point.y + ship.getOrientation().y * i) != null) {
                System.out.println("OVerlap");
                return false;
            }

        return true;
    }

    /** Fire at this position, returning ship that was hit or null on miss
     * @param       point     position to fire to
     * @return      Ship that was hit or null on miss
     */
    public ShotState fireAtPos(Point point) {
        Ship ship = shipPositions.get(point.x).get(point.y);
        guessPos.add(new Point(point)); //Miss; add to our miss positions and return nothing
        if(ship != null) {
            if (ship.fireAtShip(point) == ShotState.SUNK){
                return ShotState.SUNK;
            }
            else return ShotState.HIT;
        }
        else
            return ShotState.MISS;
    }

    public Array<ShotState> fireBomb(){
        Array<ShotState> fireResult = new Array<ShotState>();
        for (Point bombPoint : bomb.bomb){
            fireResult.add(fireAtPos(bombPoint));
        }
        return fireResult;
    }

    /** Test if we've already fired a missile at this position
     * @return  true if we have fired at this position already, false if not
     */
    public boolean alreadyFired(Point point) {
        return guessPos.contains(point, false);
    }

    /**
     * Reset the board to uninitialized state
     */
    public void reset() {
        for(Ship s : m_lShips.values())
            s.reset();

        guessPos.clear();
    }

    /**
     * Test to see if every ship is sunk
     * @return true if every ship on the board is sunk, false otherwise
     */
    /*public boolean boardCleared() {
        for(Ship s : m_lShips.values())
            if(s.isSunk() == null)
                return false;

        return true;
    }*/
    public boolean boardCleared() {
        if (Ship.sunkShipCount == m_lShips.size())
                return false;
        return true;
    }

    /**Special feature - ship is allowed to teleport to a different position on the board if it has not been hit and the new position has not been hit 
     * @param xPos new x position
     * @param yPos new y position
     * @param ship the ship to teleport
     * @param horizontal new orientation
    */
    public void teleport(Point point, Ship ship, boolean horizontal){
        if (ship.beenHit = false){
            if (!alreadyFired(point)){
                placeShip(point, ship.type, horizontal);
            }
        }
    }
    public Array<Point> placeSonar(Point point){
        sonar.moveSonar(point);
        return sonar.findShip(point);
    }

    public void placeShield(Point point){
        if (shipPositions.get(point.x).get(point.y)!=null){
            shield.setPosition(point);
        }
    }

    /** Draw the board and all ships on it onto the specified Batch.
     *
     * @param    bHidden     true means hide ships that haven't been hit, false means draw all ships
     * @param    bBatch      The batch to draw the board onto
     */
    public void draw(boolean bHidden, Batch bBatch) {
        //Draw board background image
        Sprite bg = new Sprite(m_txBoardBg);
        bg.flip(false, true);
        bg.setPosition(0, 0);
        bg.draw(bBatch);
        //Draw misses
        for(Point p : guessPos)
            bBatch.draw(m_txMissImage, p.x * TILE_SIZE + offset.x, p.y * TILE_SIZE + offset.y);
        //Draw ships
        for(Ship s : m_lShips.values())
            s.draw(bHidden, bBatch, offset);
    }
}













