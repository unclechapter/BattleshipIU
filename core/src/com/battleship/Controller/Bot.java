package com.battleship.Controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.battleship.Controller.BoardController;
import com.battleship.MyBattleshipGame;
import com.battleship.Ship;
import com.battleship.ShipType;
import com.battleship.ShotState;

import java.awt.*;
import java.util.Random;

import static com.battleship.Board.Board.BOARD_SIZE;

public class Bot extends BoardController {
    Array<Array<Integer>> map;
    /**
     * Constructor for creating a Board class object
     *
     * @param txBg     Background board texture to use as the backdrop when drawing the board
     * @param txMiss   Texture used for drawing guesses that missed
     * @param txCenter Texture used for drawing the central portion of ships that have been hit
     * @param txEdge   Texture used for drawing the edge of ships
     */
    public Bot(Texture txBg, Texture txMiss, Texture txCenter, Texture txEdge) {
        super(txBg, txMiss, txCenter, txEdge, MyBattleshipGame.playerBoardOffset);

        map = new Array<>();
        for (int i = 0; i < boardSize; i++){
            Array<Integer> temp = new Array();
            temp.setSize(BOARD_SIZE);
            map.add(temp);
        }

        System.out.println(map);
    }

    public void placeShipRandom(){
        int xPos, yPos;
        boolean horizontal;
        for (ShipType type : ShipType.values()){
            do {
                horizontal = MathUtils.randomBoolean();

                xPos = MathUtils.random(0, boardSize - (horizontal ? 1 : 0) * type.getSize() - 1);
                yPos = MathUtils.random(0, boardSize - (horizontal ? 0 : 1) * type.getSize() - 1);

                System.out.println(new Point(xPos, yPos) + " " + horizontal + " " + type.getSize());

            }while(!board.placeShip(new Point(xPos, yPos), type, horizontal));

            for(int i = 0; i < 12 ; i ++) {
                for (int j = 0; j < 12; j++) {
                    System.out.print((board.shipPositions.get(j).get(i) == null ? "*" : board.shipPositions.get(j).get(i).getType().id) + " ");
                }
                System.out.println();
            }

            System.out.println();
            System.out.println();
        }
    }

    public ShotState attack(BoardController opponent) {
        System.out.println("Bot attacking");

        opponent.fireAtPos(new Point(MathUtils.random(0, boardSize - 1), MathUtils.random(0, boardSize - 1)));
        return null;
    }
}
