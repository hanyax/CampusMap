package hw6;
import hw5.DirectedLabeledEdge;
import hw5.DirectedLabeledGraph;
import hw6.MarvelParser.MalformedDataException;

import java.util.*;

// This is where AF and RI would normally go

public class MarvelPaths {
	private static boolean quit = false; // Switch to quit the application
	
	// Prompt user to enter two marvel heroes name and show their comic book connection
	// This application will keep running until user decide to quit
	public static void main(String[] args) {
		//give introduction to the game 
		giveIntro();
		// Build Graph of marvel heros
		DirectedLabeledGraph<String, String> marvelGraph = buildGraph("src/hw6/data/marvel.tsv");
		
		while (!quit) {
			Scanner input = new Scanner(System.in);
			System.out.println("Who is your favorite Marvel Universe character? ");
			String hero1 = input.nextLine();
			System.out.println("Who is your least favorite Marvel Universe character? ");
			String hero2 = input.nextLine();
			ArrayList<String> enteredHeros = checkPath(marvelGraph, hero1, hero2, input);
			printResult(findPath(marvelGraph, enteredHeros), enteredHeros.get(0), enteredHeros.get(1)); 
			askQuit(input);
		}
	}
	
	// Build the marvel character graph from marvel.tsv file and return the graph built
	public static DirectedLabeledGraph<String, String> buildGraph(String fileName) {
		Set<String> characters = new HashSet<String>();
		Map<String, List<String>> books = new HashMap<String, List<String>>();
		DirectedLabeledGraph<String, String> marvelGraph = new DirectedLabeledGraph<String, String>();
		
		try {
			MarvelParser.parseData(fileName, characters, books);
		} catch (MalformedDataException e) {
			e.printStackTrace();
		}
		
		for (String character : characters) {
			marvelGraph.addNode(character);
		}
		for (String book : books.keySet()) {
			for (String parentChar : books.get(book)) {
				for (String childChar : books.get(book)) {
					if(!parentChar.equals(childChar)) {
					 marvelGraph.addEdge(parentChar, childChar, book);
					}
				}
			}
		}
		return marvelGraph;
	}
	
	// Check if the input character is in the graph
	// if not, prompt user to enter the name again
	public static ArrayList<String> checkPath(DirectedLabeledGraph<String, String> graph, String startNode, String endNode, Scanner input) {
		ArrayList<String> result = new ArrayList<String>();
		startNode = contains(graph ,startNode);
		endNode = contains(graph, endNode);
		result.add(startNode);
		result.add(endNode);
		
		if (startNode.equals("false") || endNode.equals("false")) {
			while (startNode.equals("false")) {
				System.out.println("The favorite character you entered is not in the data base, please enter again:");
				startNode = contains(graph, input.nextLine());
			}
			result.set(0, startNode);
			while (endNode.equals("false")) {
				System.out.println("The least favorite character you entered is not in the data base, please enter again:");
				endNode = contains(graph, input.nextLine());
			}
			result.set(1, endNode);
		} 
		return result;
	}
	
