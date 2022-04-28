package Ship_types;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.sun.org.apache.xpath.internal.objects.XString;

public class Ship_Cruiser extends Ship {
    public int getSize(){
        return Ship.SIZE_CRUISER;
    }
    public String getName(){
        return Ship.NAME_CRUISER;
    }
    public int getType(){
        return Ship.TYPE_CRUISER;
    }
    public Ship_Cruiser(Sprite sCenter, Sprite sEdge) {
        super(sCenter,sEdge) //we get the value from the SHIP CLASS
    }
}
