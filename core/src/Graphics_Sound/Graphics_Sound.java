package Graphics_Sound;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import java.awt.*;

public class Graphics_Sound {

    //Images
    private Texture m_txShipCenterImage;
    private Texture m_txMissImage;
    private Texture m_txShipEdgeImage;
    private Texture m_txBoardBg;
    private Texture m_txFireCursorSm;
    private Texture m_txFireCursorLg;
    //Sounds
    private Sound m_sMissSound;
    private Sound m_sHitSound;
    private Sound m_sSunkSound;
    private Sound m_sWinSound;
    private Sound m_sLoseSound;
    //Music
    private Music m_mPlacingMusic;
    private Music m_mPlayingMusic;

}
