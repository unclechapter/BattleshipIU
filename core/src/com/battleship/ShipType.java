package com.battleship;

import java.util.HashMap;

public enum ShipType {
    CARRIER     (6, "Carrier", 5),
    BATTLESHIP  (5, "Battleship", 4),
    CRUISER     (4, "Cruiser", 3),
    SUBMARINE   (3, "Submarine", 3),
    DESTROYER   (2, "Destroyer", 2);

    int id;
    int size;
    String name;
    ShipType(int id, String name, int size) {
        this.id = id;
        this.size = size;
        this.name = name;
    }
}
