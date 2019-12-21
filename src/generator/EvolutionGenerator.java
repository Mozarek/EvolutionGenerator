package generator;

import Map.WorldMap;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EvolutionGenerator extends JPanel {

    private int animationDelay;
    private ScheduledExecutorService executor;

    public EvolutionGenerator() throws IOException, ParseException {
        GameConfig gameConfig = new GameConfig("src/config.json");
        WorldMap map = new WorldMap(gameConfig);
        Game game = new Game(map , gameConfig);

        this.animationDelay = gameConfig.animationDelay;
        AnimationPanel animationPanel = new AnimationPanel(map , gameConfig);

        add(animationPanel);

        Runnable animateNextFrame = () -> {
            game.nextDay();
            repaint();
        };

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(animateNextFrame, 0, animationDelay, TimeUnit.MILLISECONDS);
    }
}
