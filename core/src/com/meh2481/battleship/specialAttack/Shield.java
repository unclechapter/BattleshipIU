package com.meh2481.battleship.specialAttack;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import java.awt.*;

public class Shield {
    private Sprite shieldSprite;
    private Array<Point> shieldPosition;
    private boolean shieldActivate;

    public Shield(Sprite shieldSprite){
        this.shieldSprite = shieldSprite;
    }

    public void setPosition(int x, int y){
        Point point = new Point(x,y);
        shieldPosition.add(point);
    }

    public boolean activateShield(int x, int y){
        shieldActivate = true;
        return true;
    }
    public boolean deactivateShield(int x, int y){
        for (Point point : shieldPosition){
            if (point.x == x && point.y == y){
                shieldPosition.removeValue(point, false);
            }
        }
        shieldActivate = false;
        return true;
    }
    
}
