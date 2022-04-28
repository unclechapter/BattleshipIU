package Ship_types;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ship_Battleship extends Ship {

    public int getSize() {
        return Ship.SIZE_BATTLESHIP;
    }
    public String getName() {
        return Ship.NAME_BATTLESHIP;
    }

    public int getType() {
        return Ship.TYPE_BATTLESHIP;
    }

    public Ship_Battleship(Sprite sCenter, Sprite sEdge) {
        super(sCenter,sEdge);
    }
}
