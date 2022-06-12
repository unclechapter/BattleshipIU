package com.battleship.Board.Ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.battleship.Board.Board;
import com.battleship.Manager.AssetManager;
import com.battleship.Manager.Game.GameManager;
import com.battleship.Interface.Observer;


import java.awt.*;
import java.util.HashMap;

/**
 *
 * Abstract class for ships that handles interactions between ships, facilitates launching missiles at ships, etc
 * See Ship_* classes for implementations of this class that contain ship-specific info (such as size, name, etc)
 */
public class Ship {
    private static final HashMap<String, Sprite> spriteAsset = setupAsset(AssetManager.getManager().requestAsset(Ship.class));
    private static Sprite hitSprite;    //Image for the ship being hit (inner image)
    private ShipType type;
    private Sprite m_sShipOKSprite; //Image for the ship being ok (outer image)
    private Array<Point> m_iHitPositions; //Array of positions that have been hit
    private Array<Point> shipPoints; //Array of the all the points of the ship
    private Point orientation; // orientation vector
    private Observer observer;

    //How faded out a sunk ship looks
    public static final float SHIP_SUNK_ALPHA = 0.65f;

    /**
     * Constructor. Requires a sprite for the hit image and one for the non-hit image
     */
    public Ship(ShipType type) {
        this.type = type;
        m_sShipOKSprite = type.getSprite();
        m_sShipOKSprite.setOrigin(37, 37);

        reset();    //Set default values
    }

    /**
     * Resets this ship to default state (off board and not hit)
     */
    public void reset() {
        m_iHitPositions = new Array<>();
        shipPoints = new Array<>();
        orientation =  new Point(1, 0);
    }

    private static HashMap<String, Sprite> setupAsset(HashMap<String, Sprite> sprites) {
        hitSprite = sprites.get("hitSprite");

        return sprites;
    }

    //Getter/setter methods
    public Point getOrientation(){
        return orientation;
    }

    public Point getPosition() {
        if(!shipPoints.isEmpty())
            return shipPoints.get(0);
        else
            return null;
    }

    public ShipType getType() {
        return type;
    }

    public boolean isHorizontal(){
        return orientation.x == 1;
    }

    //Returns true if this ship has been sunk, false otherwise
    public boolean isSunk() {
        return m_iHitPositions.size == type.getSize();
    }

    public boolean beenHit() {
        return m_iHitPositions.size > 0;
    }

    public void updatePosition(Point position, boolean horizontal) { //Sets the ship position
        attachObserver();

        this.orientation = horizontal ? new Point(1, 0) : new Point(0, 1);

        shipPoints = new Array<>();
        for (int i = 0; i < type.getSize(); i++)
            shipPoints.add(new Point(position.x + this.orientation.x * i, position.y + this.orientation.y * i));
    }


    /**
     * Fires at this ship. Returns true and marks as hit if hit, returns false on miss
     * @return  true on hit, false on miss
     */
    public ShotState fireAtShip(Point point) {
        m_iHitPositions.add(new Point(point));

        if(isSunk()) {
            notifyObserver();
            return ShotState.SUNK;
        } else
            return ShotState.HIT;
}

    public void attachObserver(){
        this.observer = GameManager.getManager();
    }

    public void notifyObserver(){
        observer.updateSunkShip(this.type);
    }
    /**
     * Draw the ship to the specified SpriteBatch
     * @param bHidden   if ship should be considered "hidden," that is only tiles that have previously been hit should be drawn
     * @param bBatch    LibGDX batch to draw the ship to
     */
    public void draw(boolean bHidden, Batch bBatch, Point offset) {
        if(getPosition() == null || hitSprite == null || m_sShipOKSprite == null)
            return;

        int tileSize = Board.TILE_SIZE;
        Point drawPosition = new Point(getPosition().x * tileSize + offset.x, getPosition().y * tileSize + offset.y);

        m_sShipOKSprite.setRotation(isHorizontal() ? 0 : 90);

        //Change ship's appearance slightly if it's been sunk
        if(isSunk()) {
            hitSprite.setColor(1, 1, 1, SHIP_SUNK_ALPHA); //Draw at half alpha
            m_sShipOKSprite.setColor(1, 1, 1, SHIP_SUNK_ALPHA);

            m_sShipOKSprite.setPosition(drawPosition.x, drawPosition.y);
            m_sShipOKSprite.draw(bBatch);
        }

        //Only draw ship tiles that have been hit (enemy board generally)
        if(!bHidden) {
            //Draw all ship tiles first
                m_sShipOKSprite.setPosition(drawPosition.x, drawPosition.y);
                m_sShipOKSprite.draw(bBatch);
        }

        for(Point point : m_iHitPositions) {
            hitSprite.setPosition(point.x * tileSize + offset.x, point.y * tileSize + offset.y);
            hitSprite.draw(bBatch);
        }

        if(isSunk()) {
            //Reset to default color since other ships share this sprite
            hitSprite.setColor(Color.WHITE);
        }
    }
}


























