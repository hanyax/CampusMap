package hw9;
import javax.swing.*;
import hw8.CampusMap;
import hw8.CampusPath;

import java.awt.*;

/**
 * Graphical viewer for a path object 
 */
public class PathView extends JPanel{
	private final FoundPath path;
	
	/**
	* Initialize this as a viewer linked to the given model.
	* @param model  The model we are watching
	*/
	public PathView(FoundPath path) {
		this.path = path;
	}
	
	/**
	 * React when notified of a change by a model by repainting this view
	 */	
	public void notifyViewer() {
		repaint();
	}
	
	/**
	 * Repaint the map by request foundpath to repaint itself 
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Image img = Toolkit.getDefaultToolkit()
					.getImage("../cse331/src/hw8/data/campus_map.jpg");
		int panelHeight = getHeight();
		int panelWidth = getWidth();
		g2.drawImage(img, 0, 0, panelWidth, panelHeight, this);
		path.paintPath(g2, panelWidth, panelHeight, img);
	}
}
