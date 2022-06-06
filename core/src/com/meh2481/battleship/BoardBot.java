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

    /**
     * Position ships randomly around the board (unintelligent, but non-overlapping)
     */
    public void placeShipRandom(Ship ship){
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

    public void placeAllShips(){
        for (Ship s : m_lShips)
            s.setPosition(-1, -1);
        for (int i = 0; i < m_lShips.size; i++){
            boolean placing = true;
            while (placing==true){
                placeShipRandom(m_lShips.get(i));
                placing = false;
                for(int j = 0; j < i; j++){
                    if(m_lShips.get(i).checkOverlap(m_lShips.get(j))){    //This ship overlaps another one
                        placing = true;
                        break;
                    }
                }
            }
        }
    }
}
