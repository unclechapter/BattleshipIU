package Ship_types;

import com.badlogic.gdx.graphics.Texture;
import java.awt.*;

public class Board_Player extends Board
{
    private Point m_ptCurPos;   //Hold onto the current position of the ship we're placing
    private int m_iPlacing;    //For handling ship placement - current ship we're placing

    /**
     * Construct a new board for the player to use (also see Board.Board())
     * @param txBg      Background texture for board
     * @param txMiss    Texture for drawing where the player has missed guesses
     * @param txCenter  Texture for drawing the center of ships when they are hit
     * @param txEdge    Texture for drawing the edge of ships
     */
    public Board_Player(Texture txBg, Texture txMiss, Texture txCenter, Texture txEdge)
    {
        super(txBg, txMiss, txCenter, txEdge);  //Construct default class
        m_ptCurPos = new Point(-1,-1);          //Reset current position we're placing a ship
        m_iPlacing = -1;    //Not currently placing a ship
    }

    public void startPlacingShips()
    {
        m_iPlacing = 0;
    }   //Init ship placement by placing ship 0 (first ship)

    /**
     * Place a ship on the board and prepare to place next ship
     * @param xPos  X board position to place the upper left of the ship
     * @param yPos  Y board position to place the upper left of the ship
     * @return      true if this is the last ship and placement should stop, false otherwise
     */
    public boolean placeShip(int xPos, int yPos)
    {
        if(m_iPlacing >= 0 && m_iPlacing < m_lShips.size)
        {
            moveShip(xPos, yPos);
            boolean bOKPlace = true;
            //Make sure this isn't overlapping other ships
            for(int i = 0; i < m_lShips.size; i++)
            {
                if(i == m_iPlacing)
                    continue;
                if(m_lShips.get(i).checkOverlap(m_lShips.get(m_iPlacing)))
                {
                    bOKPlace = false;
                    break;
                }
            }
            //We're good
            if(bOKPlace)
                m_iPlacing++;   //Go to next ship
        }
        if(m_iPlacing >= m_lShips.size)
            return true;
        return false;
    }

    /**
     * Move the current ship we're going to place next
     * @param xPos  x board position to move the upper left of the ship to
     * @param yPos  y board position to move the upper left of the ship to
     */
    public void moveShip(int xPos, int yPos)
    {
        m_ptCurPos.x = xPos;
        m_ptCurPos.y = yPos;
        if(m_iPlacing >= 0 && m_iPlacing < m_lShips.size)
        {
            //Check and be sure we're not off the edge of the map.
            Ship sPlace = m_lShips.get(m_iPlacing);
            if(sPlace.isHorizontal())
            {
                if(sPlace.getSize() + xPos > BOARD_SIZE)
                    xPos = BOARD_SIZE - sPlace.getSize();   //Set position in the map if we're off it
            }
            else
            {
                if(sPlace.getSize() + yPos > BOARD_SIZE)
                    yPos = BOARD_SIZE - sPlace.getSize();
            }
            m_lShips.get(m_iPlacing).setPosition(xPos, yPos);
        }
    }

    /**
     * Rotate the current ship we're placing (rotate horizontal if vertical & vice versa)
     */
    public void rotateShip()
    {
        if(m_iPlacing >= 0 && m_iPlacing < m_lShips.size)
        {
            Ship sPlace = m_lShips.get(m_iPlacing);
            sPlace.setHorizontal(sPlace.isVertical());
            //Make sure we're not off the map after rotating by moving to the current position
            moveShip(m_ptCurPos.x, m_ptCurPos.y);
        }
    }
}