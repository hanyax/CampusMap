package hw9;
import javax.swing.*;
import java.awt.*;
import hw8.CampusMap;

/**
 * Campus Map program that allow user to find shortest path
 * between two uw campus buildings by entering their short name
 *
 */
public class CampusPathsMain {
	
	  /** 
	   * Create window, viewer, and maps and run the program 
	   */
	  public static void main(String[] args) {		    
		  JFrame frame = new JFrame("Campus Map");
		  
		  // Create the map model, controller and viewer and add a couple of things to it
		  JPanel control = new MapControl();
		  frame.add(control,BorderLayout.CENTER);
		  frame.pack();
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  frame.setVisible(true);
	  }
}
