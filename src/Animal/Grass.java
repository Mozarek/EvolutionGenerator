package Animal;

import utils.Vector2d;

public class Grass {
    public final Vector2d position;
    public final int energy;

    public Grass(Vector2d position, int energy){
        this.energy = energy;
        this.position = position;
    }

    @Override
    public String toString() {
        return "G";
    }
}
