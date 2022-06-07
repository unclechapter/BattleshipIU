package com.meh2481.battleship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class BoardBot extends Board{
    /**
     * Constructor for creating a Board class object
     *
     * @param txBg     Background board texture to use as the backdrop when drawing the board
     * @param txMiss   Texture used for drawing guesses that missed
     * @param txCenter Texture used for drawing the central portion of ships that have been hit
     * @param txEdge   Texture used for drawing the edge of ships
     */
    public BoardBot(Texture txBg, Texture txMiss, Texture txCenter, Texture txEdge) {
        super(txBg, txMiss, txCenter, txEdge);
    }

    public void placeShipRandom(){
        int xPos, yPos;
        boolean horizontal;
        for (Ship ship : m_lShips){
            do {
                horizontal = MathUtils.randomBoolean();
                if (horizontal) {
                    xPos = MathUtils.random(0, BOARD_SIZE - ship.type.size);
                    yPos = MathUtils.random(0, BOARD_SIZE - 1);
                } else {
                    xPos = MathUtils.random(0, BOARD_SIZE - 1);
                    yPos = MathUtils.random(0, BOARD_SIZE - ship.type.size);
                }
            }while(!placeShip(xPos, yPos, ship, horizontal));
        }

        for (Ship ship : m_lShips){
            System.out.println(ship.getPosition());
        }
    }

}


    /**
     * Position ships randomly around the board (unintelligent, but non-overlapping)
     */
    /*public void placeShipRandom(Ship ship){
        int xPos, yPos;
        if (ship.isHorizontal()){
            xPos = MathUtils.random(0,BOARD_SIZE - ship.type.size);
            yPos = MathUtils.random(0, BOARD_SIZE - 1);
        }
        else {
            xPos = MathUtils.random(0,BOARD_SIZE-1);
            yPos = MathUtils.random(0, BOARD_SIZE - ship.type.size);
        }
        ship.updatePosition(xPos, yPos, MathUtils.randomBoolean());
    }

    */
