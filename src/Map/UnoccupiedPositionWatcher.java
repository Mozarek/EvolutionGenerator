package Map;

import utils.Vector2d;

import java.util.*;

/*
   Keeps a list of all unoccupied positions in watchedArea.
   Implements removing and adding positions from the list in O(1) time complexity
   Getting random position from the list is deterministic,
    returns positions with uniform distribution and also in O(1) time
 */
public class UnoccupiedPositionWatcher {
    private final AbstractArea watchedArea;
    private HashMap<Vector2d , Integer> indexMap = new HashMap<>();
    private List<Vector2d> positionsList;
    private Random randomGenerator = new Random();

    UnoccupiedPositionWatcher(AbstractArea area){
        this.watchedArea = area;
        this.positionsList = area.getAllPositions();
        for(int i=0;i<positionsList.size();i++){
            indexMap.put(positionsList.get(i), i);
        }
    }

    public void positionEmptied(Vector2d position){
        if(watchedArea.isInBoundary(position)){
            if(indexMap.containsKey(position)){
                throw new IllegalArgumentException("Position: " + position + " is already unoccupied");
            }

            positionsList.add(position);
            indexMap.put(position , positionsList.size()-1);
        }

        if(indexMap.size()!=positionsList.size()){
            throw new IllegalStateException("HashMap and List are not in sync, sizes differ");
        }
    }

    public void positionTaken(Vector2d position){
        if(watchedArea.isInBoundary(position)){
            if(!indexMap.containsKey(position)){
                throw new IllegalArgumentException("Position: " + position + " is already occupied");
            }

            int index = indexMap.get(position);
            int lastIndex = positionsList.size()-1;
            if(lastIndex == index){
                indexMap.remove(position);
                positionsList.remove(index);
            }else {
                Vector2d lastPosition = positionsList.get(lastIndex);

                //swap
                positionsList.set(index, lastPosition);
                positionsList.remove((int) lastIndex);
                indexMap.remove(lastPosition);
                indexMap.remove(position);
                indexMap.put(lastPosition, index);
            }

            if(indexMap.size()!=positionsList.size()){
                throw new IllegalStateException("HashMap and List are not in sync, sizes differ");
            }
        }

    }

    public Vector2d getRandomUnoccupiedPosition(){
        if(positionsList.size()==0){
            return null;
        }
        return positionsList.get(randomGenerator.nextInt(positionsList.size()));
    }
}
