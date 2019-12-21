package Map;

import utils.Rectangle;
import utils.Vector2d;

public class JungleArea extends AbstractArea {

    JungleArea(MapArea mapBoundary , double jungleRatio){
        super(getRectangle(mapBoundary,jungleRatio));
    }

    private static Rectangle getRectangle(MapArea mapBoundary , double jungleRatio){
        Vector2d middle = new Vector2d(
                (mapBoundary.rectangleBoundary.lowerLeft.x + mapBoundary.rectangleBoundary.upperRight.x)/2 ,
                (mapBoundary.rectangleBoundary.lowerLeft.y + mapBoundary.rectangleBoundary.upperRight.y)/2
        );
        int jungleWidth = (int)(mapBoundary.width*jungleRatio);
        int jungleHeight = (int)(mapBoundary.height*jungleRatio);

        Vector2d lowerLeft = middle.subtract(new Vector2d(jungleWidth/2 , jungleHeight/2));
        Vector2d upperRight = lowerLeft.add(new Vector2d(jungleWidth-1 , jungleHeight-1));

        return new Rectangle(lowerLeft , upperRight);
    }

    @Override
    public boolean isInBoundary(Vector2d position) {
        return rectangleBoundary.isInside(position);
    }
}
