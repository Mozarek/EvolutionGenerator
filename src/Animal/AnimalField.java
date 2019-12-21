package Animal;

import Animal.Animal;
import utils.Vector2d;

import java.util.*;

public class AnimalField implements IEnergyChangedObserver{
    private Comparator<Animal> comparator = Animal::totalOrderByEnergy;
    private TreeSet<Animal> animals = new TreeSet<>(comparator);
    public final Vector2d position;
    private Grass grass;

    public AnimalField(Animal animal){
        this.animals.add(animal);
        this.position = animal.position;
    }

    public AnimalField(Grass grass){
        this.grass = grass;
        this.position = grass.position;
    }

    public boolean isEmpty() {
        return animals.isEmpty() && grass == null;
    }

    public boolean hasAnimals() {
        return !animals.isEmpty();
    }

    public List<Animal> getTwoAnimalsWithLargestEnergy(){
        if(animals.size()<2){
            return null;
        }
        List<Animal> result = new ArrayList<>();
        Iterator<Animal> it = animals.iterator();
        for(int i=0;i<2;i++){
            result.add(it.next());
        }

        return result;
    }

    public List<Animal> getOneAnimalWithLargestEnergyWithTies(){
        if(animals.isEmpty()){
            return null;
        }

        List<Animal> result = new ArrayList<>();
        Iterator<Animal> it = animals.iterator();
        result.add(it.next());
        int energy = result.get(0).energy;

        //TODO: refactor this part
        while(it.hasNext()){
            Animal a = it.next();
            if(a.energy == energy){
                result.add(a);
            }else{
                break;
            }
        }

        return result;
    }

    public void remove(Animal animal) {
        if(!animals.contains(animal)){
            animals.contains(animal);
            throw new IllegalArgumentException("Animal to be removed was not found on field");
        }
        animals.remove(animal);
    }

    public void add(Animal animal){
        animals.add(animal);
    }

    public boolean contains(Animal animal){
        return animals.contains(animal);
    }


    public static List<Animal> getAnimalList(AnimalField animalField) {
        return new ArrayList<Animal>(animalField.animals);
    }

    public Grass getGrass(){
        return grass;
    }

    public void putGrass(Grass grass){
        if(this.grass!=null){
            this.grass = grass;
        }else{
            throw new IllegalArgumentException("Grass is already placed on field");
        }
    }

    public void removeGrass(){
        this.grass = null;
    }

    @Override
    public String toString(){
        if(animals.size()>0){
            return animals.first().toString();
        }else{
            return grass.toString();
        }
    }

    @Override
    public void energyChanged(Animal animal , int previousEnergy) {
        int newEnergy = animal.energy;
        animal.energy = previousEnergy;
        animals.remove(animal);
        animal.energy = newEnergy;
        animals.add(animal);
    }
}
