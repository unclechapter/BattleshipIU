package com.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Queue;
import com.battleship.Board.Board;
import com.battleship.Board.BoardPlayer;
import com.battleship.Controller.Bot;
import com.battleship.Controller.Player;

import java.awt.*;

import static com.battleship.MyBattleshipGame.*;

public class GameManager implements Observer{
    private static GameManager manager;
    public static Point mouseCursorTile;
    public static GameMode currentMode;
    public static PlayerTurnState playerTurnState;

    private Player player;
    private Bot bot;
    private final int PLAYER_WON = 0;
    private final int ENEMY_WON = 1;
    public static BoardPlayer playerBoard;
    public static Board botBoard;

    private final double PLAYERHITPAUSE = 1.25; //Pause in seconds after player launches a missile, to let them read the result

    private Texture m_txShipCenterImage;
    private Texture m_txMissImage;
    private Texture m_txShipEdgeImage;
    private Texture m_txBoardBg;

    private final String MISS_STR = "Miss";
    private final String HIT_STR = "Hit";
    private final String SUNK_STR = "Sunk ";

    //Sounds
    private Sound m_sHitSound;
    private Sound m_sMissSound;
    private Sound m_sSunkSound;
    private Sound m_sWinSound;
    private Sound m_sLoseSound;

    //Music
    private Music m_mPlacingMusic;
    private Music m_mPlayingMusic;

    private Queue<ShipType> sunkShip;

    public static GameManager createGameManager() {
        if(manager == null) {
            manager = new GameManager();
            return manager;
        }
        else return null;
    }

    private GameManager(){
        m_txShipCenterImage = new Texture("hit.png");
        m_txShipEdgeImage = new Texture("ship_edge.png");
        m_txMissImage = new Texture("miss.png");
        m_txBoardBg = new Texture("map.png");
        //Create game logic classes
        player = new Player(m_txBoardBg, m_txMissImage, m_txShipCenterImage, m_txShipEdgeImage);
        playerBoard = (BoardPlayer) player.getBoard();

        bot = new Bot(m_txBoardBg, m_txMissImage, m_txShipCenterImage, m_txShipEdgeImage);
        botBoard = bot.getBoard();

        sunkShip = new Queue<>();

        m_sHitSound = Gdx.audio.newSound(Gdx.files.internal("hit.ogg"));
        m_sSunkSound = Gdx.audio.newSound(Gdx.files.internal("sunk.ogg"));
        m_sMissSound = Gdx.audio.newSound(Gdx.files.internal("miss.ogg"));
        m_sWinSound = Gdx.audio.newSound(Gdx.files.internal("youWin.mp3"));
        m_sLoseSound = Gdx.audio.newSound(Gdx.files.internal("youLose.mp3"));

        m_mPlacingMusic = Gdx.audio.newMusic(Gdx.files.internal("beginningMusic.mp3"));
        m_mPlayingMusic = Gdx.audio.newMusic(Gdx.files.internal("mainTheme.mp3"));

        mouseCursorTile = new Point(0, 0);

        //Start music
        m_mPlayingMusic.setLooping(true);
        m_mPlacingMusic.setLooping(true);
    }

    public static GameManager getManager() {
        return manager;
    }

    public Music getM_mPlacingMusic(){
        return m_mPlacingMusic;
    }

    public void update(){
        mouseHover();

        //Update our state machine
        if(currentMode == GameMode.PLAYERTURN && m_iModeCountdown > 0)  //Player turn waiting for enemy turn
        {
            if(System.nanoTime() >= m_iModeCountdown)   //Timer expired; set to enemy turn
            {
                m_iModeCountdown = 0;   //Reset timer
                if(botBoard.boardCleared())    //Game over; player won
                {
                    currentMode = GameMode.GAMEOVER;
                    m_iCharWon = PLAYER_WON;
                    //Play winning music
                    m_mPlayingMusic.stop();
                    m_sWinSound.play();
                }
                else
                {
                    currentMode = GameMode.BOTTURN;   //Change mode
                    m_iEnemyGuessTimer = (long) (System.nanoTime() + MODESWITCHTIME * NANOSEC);  //Set timer for the pause before an enemy guess
                }
            }
        }
        else if(currentMode == GameMode.BOTTURN)  //Enemy turn
        {
            if(m_iEnemyGuessTimer > 0)  //If we're waiting for the enemy firing animation
            {
                if(System.nanoTime() >= m_iEnemyGuessTimer) //If we've waited long enough
                {
                    //EnemyAI.Guess gHit = m_aiEnemy.guess(m_bPlayerBoard); //Make enemy AI fire at their guessed position

                    //Play the appropriate sound
                    //if(gHit == EnemyAI.Guess.HIT)
                    m_sHitSound.play();
                    //else if(gHit == EnemyAI.Guess.MISS)
                    m_sMissSound.play();
                    /*else*/ if(!playerBoard.boardCleared())
                    m_sSunkSound.play();

                    m_iModeCountdown = (long)(System.nanoTime() + MODESWITCHTIME * MyBattleshipGame.NANOSEC);    //Start countdown for switching to player's turn
                    m_iEnemyGuessTimer = 0; //Stop counting down
                }
            }
            else if(m_iModeCountdown > 0)   //If we're waiting until countdown is done for player's turn
            {
                if(System.nanoTime() >= m_iModeCountdown)  //Countdown is done
                {
                    m_iModeCountdown = 0;   //Reset timer
                    if(playerBoard.boardCleared())   //Game over; enemy won
                    {
                        currentMode = GameMode.GAMEOVER;
                        m_iCharWon = ENEMY_WON;
                        //Play losing sound
                        m_mPlayingMusic.stop();
                        m_sLoseSound.play();
                    }
                    else
                    {
                        currentMode = GameMode.PLAYERTURN;  //Switch to player's turn
                    }
                }
            }
        }
    }

