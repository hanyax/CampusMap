package hw8;

import hw5.DirectedLabeledEdge;
import hw5.DirectedLabeledGraph;
import java.util.*;
import java.io.*;

/**
 * This is a Map application that can find direction between two buildings in UW campus
 */
public class CampusPathMain {
	
	public static void main(String[] args) {
		CampusMap map = new CampusMap("src/hw8/data/campus_buildings.dat", "src/hw8/data/campus_paths.dat");
		Scanner input = new Scanner(System.in);
		printMenu();
		UI(map, chooseOperation(input), input);
	}
	
	/**
	 * Interact with user based on the command they entered and execute command:
	 * 		r to find a route
	 * 		b to see a list of all buildings
	 * 		q to quit
	 * 		m to display the menu
	 * 
	 * @param map 		The CampusMap for finding path
	 * @param answer	The answer user typed in for the command they wanted
	 */
	private static void UI (CampusMap map, String answer, Scanner input) {
		if (answer == null) {
			UI(map, chooseOperation(input), input);
		} else {
			if (!answer.equals("q")) {
				if (answer.equals("b") ||answer.endsWith("m") || answer.equals("r")) {
					if(answer.equals("b")) {
						listBuildings(map);
					} else if (answer.equals("r")){
						findBuildingPath(map, input);
					} else if(answer.startsWith("#") || answer.isEmpty()) {
						System.out.println(answer);
					} else {
						printMenu();
					}
				}  else {
					System.out.println("Unknown option");
					System.out.println();
				}
				UI(map, chooseOperation(input), input);
			}
		}
	}

	/**
	 * Ask user to choose an operate by typing in the operation
	 * @return the command they entered
	 */
	private static String chooseOperation(Scanner input) {
		System.out.print("Enter an option ('m' to see the menu): ");
		String answer = input.nextLine();
		while (answer.isEmpty() || answer.startsWith("#")) {
			if (answer.isEmpty()) { 
				System.out.println(); 
			}else { 
				System.out.println(answer); 
			}
			answer = input.nextLine();
		}
		return answer;
	}
	
	/**
	 * Print the menu that contain's description of commands
	 */
	private static void printMenu() {
		System.out.println("Menu:");
		System.out.println("	r to find a route");
		System.out.println("	b to see a list of all buildings");
		System.out.println("	q to quit");
		System.out.println();
	}
	
	/**
	 * Prompt user to enter the buildings they want to find path for and 
	 * find the closest path between two buildings and print it out in specific format
	 * @param the map used to find paths
	 */
	private static void findBuildingPath(CampusMap map, Scanner input) {
		System.out.print("Abbreviated name of starting building: ");
		String start = input.nextLine();
		System.out.print("Abbreviated name of ending building: ");
		String dest = input.nextLine();
		
		if (map.exist(start) && map.exist(dest)) {
			System.out.println("Path from " + map.getBuildingLongName(start) + 
							  " to " + map.getBuildingLongName(dest) + ":");
			printPath(map, start, dest);
		} else {
			if (!map.exist(start)) {
				System.out.println("Unknown building: " + start);
			} 
			if (!map.exist(dest)){
				System.out.println("Unknown building: " + dest);
			}
			System.out.println();
		}
		
	}
	
	/**
	 * Find and Print out the found path in specific format
	 * @param map 	the map used to find paths
	 * @param start	Start building of the path
	 * @param dest	Destination building of the path
	 */
	private static void printPath(CampusMap map, String start, String dest) {
		CampusPath foundPath = map.findPath(start, dest);
		Double totalDistance = 0.0;
		if (!start.equals(dest)) {
			for (int i = 0; i <= foundPath.getTotalTurns(); i++) {
				Double distance = foundPath.getDistance(i);
				Double startX = foundPath.getStartLocationX(i);
				Double startY = foundPath.getStartLocationY(i);
				
				Double destX = foundPath.getDestLocationX(i);
				Double destY = foundPath.getDestLocationY(i);
				
				String direction = findDirection(startX, startY, destX, destY); 
				totalDistance += distance;
				System.out.println("\t" + "Walk " + String.format("%.0f",distance) +" feet " + 
									direction + " to (" + String.format("%.0f",destX) + ", " + String.format("%.0f",destY) + ")");
			}
		}
		System.out.println("Total distance: " + String.format("%.0f", totalDistance) + " feet");
		System.out.println();
	}
	
	/**
	 * Compute relative direction from start location to destination location 
	 * and return the string representation of that direction 
	 * @param startX 	start location's x coordinate 
	 * @param startY	start location's x coordinate 
	 * @param destX		Destination location's x coordinate 
	 * @param destY		Destination location's x coordinate 
	 * @return the string representation of that direction relative direction from start location to destination location
	 */
	private static String findDirection(Double startX, Double startY, Double destX, Double destY) {
		double x = destX.doubleValue() - startX.doubleValue();
		double y = destY.doubleValue() - startY.doubleValue();
		double angle = Math.atan2(y, x);
		double pi = Math.PI;
		if (pi/8 < angle && angle < 3*pi/8) {
			return "SE";
		} else if (3*pi/8 <= angle && angle <= 5*pi/8) {
			return "S";
		} else if (5*pi/8 < angle && angle < 7*pi/8) {
			return "SW";
		} else if (7*pi/8 <= angle && angle <= pi &&
					angle >= -pi && angle <= -7*pi/8) {
			return "E";
		} else if (angle > -7*pi/8 && angle < -5*pi/8) {
			return "NW";
		} else if (angle >= -5*pi/8 && angle <= -3*pi/8) {
			return "N";
		} else if (angle > -3*pi/8 && angle < -pi/8) {
			return "NE";
		} else {
			return "W";
		}
			
	}
	
	/**
	 * List all the building's short Name and long name in the map in follwing format:
	 * 			shortName : longName
	 * @param map the map used to find paths
	 */
	private static void listBuildings(CampusMap map) {
		List<String> buildingsShort = new ArrayList<>(map.listBuildingsShortName());
		Collections.sort(buildingsShort);
		System.out.println("Buildings:");
		for (int i = 0; i < buildingsShort.size(); i++) {
			System.out.println("\t" + buildingsShort.get(i) + ": " + map.getBuildingLongName(buildingsShort.get(i)));
		}
		System.out.println();
	}
}