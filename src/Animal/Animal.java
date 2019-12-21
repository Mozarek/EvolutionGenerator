package Animal;

import Map.IPositionChangedObserver;
import Map.WorldMap;
import Genotype;
import utils.MapRotation;
import utils.Vector2d;

public class Animal {
    private IPositionChangedObserver positionObserver;
    private IEnergyChangedObserver energyObserver;
    private WorldMap map;
    public int energy;
    public int age;
    private final int ID;
    Vector2d position;
    MapRotation orientation;
    private Genotype genotype;

    private void notifyPositionChanged(Vector2d oldPos){
        positionObserver.updatePosition(oldPos ,  this);
    }
    private void notifyEnergyChanged(int previousEnergy){ energyObserver.energyChanged(this , previousEnergy);}

    public Animal(WorldMap map , Vector2d pos , int startEnergy){
        this(map , pos , new Genotype() , startEnergy);
    }

    public Animal(Animal mother, Animal father , Vector2d position , int energy){
        this(mother.map , position , new Genotype(mother.genotype , father.genotype) , energy);
    }

    public void setEnergyObserver(IEnergyChangedObserver energyObserver){
        this.energyObserver = energyObserver;
    }

    public int takeEnergyForChild(){
        int energyForChild = energy/4;
        energy -= energyForChild;
        return energyForChild;
    }

    public Animal(WorldMap map , Vector2d pos , Genotype genotype , int startEnergy){
        this.map = map;
        this.positionObserver = map;
        this.position = pos;
        this.genotype = genotype;
        this.energy = startEnergy;
        this.orientation = genotype.getRandomRotation();
        this.age = 0;
        this.ID = map.getNextAnimalID();
    }


    public void rotate(){
        orientation = orientation.add(genotype.getRandomRotation());
    }

    public void move(){
        Vector2d oldPosition= new Vector2d(this.position.x , this.position.y);
        Vector2d newPosition = position.add(orientation.toUnitVector());
        this.position = map.toBoundedPosition(newPosition);

        notifyPositionChanged(oldPosition);
    }

    public boolean isDead(){
        return energy<=0;
    }

//    public Animal reproduce(Animal other){
//        throw new IllegalStateException("NOT IMPLEMENTED");
//        //TODO: IMPLEMENT
//        return null;
//    }

    public static int totalOrderByEnergy(Animal a1 , Animal a2){
        if( a1.energy != a2.energy)
            return a1.energy - a2.energy>0 ? 1 : -1;
        else{
            if(a1.equals(a2)){
                return 0;
            }else{
                return a1.ID - a2.ID>0 ? 1 : -1;
            }
        }
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Animal){
            return ((Animal) other).ID == this.ID;
        }else{
            return false;
        }
    }

    public Vector2d getPosition(){
        return position;
    }

    public void takeEnergy(int energy){
        changeEnergy(-energy);
    }

    @Override
    public String toString(){
        return "A";
    }

    public void eatGrass(int grassEnergy) {
        changeEnergy(grassEnergy);
    }

    void changeEnergy(int delta){
        int previousEnergy = energy;
        energy += delta;
        notifyEnergyChanged(previousEnergy);
    }
}
