package com.meh2481.battleship;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

import java.awt.*;

/**
 * Created by Mark on 1/15/2016.
 *
 * Abstract class for ships that handles interactions between ships, facilitates launching missiles at ships, etc
 * See Ship_* classes for implementations of this class that contain ship-specific info (such as size, name, etc)
 */
public class Ship
{
    private Sprite m_sShipHitSprite;    //Image for the ship being hit (inner image)
    private Sprite m_sShipOKSprite; //Image for the ship being ok (outer image)
    private Array<Point> m_iHitPositions;
    private Array<Point> position;
    protected ShipType type;
    private boolean horizontal = true;
    public boolean beenHit; //if ship has been hit by a bomb

    //How faded out a sunk ship looks
    public static final float SHIP_SUNK_ALPHA = 0.65f;

    //Getter/setter methods
    public void updatePosition(int x, int y, boolean horizontal) { //Sets the ship position
        this.horizontal = horizontal;
        for (int i = 0; i < type.size; i++) {
            position.clear();
            position.add(horizontal ? new Point(x + i, y) : new Point(x, y + i));
        }
    }

    public boolean isHorizontal(){
        return horizontal;
    }
    public boolean isSunk() { return m_iHitPositions.size == type.size; }   //Returns true if this ship has been sunk, false otherwise

    public Array<Point> getPosition() {
        return position;
    }

    /**
     * Constructor. Requires a sprite for the hit image and one for the non-hit image
     * @param sShipHit  LibGDX sprite to use when drawing the center part of the ship (hit image)
     * @param sShipOK   LibGDX sprite to use when drawing the outside edge of the ship
     */
    public Ship(Sprite sShipHit, Sprite sShipOK)
    {
        m_sShipHitSprite = sShipHit;
        m_sShipOKSprite = sShipOK;
        reset();    //Set default values
    }

    /**
     * Resets this ship to default state (off board and not hit)
     */
    public void reset()
    {
        beenHit = false;
        m_iHitPositions = new Array<>();
    }

    /**
     * Fires at this ship. Returns true and marks as hit if hit, returns false on miss
     * @param x board x position to test and see if on ship
     * @param y board y position to test and see if on ship
     * @return  true on hit, false on miss
     */
    public boolean fireAtShip(int x, int y)
    {
        beenHit = true;
        if(isHit(x, y)) {
            m_iHitPositions.add(new Point(x, y));
            return true;
        }

        return false;
    }

    private boolean isHit(int x, int y) {
        for(int i = 0; i < type.size; i++)
            if(position.get(i).x == x && position.get(i).y == y)
                return true;

        return false;
    }

    /**
     * Draw the ship to the specified SpriteBatch
     * @param bHidden   if ship should be considered "hidden," that is only tiles that have previously been hit should be drawn
     * @param bBatch    LibGDX batch to draw the ship to
     */
    public void draw(boolean bHidden, Batch bBatch)
    {
        if(m_sShipHitSprite == null || m_sShipOKSprite == null) return; //Don't draw if no sprite textures

        if(isSunk())    //Change ship's appearance slightly if it's been sunk
        {
            m_sShipHitSprite.setColor(1, 1, 1, SHIP_SUNK_ALPHA); //Draw at half alpha
            m_sShipOKSprite.setColor(1, 1, 1, SHIP_SUNK_ALPHA);
        }

        if(bHidden) //Only draw ship tiles that have been hit (enemy board generally)
        {
            for(Point point : m_iHitPositions)
            {
                //Draw both center and edge for hit tiles
                float x = point.x * m_sShipHitSprite.getWidth();
                float y = point.y * m_sShipHitSprite.getHeight();
                m_sShipOKSprite.setPosition(x, y);
                m_sShipOKSprite.draw(bBatch);
                m_sShipHitSprite.setPosition(x, y);
                m_sShipHitSprite.draw(bBatch);
            }
        }
        else    //Draw all tiles, including ones that haven't been hit (generally player board)
        {
            //Draw all ship tiles first
            for (Point point : position)
            {
                //Draw horizontally or vertically depending on our rotation
                m_sShipOKSprite.setPosition(point.x * m_sShipOKSprite.getWidth(), point.y * m_sShipOKSprite.getHeight());
                m_sShipOKSprite.draw(bBatch);
            }
            //Draw image for tiles on the ship that have been hit
            for(Point point : m_iHitPositions)
            {
                m_sShipHitSprite.setPosition(point.x * m_sShipHitSprite.getWidth(), point.y * m_sShipHitSprite.getHeight());
                m_sShipHitSprite.draw(bBatch);
            }
        }

        if(isSunk())
        {
            m_sShipOKSprite.setColor(Color.WHITE);  //Reset to default color since other ships share this sprite
            m_sShipHitSprite.setColor(Color.WHITE);
        }
    }
}


























