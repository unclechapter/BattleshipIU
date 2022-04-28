package Ship_types;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ship_Cruiser extends Ship {
    public static getSize(){
        return Ship.SIZE_CRUISER;
    }
    public static getName(){
        return Ship.NAME_CRUISER;
    }
    public static getSize(){
        return Ship.TYPE_CRUISER;
    }
    public Ship_Cruiser(Sprite sCenter, Sprite sEdge) {
        super(sCenter,sEdge) //we get the value from the SHIP CLASS
    }
}
