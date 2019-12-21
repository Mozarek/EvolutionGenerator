package generator;

import Animal.Animal;
import Animal.AnimalField;
import Animal.Grass;
import Map.WorldMap;
import utils.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private WorldMap map;
    private List<Animal> currentAnimals;
    private final GameConfig config;
    private final int grassRegeneratedPerDayJungle;
    private final int grassRegeneratedPerDaySavanna;

    Game(WorldMap map, GameConfig config){
        this.map = map;
        this.currentAnimals = map.getAnimals();
        this.config = config;

        this.grassRegeneratedPerDayJungle = 1;
        this.grassRegeneratedPerDaySavanna = 1;
    }

    private void collectEnergy(){
        currentAnimals.forEach(a->a.takeEnergy(config.moveEnergy));
    }

    private void removeDeadAnimals(){
        List<Animal> aliveAnimals = new ArrayList<>();

        for(Animal a : currentAnimals){
            if(a.isDead()){
                map.remove(a);
            }else{
                aliveAnimals.add(a);
            }
        }
        currentAnimals = aliveAnimals;
    }

    private void rotateAll(){
        currentAnimals.forEach(Animal::rotate);
    }

    private void moveAll(){
        currentAnimals.forEach(Animal::move);
    }

    private void eatGrass(){
        for(AnimalField field : map.getFields()){
            if(field.hasAnimals() && field.getGrass()!=null) {
                Grass grass = field.getGrass();
                List<Animal> strongestAnimals = field.getOneAnimalWithLargestEnergyWithTies();
                int energyPerAnimal = grass.energy/strongestAnimals.size();
                strongestAnimals.forEach(a -> a.eatGrass(energyPerAnimal));
                map.remove(grass);
            }
        }
    }

    private void reproduceAll(){
        for(AnimalField field : map.getFields()){
            List<Animal> parents = field.getTwoAnimalsWithLargestEnergy();
            Vector2d childPosition = map.getSurroundingEmptyPosition(field.position);
            if(parents!=null && childPosition!=null){
                Animal mother = parents.get(0);
                Animal father = parents.get(1);
                int childEnergy = mother.takeEnergyForChild() + father.takeEnergyForChild();
                Animal child = new Animal(mother , father , childPosition , childEnergy);
                map.place(child);
            }
        }
    }

    private void draw(){

    }

    public void nextDay(){
        currentAnimals = map.getAnimals();
        collectEnergy();
        removeDeadAnimals();
        rotateAll();
        moveAll();
        eatGrass();
        map.generateDailyGrasses();
        reproduceAll();
    }
}
