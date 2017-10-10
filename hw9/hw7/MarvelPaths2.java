package hw7;
import java.util.*;

import hw5.DirectedLabeledEdge;
import hw5.DirectedLabeledGraph;
import hw6.MarvelParser;
import hw6.MarvelParser.MalformedDataException;

public class MarvelPaths2 {
	
	//This is where the AF and RI would normally go.
	
	/**
	 * Build a weighted DirectedLabeledGraph whose label represent the degree of connection 
	 * between two edges 
	 * 
	 * @requires file name is valid in specific directory 
	 * @param fileName The tsv file that the Graph is based on
	 * @return DirectedLabeledGraph that built based on input file's contend. 
	 * @throws MalformedDataException if the file is not well-formed:
	 *         each line contains exactly two tokens separated by a tab,
	 *         or else starting with a # symbol to indicate a comment line.
	 */
	public static DirectedLabeledGraph<String, Double> buildGraph(String fileName) {
		Set<String> characters = new HashSet<>();
		Map<String, List<String>> books = new HashMap<>();
		DirectedLabeledGraph<String, String> marvelGraph = new DirectedLabeledGraph<>();
		
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
		
		DirectedLabeledGraph<String, Double> weightedGraph = new DirectedLabeledGraph<>();
		for (String parent : marvelGraph.getAllParents()) {
			weightedGraph.addEdge(parent, parent, 0.0);
			for (String child : marvelGraph.getAllEdges(parent).keySet()) {
				double weight = 1.0 / marvelGraph.getAllEdges(parent).get(child).size();
				weightedGraph.addEdge(parent, child, weight);
			}
		}
		return weightedGraph;
	}
	
	/**
	 * Find the least cost path between startNode and endNode with given graph
	 * 
	 * @param 	graph The directedLabeledGraph used to find path
	 * @param 	startNode	the starting node in the graph
	 * @param 	endNode the destination node in the graph
	 * @return	List of edges that represent the least cost path from startNode to EndNode
	 * @return 	Null if startNode or endNode is not in the Graph 
	 * 			or graph or startNode or endNode is null
	 * 			startNode or endNode is not linked in the Graph
	 */
	public static List<DirectedLabeledEdge<String, Double>> findPath(DirectedLabeledGraph<String, Double> graph, String startNode, String endNode) {
		if (graph == null || !graph.getAllParents().contains(startNode) || 
				!graph.getAllParents().contains(endNode) || startNode == null || endNode == null) {
			return null;
		} else {
			MinimumCostPath<String, Double> pathObject = new MinimumCostPath<>();
			List<DirectedLabeledEdge<String, Double>> path = pathObject.findPath(graph, startNode, endNode, 0.0);
			return path;
		}
	}

}
