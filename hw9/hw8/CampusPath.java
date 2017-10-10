package hw8;
import java.util.*;
import hw5.DirectedLabeledEdge;

/**
 * 	CampusPath represented an immutable path on map from location A to location B
 * 	A campusPath is a collection of Edge of PathNode which represent the path from 
 * 	A to B if A and B is directly connected by a single road.
 * 
 * 	Assume an edge from A to B is represent as [A, distance, B]:
 * 	A path from A to D is represented as:
 * 	[A, 0.0, A] [A, distance, B] [B, distance, C] [C, distance, D]
 * 
 * 	Specification field:
 * 	@specfield path : List of the passed-by location's coordinate and distance between them 
 * 	@specfield totalDistance: Double  // Distance of a path
 * 	@specfield totalTurns : Double // Number of turns this path has 
 * 
 * 	Abstract Invariant:
 * 	A path must have at least 0 turn and at least 0 distance
 */

public class CampusPath {
	
	/*	Abstraction Function:
	 * 	AF(r) = CampusPath p such that:
	 * 			p.path = r.path
	 * 			p.totalDistance = r.totalCost
	 * 			p.totalTurns = r.size
	 * 
	 *	Representation Invariant:	
	 *	CampusPath != null || path != null || totalCost != null || 
	 *	size >= 0 || totalCost >=0
	 */
	
	/** 
	 * Checks that the representation invariant holds (if any).
	 */
	private void checkRep() {
		assert(this != null);
		assert(this.path != null);
		assert(this.totalCost != null);
		assert(this.size >= 0);
		assert(this.totalCost >= 0);
	}
	
	
	private final List<DirectedLabeledEdge<PathNode, Double>> path;
	private final Double totalCost;
	private final int size;
	
	/**
	 * @requires passed in list of pass is not null
	 * @param path the collection of edges that represent the location a Campus path passed by and distance between locations
	 * @effects	Construct a new CampusPath with passed-in path
	 * @modifies this
	 * @throws IllegalArgumentException if passed in list is null
	 */
	public CampusPath(List<DirectedLabeledEdge<PathNode, Double>> path) {
		if (path == null) {
			throw new IllegalArgumentException("Campus path can not build on a null path");
		}
		this.path = path;
		Double cost = 0.0;
		for (DirectedLabeledEdge<PathNode, Double> edge : path) {
			cost += edge.getLabel();
		}
		totalCost = cost;
		size = path.size();
		checkRep();
	}
	
	/**
	 * @return the total turns a campus path has 
	 */
	public int getTotalTurns() {
		if (size == 1) {
			return 0;
		} else {
			return this.size - 2;
		}
	}
		
	/**
	 * @requires	n >= 0 && n < this.size
	 * @param 	turn, the number of turns at the target location
	 * @return 	the distance from location after n turns to location after n+1 turns, Double
	 * 		   	if path's start location and destination is the same, return 0.0 regardless the parameter
	 * @throws 	IllegalArgumentException when n < 0 || n >= this.size
	 */
	public Double getDistance(int n) {
		if (n < 0 || n >= this.size) {
			throw new IllegalArgumentException("The number of turns has to be in the range of total path turns"); 
		}
		if (this.size == 1) {
			return path.get(0).getLabel();
		} else {
			return path.get(n+1).getLabel();
		}
	}
	
	/**
	 * @requires n >= 0 && n < this.size
	 * @param 	turn, the number of turns at the target location
	 * @return 	the x coordinate of current start position after n turns, Double
	 * 			if path's start location and destination is the same, return start location regardless the parameter
	 * @throws 	IllegalArgumentException when n < 0 || n >= this.size
	 */
	public Double getStartLocationX(int n) {
		if (n < 0 || n >= this.size) {
			throw new IllegalArgumentException("The number of turns has to be in the range of total path turns"); 
		}
		if (this.size == 1) {
			return path.get(0).getParentNode().getX();
		} else {
			return path.get(n+1).getParentNode().getX();
		}
	}
	
	/**
	 * @requires n >= 0 && n < this.size
	 * @param 	turn, int the number of turns already made
	 * @return 	the y coordinate of current start position after n turns, Double
	 * 			if path's start location and destination is the same, return start location regardless the parameter
	 * @throws 	IllegalArgumentException when n < 0 || n >= this.size
	 */
	public Double getStartLocationY(int n) {
		if (n < 0 || n >= this.size) {
			throw new IllegalArgumentException("The number of turns has to be in the range of total path turns"); 
		}
		if (this.size == 1) {
			return path.get(0).getParentNode().getY();
		} else {
			return path.get(n+1).getParentNode().getY();
		}
	}
	
	/**
	 * @requires n >= 0 && n < this.size
	 * @param 	turn, int the number of turns already made
	 * @return 	the x coordinate of current destination position after n turns, Double
	 * 			if path's start location and destination is the same, return dest location regardless the parameter
	 * @throws 	IllegalArgumentException when n < 0 || n >= this.size
	 */
	public Double getDestLocationX(int n) {
		if (n < 0 || n >= this.size) {
			throw new IllegalArgumentException("The number of turns has to be in the range of total path turns"); 
		}
		if (this.size == 1) {
			return path.get(0).getChildrenNode().getX();
		} else {
			return path.get(n+1).getChildrenNode().getX();
		}
	}
	
	/**
	 * @requires n >= 0 && n < this.size
	 * @param 	turn, int the number of turns already made
	 * @return 	the y coordinate of current destination position after n turns, Double
	 * 			if path's start location and destination is the same, return dest location regardless the parameter
	 * @throws 	IllegalArgumentException when n < 0 || n >= this.size
	 */
	public Double getDestLocationY(int n) {
		if (n < 0 || n >= this.size) {
			throw new IllegalArgumentException("The number of turns has to be in the range of total path turns"); 
		}
		if (this.size == 1) {
			return path.get(0).getChildrenNode().getY();
		} else {
			return path.get(n+1).getChildrenNode().getY();
		}
	}
}
