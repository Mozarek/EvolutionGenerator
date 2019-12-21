package generator;

import Animal.AnimalField;
import Map.WorldMap;

import javax.swing.*;
import java.awt.*;

public class AnimationPanel extends JPanel {
    private WorldMap map;
    private GameConfig config;
    private int width;
    private int height;
    public int WINDOW_WIDTH = 1000;
    public int WINDOW_HEIGHT = 750;

    private static final Color healthyAnimal = new Color(255,0,0);
    private static final Color dyingAnimal = new Color(0, 0, 0);
    private static final Color grass = new Color(12, 84, 23);
    private static final Color jungleColor = new Color(12,200 , 23);
    private static final Color defaultColor = new Color(217,255, 219);


    public AnimationPanel(WorldMap map , GameConfig config) {
        this.map = map;
        this.config = config;
        this.width = map.mapArea.width;
        this.height = map.mapArea.height;
        int preferredWidth = (WINDOW_WIDTH/width) * width;
        int preferredHeight = (WINDOW_HEIGHT/height) * height;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int fieldWidth = getWidth() / width;
        int fieldHeight = getHeight() / height;

        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(defaultColor);
        g.fillRect(0,0 , getWidth() , getHeight());
        g.setColor(jungleColor);
        g.fillRect(map.jungleArea.rectangleBoundary.lowerLeft.x*fieldWidth,
                map.jungleArea.rectangleBoundary.lowerLeft.y*fieldHeight,
                map.jungleArea.rectangleBoundary.getWidth()*fieldWidth,
                map.jungleArea.rectangleBoundary.getHeight()*fieldHeight);

        for(AnimalField field : map.getFields()){
            int xPos = field.position.x*fieldWidth;
            int yPos = field.position.y*fieldHeight;
            g.setColor(getColor(field));
            g.fillRect(xPos , yPos , fieldWidth , fieldHeight);
        }
    }

    private Color getColor(AnimalField field){
        if(field.hasAnimals()){
            return healthyAnimal;
        }else if(field.getGrass()!=null){
            return grass;
        }else{
            throw new IllegalArgumentException("Field is empty");
        }
    }
}
