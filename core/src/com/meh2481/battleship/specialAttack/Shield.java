package com.meh2481.battleship.specialAttack;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import java.awt.*;

public class Shield {
    private Sprite shieldSprite;
    private Array<Point> shieldPositions;

    public Shield(Sprite shieldSprite){
        this.shieldSprite = shieldSprite;
    }

    public boolean activateShield(int x, int y){
        Point shieldPoint = new Point(x,y);
        shieldPositions.add(shieldPoint);
        return true;
    }
    public boolean deactivateShield(int x, int y){
        for (Point point : shieldPositions){
            if (point.x == x && point.y == y){
                shieldPositions.removeIndex(shieldPositions.indexOf(point, false));
            }
        }
        return false;
    }
    
}
