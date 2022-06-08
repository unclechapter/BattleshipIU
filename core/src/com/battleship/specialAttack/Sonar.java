package com.battleship.specialAttack;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.battleship.Board.Board;
import com.battleship.Ship;

import java.awt.*;

public class Sonar {
    private Sprite sonarSprite;
    private Array<Point> foundShipPositions;
    public static final int SONAR_RADIUS = 1;
    private Point point;
    private Array<Array<Ship>> shipPositions;
    
    public Sonar(Sprite sonar, Array<Array<Ship>> ships){
        this.sonarSprite = sonar;
        this.shipPositions = ships;
    }

    public void setPosition(Point point) {
        this.point = point;
    }

    public Array<Point> findShip(){
        for (int i = -SONAR_RADIUS; i <= SONAR_RADIUS; i++){
            for (int j = -SONAR_RADIUS; j<= SONAR_RADIUS; j++){
                if (shipPositions.get(point.x).get(point.y)!= null)
                    foundShipPositions.add(point);
            }
        }
        return foundShipPositions;
    }

    
}
