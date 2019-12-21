package Animal;

import utils.MapRotation;

import java.util.Map;
import java.util.Random;

public class Genotype {
    private static final byte length = 32;
    public final byte[] genes = new byte[length];
    private static Random randomGenerator = new Random();

    public Genotype(){
        for(int i = 0; i< MapRotation.values().length ; i++){
            genes[i] = (byte)i;
        }
        for(int i=MapRotation.values().length ; i<length ; i++){
            genes[i] = (byte)randomGenerator.nextInt(MapRotation.values().length);
        }
        //Arrays.sort(genes);
    }

    public Genotype(Genotype parentGenotype1 , Genotype parentGenotype2){
        for(int i =0; i<MapRotation.values().length ; i++){
            genes[i] = (byte)i;
        }
        int midCut = (length- MapRotation.values().length)/2 + MapRotation.values().length;

        for(int i=MapRotation.values().length ; i<midCut ; i++){
            genes[i] = parentGenotype1.genes[i];
        }

        for(int i=midCut ; i<length ; i++){
            genes[i] = parentGenotype1.genes[i];
        }
        //Arrays.sort(genes);
    }

    public MapRotation getRandomRotation(){
        int index = randomGenerator.nextInt(length);
        return MapRotation.values()[genes[index]];
    }
}
