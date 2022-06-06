package com.meh2481.battleship;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Mark on 1/15/2016.
 */
public class ShipBattleship extends Ship
{
    public int getSize() { return Ship.SIZE_BATTLESHIP; }
    public String getName() { return Ship.NAME_BATTLESHIP; }
    public int getType() { return Ship.TYPE_BATTLESHIP; }

    public ShipBattleship(Sprite sCenter, Sprite sEdge)
    {
        super(sCenter, sEdge);
    }
}