	// Find the path between two entered characters from graph built previously and return the path of their connection
	// Return null if there is no path between two characters
	public static List<DirectedLabeledEdge<String, String>> findPath(DirectedLabeledGraph<String, String> graph, ArrayList<String> heros) {
		if (!graph.getAllParents().contains(heros.get(0)) || !graph.getAllParents().contains(heros.get(1))){
			return null;
		} else {
			Queue<String> workList = new LinkedList<String>(); // Nodes to be searched for edge
			// Map that store the node mapped to their path from startNode
			Map<String, ArrayList<DirectedLabeledEdge<String, String>>> paths = new HashMap<String, ArrayList<DirectedLabeledEdge<String, String>>>();
			
			workList.add(heros.get(0));
			paths.put(heros.get(0), new ArrayList<DirectedLabeledEdge<String, String>>());
			
			while(!workList.isEmpty()) {
				// dequeue
				String currentNode = workList.remove();
				// found the endNode, return the path store in the map
				if (currentNode.equals(heros.get(1))) {
					return paths.get(currentNode);
					
				// Not found, add each edge's childNode in the worklist and map 
				} else {
					ArrayList<DirectedLabeledEdge<String, String>> oldPath = paths.get(currentNode);
					
					// Sort the ChildNodes lexicographically 
					ArrayList<String> sortedChildNode = new ArrayList<String>(graph.getAllEdges(currentNode).keySet());
					Collections.sort(sortedChildNode);
					
					// put each childNode of the currentNode into the queue to be searched and map that store their path
					for (String childNode :  sortedChildNode) {
						if (!paths.containsKey(childNode)) {
							// Find lexicographically shortest edge (book)
							ArrayList<DirectedLabeledEdge<String, String>> sortedAllEdges = sort(new ArrayList<DirectedLabeledEdge<String, String>>(
																	       graph.getAllEdges(currentNode).get(childNode)));
							
							// build a new path based on old path from currentNode 
							ArrayList<DirectedLabeledEdge<String, String>> newPath =  new ArrayList<DirectedLabeledEdge<String, String>>(oldPath);
			
							// Put this edge to the path
							newPath.add(sortedAllEdges.get(0));
							
							// put this node and path to paths map
							paths.put(childNode, newPath);
							
							//  add this node to q
							workList.add(childNode);
						}
					}
				}
			}
			return null;
		}
	} 
			
	// Make sure user get the right answer when the name they entered is not strictly follow the 
	// format in database
	// Take the name user entered and return the corresponding database name. 
	// Return "false" if entered name in not in the database
	private static String contains(DirectedLabeledGraph<String, String> graph, String node) {
		for (String parentNode : graph.getAllParents()) {
			if (parentNode.toLowerCase().contains((CharSequence) node.toLowerCase())) {;
				return parentNode; 
			} 
		}
		return "false";
	}
	
	// Make sure the lexicographically lowest String will be at index 0 of the return list
	// To improve efficiency as only part of sorting is needed
	private static ArrayList<DirectedLabeledEdge<String, String>> sort(ArrayList<DirectedLabeledEdge<String, String>> list) {
		ArrayList<DirectedLabeledEdge<String, String>> result = new ArrayList<DirectedLabeledEdge<String, String>>();
		result.add(list.get(0));
		for(DirectedLabeledEdge<String, String> item : list) {
			if (item.getLabel().compareTo(result.get(0).getLabel()) < 0) {
				result.add(0, item);
			} else {
				result.add(item);
			}
		}
		return result;
	}
	
	// Give introduction to this application 
	private static void giveIntro() {
		System.out.println("Want to learn something weirdly interesting about your "
				+ "favoriate Marvel superhero?");
		System.out.println("Follow the instructions and have fun!");
		System.out.println("Enter the real name of the superhero "
				+ "(Ex: Tony Stark for Iron Man) will help to get more precise result");
	}
	
	// Print out the connection between two characters entered by the user
	private static void printResult(List<DirectedLabeledEdge<String, String>> path, String hero1, String hero2) {
		List<DirectedLabeledEdge<String, String>> foundPath = path;
        System.out.println( hero1 + " and " + hero2 + " are linked by following book:");
        if (foundPath != null) {
	        for (DirectedLabeledEdge<String, String> edge : foundPath) {
	        	System.out.println(edge.getParentNode() + " and " + edge.getChildrenNode() + 
	        						" appeared together in issue " + edge.getLabel());
	        }
        } else {
        	System.out.println("Your characters are completely not related");
        }
	}
	
	// Ask user if they want to quit the application
	// Quit application if answer yes, otherwise keep running
	private static void askQuit(Scanner input) {
		System.out.println("Do you want to play again? (yes/no)");
		String answer = input.nextLine();
		if (answer.toLowerCase().startsWith("y")) {
			quit = false;
		} else {
			quit = true;
		}
	}
	
}
