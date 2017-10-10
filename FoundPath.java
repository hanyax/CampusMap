package hw9;

import hw8.CampusMap;
import hw8.CampusPath;
import java.awt.*;
import java.util.*;
import hw9.PathView;

/**
 * FoundPath represent a Campus path between two given buildings
 * and it can print out itself with marked start and destination
 * on a given JPanel
 * 
 * Specification field:
 * @specfield path : CampusPath List of the passed-by location's coordinate and distance between them 
 * @specfield map : CampusMap The collection of location and path that represent a campus map
 * @specfield viewers : the list of viewer that is used to repaint this path
 * 
 * Abstract Invariant:
 * A path must have at least 0 turn and at least 0 distance
 */
public class FoundPath {
	/*	Abstraction Function:
	 * 	AF(r) = FoundPath p such that:
	 * 			p.path = r.path
	 * 			p.map = r.totalCost
	 * 			p.viwer = r.viewers
	 * 
	 *	Representation Invariant:	
	 *	CampusMap != null || viewers != null
	 */
	
	private void checkRep() {
		assert(map != null);
		assert(viewers != null);
	}
	
	private ArrayList<PathView> viewers;
	private final CampusMap map;
	private CampusPath path;
	private boolean reset;
	private int findTime;
	
	/**
	 * Construct FoundPath will a null campusPath
	 * @modified this
	 */
	public FoundPath() {
		map = new CampusMap("src/hw8/data/campus_buildings.dat", "src/hw8/data/campus_paths.dat");
		viewers = new ArrayList<PathView>();
		path = null;
		findTime = 0;
		reset = false;
		
		checkRep();
	}
	
	/**
	 * find a new path, store a in the path and notify viewers for repaint
	 * @param start the start point of path
	 * @param dest	the destination point of path 
	 * @modified this
	 */
	public void findPath(String start, String dest) {
		findTime++;
		path = map.findPath(start, dest);
		for(PathView v:viewers) {
			v.notifyViewer();
		}
	}
	
	/**
	 * Add a viewer to this Path
	 * @param v the viewer to be added
	 * @modified this
	 */
	public void addViewer(PathView v) {
		viewers.add(v);
	}
	
	/**
	 * Set the rest field to true to repaint the original map
	 * @modified: this
	 */
	public void reset() {
		this.reset = true;
		for(PathView v:viewers) {
			v.notifyViewer();
		}
	}
	
	/**
	 * Print the original map image to rest the app
	 * @param g			the graphics context where the painting should take place
	 * @param width		the width of the panel to be painted
	 * @param height	the height of the panel to be painted
	 * @param img		the campus map image to be painted
	 */
	public void paintReset(Graphics g, int width, int height, Image img) {
		g.drawImage(img, 0, 0, width, height, null);
		this.reset = false;
	}
	
	/**
	 * Print the prompt message for user to re-enter location information
	 * @param g			the graphics context where the painting should take place
	 * @param width		the width of the panel to be painted
	 * @param height	the height of the panel to be painted
	 */
	public void paintReenter(Graphics g, int width, int height) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawString("Entered Location is not in the map, pleae enter again", width/3, height/2);
	}
	
	/**
	 * Paint the path on the JPanel if path is null
	 * If path is null, call paintReenter to print prompt for re enter location
	 * If reset is true, print original map image
	 * 
	 * @param g 		the graphics context where the painting should take place
	 * @param width 	the width of the panel to be painted
	 * @param height	the height of the panel to be painted
	 * @param img		the campus map image to be painted
	 */
	public void paintPath(Graphics g, int width, int height, Image img) {
		if (reset) {
			paintReset(g, width, height, img);
		} else {
			if (path != null) {
				double startPointX = path.getStartLocationX(0).doubleValue();
				double startPointY = path.getStartLocationY(0).doubleValue();
				double destPointX = path.getDestLocationX(path.getTotalTurns()).doubleValue();
				double destPointY = path.getDestLocationY(path.getTotalTurns()).doubleValue();
				int[] zoomed = zoomedPosition((int) startPointX, (int)startPointY, (int) destPointX, (int) destPointY);
				g.drawImage(img, 0, 0, width, height, zoomed[0], zoomed[2], zoomed[1], zoomed[3], null);
				
				
				int mapHeight = zoomed[3] - zoomed[2];
				int mapWidth = zoomed[1] - zoomed[0];
				
				int startXScaled = getX(startPointX-zoomed[0], width, mapWidth);
				int startYScaled = getY(startPointY-zoomed[2], height, mapHeight);
				int destXScaled	 = getX(destPointX-zoomed[0], width, mapWidth);
				int desttYScaled = getY(destPointY-zoomed[2], height, mapHeight);
				g.setColor(Color.RED);
							
				g.fillOval(startXScaled-10, startYScaled-10, 20, 20);
				g.fillOval(destXScaled-10, desttYScaled-10, 20, 20);
				
				for (int i = 0; i <= path.getTotalTurns(); i++) {
					int startX = getX(path.getStartLocationX(i).doubleValue()-zoomed[0], width, mapWidth);
					int startY = getY(path.getStartLocationY(i).doubleValue()-zoomed[2], height, mapHeight);
					int destX = getX(path.getDestLocationX(i).doubleValue()-zoomed[0], width, mapWidth);
					int destY = getY(path.getDestLocationY(i).doubleValue()-zoomed[2], height, mapHeight);
					g.drawLine(startX, startY, destX, destY);
				}
			} else {
				if (findTime != 0) {
					paintReenter(g,width,height);
				}
			}
		}
	}
		
	/**
	 * Compute the zoomed-in portion of the map to display undistorted when a path is painted
	 * @param startPointX	path start x coordinate 
	 * @param startPointY	path start y coordinate 
	 * @param destPointX	path destination x coordinate 
	 * @param destPointY	path destination y coordinate 
	 * @return the array of zoomed-in x and y location 
	 */
	private int[] zoomedPosition(int startPointX, int startPointY, int destPointX, int destPointY) {
		int left = 0;
		int right = 0;
		int top = 0;
		int bottom = 0;
		
		if (startPointY <= destPointY) {
			top = startPointY ;
			bottom = destPointY;
		} else {
			top = destPointY ;
			bottom = startPointY;
		}
		
		if (startPointX <= destPointX) {
			left = startPointX ;
			right = destPointX ;
		} else {
			left = destPointX ;
			right = startPointX ;
		}
		
		int height = bottom - top;
		int currentWidth = right - left;
		double width = height * 3 / 2.0; 
		double increment = ((width - currentWidth)/2.0);
		left = (int) ((left - increment)/1.25 - 30);
		right = (int) ((right + increment)*1.25 - 30);
		top = (int) (top/1.2);
		bottom = (int) (bottom*1.2);

		return new int[] {left, right, top, bottom}; 
	}
		
	/**
	 * Get the x coordinate for drawing relative to the Jpanel
	 * @param x the coordinate in the actual map
	 * @param width the width of Jpanel
	 * @param mapWidth the width of the actual map
	 * @return the x coordinate relative to the Jpanel
	 */
	private int getX(double x, int width, int mapWidth) {
		return (int) (x * width / mapWidth);
	}
	
	/**
	 * 
	 * @param 	y the coordinate in the actual map
	 * @param 	height the height of Jpanel
	 * @param 	mapHeight the width of the actual map
	 * @return 	the y coordinate relative to the Jpanel
	 */
	private int getY(double y, int height, int mapHeight) {
		return (int) (y * height / mapHeight);
	}
}	
