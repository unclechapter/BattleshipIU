package com.battleship.specialAttack;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import java.awt.*;

public class Shield {
    public Sprite shieldSprite;
    private Point shieldPosition;
    private boolean shieldActivate;

    public Shield(Sprite shieldSprite){
        this.shieldSprite = shieldSprite;
    }

    public Point getShieldPosition(){
        return shieldPosition;
    }

    public void setPosition(Point point){
        shieldPosition.x = point.x;
        shieldPosition.y = point.y;
    }

    public void activateShield(){
        shieldActivate = true;
    }

    private void deactivateShield(Point point){
        if(shieldPosition.x == point.x && shieldPosition.y == point.y){
            shieldPosition = new Point (-1, -1);
            shieldActivate = false;
        }
    }

    public boolean fireAtPos(Point point){
        if(shieldPosition.x == point.x && shieldPosition.y == point.y && shieldActivate == true){
            deactivateShield(point);
            return true;
        }
        return false;
    }
}
