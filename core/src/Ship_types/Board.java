package Ship_types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import java.awt.*;

public class Board
{
    public static final int BOARD_SIZE = 10;    //X and Y size of the board, in tiles
    public static final int TILE_SIZE = 64;     //Size of each tile, in pixels

    public Texture m_txBoardBg;    //Texture for the board background
    private Texture m_txMissImage;  //Image to draw when we've guessed somewhere and missed
    protected Array<Ship> m_lShips;   //Ships on this board
    private Array<Point> m_lMissGuessPos;   //Places on the map that have been guessed already, and were misses



    /**
     * Constructor for creating a Board class object
     * @param txBg  Background board texture to use as the backdrop when drawing the board
     * @param txMiss    Texture used for drawing guesses that missed
     * @param txCenter  Texture used for drawing the central portion of ships that have been hit
     * @param txEdge    Texture used for drawing the edge of ships
     */
    public Board(Texture txBg, Texture txMiss, Texture txCenter, Texture txEdge)
    {
        //Hang onto the board background and miss tile textures
        m_txBoardBg = txBg;
        m_txMissImage = txMiss;

        //Create arrays
        m_lShips = new Array<Ship>();
        m_lMissGuessPos = new Array<Point>();

        Sprite sCenter = new Sprite(txCenter);
        Sprite sEdge = new Sprite(txEdge);
        //Create ships and add them to our list
        //m_lShips.add(new Ship_Carrier(sCenter, sEdge)); /**teleport*/
        m_lShips.add(new Ship_Battleship(sCenter, sEdge));
        m_lShips.add(new Ship_Cruiser(sCenter, sEdge));
        m_lShips.add(new Ship_Destroyer(sCenter, sEdge));
    }

    /**
     * Reset the board to uninitialized state
     */
    public void reset()
    {
        for(Ship s : m_lShips)
            s.reset();

        m_lMissGuessPos.clear();
    }

    /** Draw the board and all ships on it onto the specified Batch.
     *
     * @param    bHidden     true means hide ships that haven't been hit, false means draw all ships
     * @param    bBatch      The batch to draw the board onto
     */
    public void draw(boolean bHidden, Batch bBatch)
    {
        //Draw board background image
        bBatch.draw(m_txBoardBg, Gdx.graphics.getWidth() / 2 - m_txBoardBg.getWidth() / 2, Gdx.graphics.getHeight() / 2 - m_txBoardBg.getHeight() / 2);
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
    public boolean boardCleared()
    {
        for(Ship s : m_lShips)
        {
            if(!s.isSunk())
                return false;
        }
        return true;
    }

    /**
     * Get the number of ships that haven't been sunk yet
     * @return  Number of ships that are still afloat
     */
    public int shipsLeft()
    {
        int numLeft = 0;
        for(Ship s : m_lShips)
        {
            if(!s.isSunk())
                numLeft++;
        }
        return numLeft;
    }



    /**
     * PLACING SHIPS MANUALLY AND NOT OVERLAPPING
     */
    public void placeShips()
    {
        //Clear all current ship positions
        for(Ship s : m_lShips)
            s.setPosition(-1, -1);

         //Loop until we find a good spot to place this ship
        

        //Make sure we're not colliding with any other ships in this location
        //for(Ship sTestCollide : m_lShips)


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
}
