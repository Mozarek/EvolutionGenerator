package Map;

import utils.Vector2d;

class SavannaArea extends AbstractArea {
    private AbstractArea mapBoundary;
    private AbstractArea jungleBoundary;

    SavannaArea(AbstractArea mapBoundary , AbstractArea jungleBoundary){
        super(mapBoundary.rectangleBoundary);
        this.mapBoundary = mapBoundary;
        this.jungleBoundary = jungleBoundary;
    }

    @Override
    public boolean isInBoundary(Vector2d position) {
        return mapBoundary.isInBoundary(position) && !jungleBoundary.isInBoundary(position);
    }
}
