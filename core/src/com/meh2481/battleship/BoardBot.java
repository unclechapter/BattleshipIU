package com.meh2481.battleship;

import com.badlogic.gdx.graphics.Texture;

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
}
