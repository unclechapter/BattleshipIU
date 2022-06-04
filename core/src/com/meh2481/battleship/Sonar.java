package com.meh2481.battleship;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class Sonar {
    private Sprite sonarSprite;
    private Array<Integer> foundShipPositions;
    private int SONAR_SIZE = 2;
    private int xPos, yPos;

    public void setPosition(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    private Sonar(Sprite sonar){
        this.sonarSprite = sonar;
    }

    private void findShip(int xPos, int yPos){
        
    }

    public void moveSonar(int xPos, int yPos){
       if (SONAR_SIZE + xPos > Board.BOARD_SIZE){
           xPos = Board.BOARD_SIZE - SONAR_SIZE;
       }
       if (SONAR_SIZE + yPos > Board.BOARD_SIZE){
           yPos = Board.BOARD_SIZE - SONAR_SIZE;
       }
       setPosition(xPos, yPos);
    }
    public void placeSonar(int xPos, int yPos){
        moveSonar(xPos, yPos);
    }

    
}
