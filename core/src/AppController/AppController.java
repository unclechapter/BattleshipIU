package AppController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.pfa.Graph;

public class AppController {
    private int scrWidth = Gdx.graphics.getWidth();
    private int scrHeight = Gdx.graphics.getHeight();
    
    public void update() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            if(Gdx.graphics.isFullscreen())
                Gdx.graphics.setWindowedMode(scrWidth / 2, scrHeight / 2);
            else
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
    }
}
