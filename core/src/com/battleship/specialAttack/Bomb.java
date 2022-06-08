package com.battleship.specialAttack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.battleship.Board.Board;

import java.awt.*;

public class Bomb {
    private Sprite bombSprite;
    public Array<Point> bomb;
    private Point orientation;
    private Point position;
    private static final int BOMB_SIZE = 4;

    public Bomb(Sprite bombSprite){
        this.bombSprite = bombSprite;
        this.bomb = new Array<Point>();
    }


    public Point getOrientation() {
        return orientation;
    }

    public boolean isHorizontal(){
        return orientation.x == 1;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void moveBomb(Point point){
        if (point.x + BOMB_SIZE > Board.BOARD_SIZE){
            point.x = Board.BOARD_SIZE - orientation.x*BOMB_SIZE;
        }
        if (point.y + BOMB_SIZE > Board.BOARD_SIZE){
            point.y = Board.BOARD_SIZE - orientation.y*BOMB_SIZE;
        }
        updateBombPosition(point);
    }

    public void updateBombPosition(Point point){
        bomb.clear();
        for (int i = 0; i < BOMB_SIZE; i++) {
            bomb.add(new Point(point.x + orientation.x*i, point.y + orientation.y*i));
        }
    }
    public void draw(boolean bHidden, Batch bBatch, Point offset){
        if(position == null || bombSprite == null) return;
        if(bHidden){
            for (Point point : bomb){
                if(!isHorizontal()) {
                    bombSprite.setTexture(new Texture("verticalBomb.png"));
                    bombSprite.setPosition(point.x * bombSprite.getWidth() + offset.x, point.y * bombSprite.getHeight() + offset.y);
                    bombSprite.setScale(1,4);
                }
                else{
                    bombSprite.setTexture(new Texture("verticalBomb.png"));
                    bombSprite.setPosition(point.x * bombSprite.getWidth() + offset.x, point.y * bombSprite.getHeight() + offset.y);
                    bombSprite.setScale(4,1);
                }
            }
        }
        else{
            for (Point point : bomb){
                if(!isHorizontal()){
              //      bombSprite.setTexture(new Texture());
                }
            }
        }
    }
}
