package utils;

import java.util.Map;
import java.util.Random;

public enum MapRotation {
    NORTH(0, "N" ,new Vector2d(0,1)),
    NORTHEAST(1, "NE" ,new Vector2d(1,1)),
    EAST(2, "E" ,new Vector2d(1,0)),
    SOUTHEAST(3, "SE" ,new Vector2d(1,-1)),
    SOUTH(4, "S" ,new Vector2d(0,-1)),
    SOUTHWEST(5, "SW" ,new Vector2d(-1,-1)),
    WEST(6, "W" ,new Vector2d(-1,0)),
    NORTHWEST(7, "NW" ,new Vector2d(-1,1));

    private final int rotation;
    private final String representation;
    private final Vector2d unitVector;

    private MapRotation(int rotation , String representation , Vector2d unitVector){
        this.rotation = rotation;
        this.representation = representation;
        this.unitVector = unitVector;
    }

    @Override
    public String  toString(){
        return this.representation;
    }

    public MapRotation add(MapRotation other){
        int newRotation = (other.rotation + this.rotation)%MapRotation.values().length;
        return MapRotation.values()[newRotation];
    }

    public Vector2d toUnitVector(){
        return this.unitVector;
    }
}
