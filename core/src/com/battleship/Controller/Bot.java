package com.battleship.Controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.battleship.MyBattleshipGame;
import com.battleship.Board.Ship.ShipType;
import com.battleship.Board.Ship.ShotState;

import java.awt.*;

import static com.battleship.Board.Board.BOARD_SIZE;

public class Bot extends BoardController {
    enum ATTACK_STATE {
        SEEK,
        DESTROY;
    }
    Array<Array<Boolean>> map;
    ATTACK_STATE currentMode;
    int checkLength;
    Point lastHit;
    Point firstHit;
    int destroyDirection;
    Array<Point> direction;
    int shipLength;
    Array<Integer> sizesOfDestroyedShips;
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

        currentMode = ATTACK_STATE.SEEK;
        sizesOfDestroyedShips = new Array<>();
        direction = new Array<>(new Point[]{new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0)});
        checkLength = 1;

        map = new Array<>();
        for (int i = 0; i < boardSize; i++){
            Array<Boolean> temp = new Array();
            for(int j = 0; j < BOARD_SIZE; j++)
                temp.add(false);
            map.add(temp);
        }
    }

    public void placeShipRandom(){
        int xPos, yPos;
        boolean horizontal;
        for (ShipType type : ShipType.values()){
            do {
                horizontal = MathUtils.randomBoolean();

                xPos = MathUtils.random(0, boardSize - (horizontal ? 1 : 0) * type.getSize() - 1);
                yPos = MathUtils.random(0, boardSize - (horizontal ? 0 : 1) * type.getSize() - 1);

            }while(!board.placeShip(new Point(xPos, yPos), type, horizontal));
        }

        for(int i = 0; i < 12 ; i ++) {
            for (int j = 0; j < 12; j++) {
                System.out.print((board.shipPositions.get(j).get(i) == null ? "*" : board.shipPositions.get(j).get(i).getType().id) + " ");
            }
            System.out.println();
        }
    }

    public ShotState attack(BoardController opponent) {
        Point firePos;
        if(currentMode == ATTACK_STATE.SEEK)
            firePos = seek();
        else
            firePos = destroy();

        for(int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++)
                System.out.print((map.get(j).get(i) == true ? 1 : "*") + "  ");

            System.out.println();
        }

        System.out.println();
        System.out.println();

        ShotState result = opponent.fireAtPos(firePos);
        updateAlgo(result, firePos);

        return result;
    }

    private Point seek() {
        int x, y;
        do {
            x = MathUtils.random(0, BOARD_SIZE - 1);
            y = MathUtils.random(0, BOARD_SIZE - 1);

            while(y % 2 != x % 2)
                y = MathUtils.random(0, BOARD_SIZE - 1);
        } while (!checkValid(x, y));

        map.get(x).set(y, true);
        return new Point(x, y);
    }

    private Point destroy() {
        System.out.println("Destroy");

        int x = lastHit.x + direction.get(destroyDirection).x;
        int y = lastHit.y + direction.get(destroyDirection).y;

        map.get(x).set(y, true);
        return new Point(x, y);
    }

    private boolean checkValid(int x, int y){
        if(x >= 0 && y >= 0 && x < boardSize && y < boardSize)
            return !map.get(x).get(y);

        return false;
    }

    private void updateAlgo(ShotState result, Point firePos) {
        if(result == ShotState.HIT) {
            for(int i = -1 ; i <= 1; i++)
                for(int j = -1; j <= 1; j++)
                    if(checkValid(firePos.x + j, firePos.y + i))
                        map.get(firePos.x + j).set(firePos.y + i, true);

        }

        if(currentMode == ATTACK_STATE.SEEK && result == ShotState.HIT){
            currentMode = ATTACK_STATE.DESTROY;

            lastHit = firePos;
            firstHit = firePos;
            shipLength = 1;

            destroyDirection = -1;
            do {
                destroyDirection = (destroyDirection + 1) % 4;
            }while (checkValid(lastHit.x + direction.get(destroyDirection).x, lastHit.y + direction.get(destroyDirection).x));
        } else if(currentMode == ATTACK_STATE.DESTROY) {
            switch (result) {
                case HIT:
                    shipLength ++;
                    lastHit = firePos;
                    break;
                case MISS:
                    if(lastHit.equals(firstHit)) {
                        destroyDirection = (destroyDirection + 1) % 4;
                    } else {
                        lastHit = firstHit;
                        destroyDirection = (destroyDirection + 2) % 4;
                    }
                    break;
                case SUNK:
                    System.out.println("Ship destroyed");
                    currentMode = ATTACK_STATE.SEEK;
                    break;
            }
        }
    }
}
