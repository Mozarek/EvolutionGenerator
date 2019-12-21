package generator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;


public class GameConfig {
    public final int mapWidth;
    public final int mapHeight;
    public final double jungleRatio;

    public final int startEnergy;
    public final int moveEnergy;
    public final int grassEnergy;

    public final int startAnimals;

    public final int startGrasses = 15;
    public final int dailyJungleGrass = 1;
    public final int dailySavannaGrass = 1;
    public final int animationDelay = 200;

    GameConfig(int mapWidth, int mapHeight, double jungleRatio, int startAnimals, int startEnergy, int moveEnergy, int grassEnergy) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.jungleRatio = jungleRatio;
        this.moveEnergy = moveEnergy;
        this.grassEnergy = grassEnergy;
        this.startEnergy = startEnergy;
        this.startAnimals = startAnimals;
    }

    GameConfig(String filePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filePath));
        JSONObject initialData = (JSONObject) obj;

        this.mapHeight = (int) (long) initialData.get("height");
        this.mapWidth = (int) (long) initialData.get("width");
        this.startEnergy = (int) (long) initialData.get("startEnergy");
        this.moveEnergy = (int) (long) initialData.get("moveEnergy");
        this.grassEnergy = (int) (long) initialData.get("grassEnergy");
        this.jungleRatio = (double) initialData.get("jungleRatio");
        this.startAnimals = (int) (long) initialData.get("startAnimals");
    }
}
