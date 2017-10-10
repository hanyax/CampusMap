package hw7;

import java.util.*;

import hw5.DirectedLabeledEdge;
import hw5.DirectedLabeledGraph;
import hw7.pathComparator;

public class MinimumCostPath<N, L> {
	
	/**
	 * Find the least cost path between startNode and endNode with given graph
	 * and return the path
	 * 
	 * @requires graph != null && startNode != null && endNode != null &&
	 * 			 graph.getAllparents.contains(satrtNode) && graph.getAllparents.contains(endNode)
	 * @param 	graph The DirectedLabeledGraph used to find path
	 * @param 	startNode the Starting node in the graph
	 * @param 	endNode the Destination node in the graph
	 * @param 	selfPointLabel the Label value of a edge that point to the startNode itself
	 * @return 	List of edges that represent the least cost path from startNode to EndNode
	 * @return 	Null if startNode or endNode is not linked in the Graph
	 */
	public List<DirectedLabeledEdge<N, Double>> findPath(DirectedLabeledGraph<N, Double> graph, N startNode, N endNode, double selfPointLabel) {
		Comparator<List<DirectedLabeledEdge<N, Double>>> comparator = new pathComparator<>();
		PriorityQueue<List<DirectedLabeledEdge<N, Double>>> active = new PriorityQueue<>(comparator);
		
		Set<N> finished = new HashSet<>();
		
		List<DirectedLabeledEdge<N, Double>> startPath = new ArrayList<>();
		DirectedLabeledEdge<N, Double> startEdge = new DirectedLabeledEdge<>(startNode, startNode, selfPointLabel);
		startPath.add(startEdge);
		active.add(startPath);
		
		active.add(startPath);
		while (!active.isEmpty()) {
			List<DirectedLabeledEdge<N, Double>> minPath = active.poll() ;
			N minDest =  minPath.get(minPath.size()-1).getChildrenNode();
			
			if (minDest.equals(endNode)) {
				return minPath;
			} 
			if (finished.contains(minDest)) {
				continue;
			} else {
				Map<N, HashSet<DirectedLabeledEdge<N, Double>>> allEdges =  graph.getAllEdges(minDest);
				for (N childNode : allEdges.keySet()) {
					if (!finished.contains(childNode)) {
						List<DirectedLabeledEdge<N, Double>> edges = new ArrayList<>(allEdges.get(childNode));
						int minIndex = indexOfLowestCost(edges);
						
						List<DirectedLabeledEdge<N, Double>> newPath = new ArrayList<>(minPath);
						newPath.add(edges.get(minIndex));
						active.add(newPath);
					}
				}
			}
			finished.add(minDest);
		}
		return null;
	}
	
	
	/**
	 * Find the index of DirectedLabeledEdge with least cost label in a list of Edge
	 * and return the index
	 * 
	 * @requires edges != null && edges.isEmpty == false
	 * @param edges a list of DirectedLabeledEdges that need to found label with lowest cost 
	 * @return the index of the DirectedLabeledEdge with lowest Cost, -1 if the edges is null or empty
	 */
	private int indexOfLowestCost(List<DirectedLabeledEdge<N, Double>> edges) {
		if (edges.isEmpty() || edges == null) {
			return -1;
		} else {
			double min = (double) edges.get(0).getLabel();
			int index = 0;
			for (int i = 0; i < edges.size(); i++) {
				if ((double) edges.get(i).getLabel() < min) {
					min = (double) edges.get(i).getLabel();
					index = i;
				}
			}
			return index;
		}
	}
}
