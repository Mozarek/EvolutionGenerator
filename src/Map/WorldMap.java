package Map;

import Animal.Animal;
import Animal.AnimalField;
import Animal.Grass;
import generator.*;
import utils.MapRotation;
import utils.Vector2d;

import java.util.*;
import java.util.stream.Collectors;

public class WorldMap implements IPositionChangedObserver {
    private final GameConfig config;
    public final MapArea mapArea;
    public final AbstractArea jungleArea;
    public final AbstractArea savannaArea;
    private int nextAnimalID = 0;

    private LinkedHashMap<Vector2d , AnimalField> fieldMap = new LinkedHashMap<>();

    //private jungleUnoccupied


    public WorldMap(GameConfig config) {
        this.config = config;
        this.mapArea = new MapArea(
                new Vector2d(0,0),
                new Vector2d(config.mapWidth - 1, config.mapHeight - 1)
            );
        this.jungleArea = new JungleArea(mapArea , config.jungleRatio);
        this.savannaArea = new SavannaArea(mapArea , jungleArea);
        this.jungleArea.setWatcher(new UnoccupiedPositionWatcher(jungleArea));
        this.savannaArea.setWatcher(new UnoccupiedPositionWatcher(savannaArea));

        generateStartingAnimals(config);
        generateStartingGrasses(config);
    }

    private void generateStartingAnimals(GameConfig config){
        Random randomGenerator = new Random();

        for(int i=0;i<config.startAnimals;i++){
            int posX = randomGenerator.nextInt(config.mapWidth);
            int posY = randomGenerator.nextInt(config.mapHeight);
            place(new Animal(this , new Vector2d(posX , posY) , config.startEnergy));
        }
    }

    private void generateStartingGrasses(GameConfig config){
        for(int i=0;i<config.startGrasses ; i+=2){
            if(!generateGrassIn(jungleArea) || !generateGrassIn(savannaArea)){
                break;
            }
        }
    }

    private boolean generateGrassIn(AbstractArea area){
        Vector2d position = area.getRandomUnoccupiedPosition();
        if(position != null){
            place(new Grass(position , config.grassEnergy));
            return true;
        }else{
            return false;
        }
    }

    public void generateDailyGrasses(){
        for(int i=0;i<config.dailyJungleGrass;i++){
            if(!generateGrassIn(jungleArea)){
                break;
            }
        }
        for(int i=0;i<config.dailySavannaGrass;i++){
            if(!generateGrassIn(savannaArea)){
                break;
            }
        }

    }

    public void place(Animal animal){
        AnimalField field = fieldMap.get(animal.getPosition());
        if(field!=null){
            field.add(animal);
            animal.setEnergyObserver(field);
        }else {
            field = new AnimalField(animal);
            animal.setEnergyObserver(field);
            fieldMap.put(field.position , field);

            jungleArea.positionTaken(field.position);
            savannaArea.positionTaken(field.position);
        }
    }

    public void place(Grass grass){
        if(isOccupied(grass.position)){
            throw new IllegalArgumentException("Grass cannot be placed on field with animals");
        }else {
            AnimalField field = new AnimalField(grass);
            fieldMap.put(field.position , field);

            jungleArea.positionTaken(field.position);
            savannaArea.positionTaken(field.position);
        }
    }

    //TODO: refactor remove and place into one function for both Animal and Grass objects
    public void remove(Animal animal){
        AnimalField field = fieldMap.get(animal.getPosition());
        if(field==null){
            throw new IllegalArgumentException("No field with specified animal was found on its position");
        }

        field.remove(animal);

        if(field.isEmpty()){
            fieldMap.remove(field.position);
            jungleArea.positionEmptied(field.position);
            savannaArea.positionEmptied(field.position);
        }
    }

    public void remove(Grass grass){
        AnimalField field = fieldMap.get(grass.position);
        if(field==null){
            throw new IllegalArgumentException("No field with specified animal was found on its position");
        }

        field.removeGrass();

        if(field.isEmpty()){
            fieldMap.remove(field.position);
            jungleArea.positionEmptied(field.position);
            savannaArea.positionEmptied(field.position);
        }
    }

    public Vector2d toBoundedPosition(Vector2d pos) {
        return mapArea.toBoundedPosition(pos);
    }

    public Vector2d getSurroundingEmptyPosition(Vector2d position){
        for(MapRotation rotation : MapRotation.values()){
            Vector2d neighbouringPosition = position.add(rotation.toUnitVector());
            if(!isOccupied(neighbouringPosition)){
                return neighbouringPosition;
            }
        }
        return null;
    }

    @Override
    public void updatePosition(Vector2d fromPosition, Animal animal) {
        AnimalField fieldFrom = fieldMap.get(fromPosition);
        if(!fieldFrom.hasAnimals()){
            throw new IllegalArgumentException("Field found on + " + fromPosition + " is empty");
        }

        fieldFrom.remove(animal);

        if(fieldFrom.isEmpty()){
            fieldMap.remove(fromPosition);
            jungleArea.positionEmptied(fromPosition);
            savannaArea.positionEmptied(fromPosition);
        }
        place(animal);
    }

    public List<AnimalField> getFields(){
        return new ArrayList<>(fieldMap.values());
    }

    public List<Animal> getAnimals(){
        return fieldMap.values().stream()
                .map(AnimalField::getAnimalList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public boolean isOccupied(Vector2d currentPosition) {
        return fieldMap.containsKey(currentPosition);
    }

    public Object objectAt(Vector2d currentPosition) {
        return fieldMap.get(currentPosition);
    }

    public int getNextAnimalID(){
        return nextAnimalID++;
    }

    public boolean isGrassAt(Vector2d position) {
        AnimalField field = fieldMap.get(position);
        if(field!= null){
            return field.getGrass()!=null;
        }else{
            return false;
        }
    }

    public void removeGrassFrom(Vector2d position) {
        if(!isGrassAt(position)){
            throw new IllegalArgumentException("No grass to remove on position: " + position);
        }

        fieldMap.get(position).removeGrass();
        if(!isOccupied(position)){
            jungleArea.positionEmptied(position);
            savannaArea.positionEmptied(position);
        }
    }
}
