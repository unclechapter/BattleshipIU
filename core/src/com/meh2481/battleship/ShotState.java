package com.meh2481.battleship;

public enum ShotState {
    HIT,
    MISS,
    SUNK;
    private ShipType type;

    public void putShip(ShipType type){
        if (this == SUNK){
            this.type = type;
        }
    }
    
    public ShipType getType() {
        return type;
    }

}