    public void updateMouse(Point mouseTile){
        mouseCursorTile = mouseTile;
    }
    public void reset() {
        //Reset boards and game state
        currentMode = GameMode.PLACESHIP;
        playerTurnState = null;
        m_iModeCountdown = 0;
        m_iEnemyGuessTimer = 0;
        player.reset();
        bot.reset();
        //m_aiEnemy.reset();
        player.startPlacingShips();
        bot.placeShipRandom();

        //Start playing music
//        m_mPlayingMusic.stop();
//        m_mPlacingMusic.stop();
//        m_mPlacingMusic.play();
    }

    public void changeDifficulty() {
//        //Show message at top of screen alterting player of this change
//        m_iAIMsgCountdown = System.nanoTime() + (long)(AI_MSG_LEN * NANOSEC);
//        //m_aiEnemy.setHardMode(!m_aiEnemy.isHardMode()); //Switch difficulty of AI
//        //Set to correct message
//        //if(m_aiEnemy.isHardMode())
//        m_sMsgTxt = AI_HARD_STR;
//        //else
//        m_sMsgTxt = AI_EASY_STR;
    }

    public void leftClick(){
        if (currentMode == GameMode.PLACESHIP)   //Placing ships; lock this ship's position and go to next ship
            playerPlacing();
        else if (currentMode == GameMode.PLAYERTURN && m_iModeCountdown == 0){   //Playing; fire at a ship
            if (playerTurnState == null)
                playerTurn();
            if (playerTurnState == PlayerTurnState.PLACESHIELD){
                player.placeShield(mouseCursorTile);
                playerTurnState = null;
            }
        } 
        else if (currentMode == GameMode.GAMEOVER) {//Game over; start a new game
            //Reset boards and game state
            currentMode = GameMode.PLACESHIP;
            player.reset();
            bot.reset();
            //m_aiEnemy.reset();
            player.startPlacingShips();
            bot.placeShipRandom();

            //Start playing music
//            m_mPlayingMusic.stop();
//            m_mPlacingMusic.play();
        }
    }

    public void rightClick() {
        if (currentMode == GameMode.PLACESHIP)   //Rotate ships on RMB if we're currently placing them
            player.rotateShip();
    }

    private void mouseHover(){
        if (currentMode == GameMode.PLACESHIP)   //If the player is currently placing ships, move ship preview to this location
            player.previewShip(mouseCursorTile);
        if (currentMode == GameMode.PLAYERTURN && playerTurnState!=null){
            if (playerTurnState == PlayerTurnState.PLACESHIELD)
                player.previewShield(mouseCursorTile);
            }
        }

    private void playerPlacing() {
        if (player.placeShip(mouseCursorTile)) {
            currentMode = GameMode.PLAYERTURN;//Done placing ships; start playing now. Player always goes first
//                m_mPlacingMusic.stop();
//                m_mPlayingMusic.play();
        }
    }

    private void playerTurn() {
        if (!botBoard.alreadyFired(mouseCursorTile)) {   //If we haven't fired here already
            ShotState shipState = bot.fireAtPos(mouseCursorTile);    //Fire!
            if (shipState != ShotState.MISS) {   //If we hit a ship
                if (sunkShip != null) {  //Sunk a ship
                    if (!bot.getBoard().boardCleared())
                        m_sSunkSound.play();

                    for (ShipType type : sunkShip) {
                        TextPrompt.updateMessage(SUNK_STR + type.name);
                        sunkShip.removeFirst();
                    }
                } else {    //Hit a ship
                    m_sHitSound.play();
                    TextPrompt.updateMessage(HIT_STR);
                }
            } else {   //Missed a ship
                m_sMissSound.play();
                TextPrompt.updateMessage(MISS_STR);
            }
            m_iModeCountdown = (long) (System.nanoTime() + PLAYERHITPAUSE * MyBattleshipGame.NANOSEC);    //Start countdown timer for the start of the enemy turn
        }
    }

    private void botTurn() {

    }

    @Override
    public void updateSunkShip(ShipType shipType){
        sunkShip.addLast(shipType);
    }
}
