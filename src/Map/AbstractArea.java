package Map;

import utils.Rectangle;
import utils.Vector2d;

import java.util.List;
import java.util.Vector;

public abstract class AbstractArea {
    public final Rectangle rectangleBoundary;
    private UnoccupiedPositionWatcher watcher;

    AbstractArea(Rectangle outerBoundary) {
        this.rectangleBoundary = outerBoundary;
    }

    void setWatcher(UnoccupiedPositionWatcher watcher){
        this.watcher = watcher;
    }

    List<Vector2d> getAllPositions(){
        List<Vector2d> result = new Vector<>();
        for(int posX = rectangleBoundary.lowerLeft.x; posX<= rectangleBoundary.upperRight.x; posX++){
            for(int posY = rectangleBoundary.lowerLeft.y; posY<= rectangleBoundary.upperRight.y; posY++){
                Vector2d position =new Vector2d(posX , posY);
                if(isInBoundary(position)) {
                    result.add(position);
                }
            }
        }
        return result;
    }

    public abstract boolean isInBoundary(Vector2d position);

    public Vector2d getRandomUnoccupiedPosition(){
        return watcher.getRandomUnoccupiedPosition();
    }

    public void positionTaken(Vector2d position){
        watcher.positionTaken(position);
    }

    public void positionEmptied(Vector2d position){
        watcher.positionEmptied(position);
    }
}
