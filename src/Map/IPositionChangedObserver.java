package Map;

import Animal.Animal;
import utils.Vector2d;

public interface IPositionChangedObserver {
    public void updatePosition(Vector2d fromPosition , Animal animal);
}
