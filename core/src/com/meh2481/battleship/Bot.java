package com.meh2481.battleship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Bot extends BoardController{
    /**
     * Constructor for creating a Board class object
     *
     * @param txBg     Background board texture to use as the backdrop when drawing the board
     * @param txMiss   Texture used for drawing guesses that missed
     * @param txCenter Texture used for drawing the central portion of ships that have been hit
     * @param txEdge   Texture used for drawing the edge of ships
     */
    public Bot(Texture txBg, Texture txMiss, Texture txCenter, Texture txEdge) {
        super(txBg, txMiss, txCenter, txEdge);
    }

    public void placeShipRandom(){
        int xPos, yPos;
        boolean horizontal;
        for (Ship ship : m_lShips){
            do {
                horizontal = MathUtils.randomBoolean();

                xPos = MathUtils.random(0, boardSize - (horizontal ? 1 : 0) *ship.type.size - 1);
                yPos = MathUtils.random(0, boardSize - (horizontal ? 0 : 1)* ship.type.size - 1);

            }while(!board.placeShip(xPos, yPos, ship, horizontal));
        }
    }
}
