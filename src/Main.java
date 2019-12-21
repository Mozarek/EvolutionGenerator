import Map.WorldMap;
import generator.EvolutionGenerator;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static java.lang.Integer.*;

public class Main{

    public static void main(String[] args) throws IOException, ParseException {
	// write your code here
        JFrame.setDefaultLookAndFeelDecorated(false);

        //Create and set up the window.
        JFrame frame = new JFrame("EvolutionGenerator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new EvolutionGenerator();
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }
}
