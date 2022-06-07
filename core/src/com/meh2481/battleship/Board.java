package com.meh2481.battleship;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import java.awt.*;
import java.util.Arrays;

/**
 * Created by Mark on 1/15/2016.
 *
 * Defines a class for handling how ships are placed on the Battleship board, and for causing interactions
 * (such as firing at a ship, placing ships randomly on the board, etc)
 */
public abstract class Board
{
    public static final int BOARD_SIZE = 12;    //X and Y size of the board, in tiles 12
    public static final int TILE_SIZE = 75;     //Size of each tile, in pixels 64

    private Texture m_txBoardBg;    //Texture for the board background
    private Texture m_txMissImage;  //Image to draw when we've guessed somewhere and missed
    protected Array<Ship> m_lShips;   //Ships on this board
    private Array<Point> m_lMissGuessPos;   //Places on the map that have been guessed already, and were misses
    protected Array<Array<Ship>> shipPositions;

    /**
     * Constructor for creating a Board class object
     * @param txBg  Background board texture to use as the backdrop when drawing the board
     * @param txMiss    Texture used for drawing guesses that missed
     * @param txCenter  Texture used for drawing the central portion of ships that have been hit
     * @param txEdge    Texture used for drawing the edge of ships
     */
    public Board(Texture txBg, Texture txMiss, Texture txCenter, Texture txEdge) {
        //Hang onto the board background and miss tile textures
        m_txBoardBg = txBg;
        m_txMissImage = txMiss;

        //Create arrays
        m_lShips = new Array<Ship>();
        m_lMissGuessPos = new Array<Point>();

        shipPositions = new Array<>();
        for (int i = 0; i < BOARD_SIZE; i++){
            Array<Ship> temp = new Array();
            temp.setSize(BOARD_SIZE);
            shipPositions.add(temp);
        }

        Sprite sCenter = new Sprite(txCenter);
        Sprite sEdge = new Sprite(txEdge);
        //Create ships and add them to our list
        for(int i = 0; i < 5; i ++)
            m_lShips.add(new Ship(sCenter, sEdge, ShipType.values()[i]));
    }

    /**
     * Reset the board to uninitialized state
     */
    public void reset() {
        for(Ship s : m_lShips)
            s.reset();

        m_lMissGuessPos.clear();
    }

/** Draw the board and all ships on it onto the specified Batch.
 *
 * @param    bHidden     true means hide ships that haven't been hit, false means draw all ships
 * @param    bBatch      The batch to draw the board onto
 */
    public void draw(boolean bHidden, Batch bBatch) {
        //Draw board background image
        bBatch.draw(m_txBoardBg, 0, 0);
        //Draw misses
        for(Point p : m_lMissGuessPos)
            bBatch.draw(m_txMissImage, p.x * TILE_SIZE, p.y * TILE_SIZE);
        //Draw ships
        for(Ship s : m_lShips)
            s.draw(bHidden, bBatch);
    }

    /**
     * Test to see if every ship is sunk
     * @return true if every ship on the board is sunk, false otherwise
     */
    public boolean boardCleared() {
        for(Ship s : m_lShips)
            if(!s.isSunk())
                return false;

        return true;
    }

    /**
     * Get the number of ships that haven't been sunk yet
     * @return  Number of ships that are still afloat
     */
    public int shipsLeft() {
        int numLeft = 0;
        for(Ship s : m_lShips)
        {
            if(!s.isSunk())
                numLeft++;
        }
        return numLeft;
    }

    /** Test if we've already fired a missile at this position
     *
     * @param    xPos     x position to test
     * @param    yPos     y position to test
     * @return  true if we have fired at this position already, false if not
     */
    public boolean alreadyFired(int xPos, int yPos)
    {
        //Check if we've missed by guessing in this position already
        for(Point p : m_lMissGuessPos)
        {
            if(p.x == xPos && p.y == yPos)
                return true;
        }
        //Check if we've hit by guessing in this position already
        for(Ship s : m_lShips)
        {
            if(s.alreadyHit(xPos, yPos))
                return true;
        }
        //This position is clear for firing
        return false;
    }

    /** Fire at this position, returning ship that was hit or null on miss
     *
     * @param       xPos     x position to fire to
     * @param       yPos     y position to fire to
     * @return      Ship that was hit or null on miss
     */
    public Ship fireAtPos(int xPos, int yPos)
    {
        for(Ship s : m_lShips)
        {
            if(s.fireAtShip(xPos, yPos))
                return s;   //Return the ship itself to facilitate getting the name and testing if sunk or not
        }

        //Miss; add to our miss positions and return nothing
        m_lMissGuessPos.add(new Point(xPos, yPos));
        return null;
    }
    public Ship fireHorizontalBomb(int xPos, int yPos){
        for (Ship ship : m_lShips){
            if(true)
                return ship;
        }
        return null;
    }

    public Ship fireVerticalBomb(int xPos, int yPos){
        for (Ship ship : m_lShips){
            if(true)
                return ship;
        }
        return null;
    }
    /**To place a ship at a certain position. It checks if the position is placeable then save the position of the ship
     * @param xPos x position to place ship
     * @param yPos y position to place ship
     * @param ship ship to place
     * @param horizontal ship orientation
    */
    public boolean placeShip(int xPos, int yPos, Ship ship, boolean horizontal){
        if (checkOK(ship, xPos, yPos)){
            ship.updatePosition(xPos, yPos, horizontal);

            for(int i = 0; i < ship.type.size; i++)
                shipPositions.get(xPos + ship.getHorizontal().x).insert(yPos + ship.getHorizontal().y, ship);

            return true;
        }

        return false;
    }
    /**Special feature - ship is allowed to teleport to a different position on the board if it has not been hit and the new position has not been hit 
     * @param xPos new x position
     * @param yPos new y position
     * @param ship the ship to teleport
     * @param horizontal new orientation
    */
    public void teleport(int xPos, int yPos, Ship ship, boolean horizontal){
        if (ship.beenHit = false){
            if (!alreadyFired(xPos, yPos)){
                placeShip(xPos, yPos, ship, horizontal);
            }
        }
    }
    /** Checks if ship at certain position is outside the border or overlaps with other ships */
    public boolean checkOK(Ship ship, int xPos, int yPos){
        if (xPos < 0 || xPos + ship.getHorizontal().x + ship.getType().size >= BOARD_SIZE
            || yPos < 0 || yPos + ship.getHorizontal().y * ship.getType().size >= BOARD_SIZE){
            return false;
        } else for (int i = 0; i < ship.type.size; i++)
            if (shipPositions.get(xPos + ship.getHorizontal().x * i).get(yPos + ship.getHorizontal().y * i) != null)
                return false;

        return true;
    }

    public void placeSonar(int xPos, int yPos){
        
    }

    public void placeShield(int xPos, int yPos){

    }
}













