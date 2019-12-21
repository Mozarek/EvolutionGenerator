package Map;

import utils.Rectangle;
import utils.Vector2d;

public class MapArea extends AbstractArea {
    public final int width;
    public final int height;

    MapArea(Vector2d lowerLeft , Vector2d upperRight){
        super(new Rectangle(lowerLeft , upperRight));
        Vector2d delta = upperRight.subtract(lowerLeft);
        this.width = delta.x;
        this.height = delta.y;
    }

    Vector2d toBoundedPosition(Vector2d position){
        position = position.subtract(rectangleBoundary.lowerLeft);
        position = new Vector2d((position.x+width)%width , (position.y+height)%height);
        position = position.add(rectangleBoundary.lowerLeft);
        return position;
    }

    @Override
    public boolean isInBoundary(Vector2d position) {
        return rectangleBoundary.isInside(position);
    }
}
