package com.battleship.specialAttack;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import java.awt.*;

public class Shield {
    public Sprite shieldSprite;
    private Array<Point> shieldPosition;
    private boolean shieldActivate;

    public Shield(Sprite shieldSprite){
        this.shieldSprite = shieldSprite;
    }

    public void setPosition(Point point){
        shieldPosition.add(point);
    }

    public boolean activateShield(){
        shieldActivate = true;
        return true;
    }
    public boolean deactivateShield(Point point){
        for (Point shieldPoint : shieldPosition){
            if (shieldPoint.equals(point)){
                shieldPosition.removeValue(point, false);
            }
        }
        shieldActivate = false;
        return true;
    }
}
