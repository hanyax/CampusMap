package hw8;
import java.util.*;
import hw5.DirectedLabeledEdge;
import hw5.DirectedLabeledGraph;
import hw7.MinimumCostPath;
import hw8.PathParser;

/**
 * 	CampusMap represent a immutable campus map that contains information 
 * 	about all the buildings and paths in UW campus.
 * 
 * 	Specification fields
 * 	@specfield graph : Collection of campus building and path location data and their connection
 * 	@specfield buildings : Collection of data about campus buildings' name and their location
 * 	
 * 	Abstract Invariant:
 * 	All the building in the graph is connected by paths
 */

public class CampusMap {
	
	/*
	 * 	Abstraction Function:
	 * 	AF(r) = CampusMap p such that:
	 * 			p.graph = r.campusGraph
	 * 			p.buildings = r.campusBuildings;
	 * 
	 * 	Representation Invariant:	
	 * 	this != null || campusGraph != null || campusBuilding != null
	 * 	all building in campusBuiling is in campusGraph and is connected with each other
	 */
	
	/** 
	 * Checks that the representation invariant holds (if any).
	 */
	private void checkRep() {
		assert(this.campusBuildings != null);
		assert(this.campusGraph != null);
		for (PathNode building : campusBuildings) {
			assert(campusGraph.getAllParents().contains(building));
		}
	}
	
	private final DirectedLabeledGraph<PathNode, Double> campusGraph;
	private final Set<PathNode> campusBuildings;
	
	/**
	 * @effects 	Construct a CampusMap object with UW campus information loaded
	 * @param 		File directory of location and building data;
	 * @modifies 	this	
	 * @throws 		MalformedDataException e if file is invalid
	 */
	public CampusMap(String buildingFileDir, String mapFileDir) {
		campusGraph = buildGraph(mapFileDir);
		campusBuildings = buildingParser(buildingFileDir);
		checkRep();
	}
	
	/**
	 * @requires The file directory is valid
	 * @param	File directory of location and building data;
	 * @return 	DirectedLabelGraph that contains campus PathNode location information
	 * 			The paths between pathNode and the distance of paths
	 * @throws MalformedDataException e if file is invalid
	 */
	private DirectedLabeledGraph<PathNode, Double> buildGraph(String fileDir) {
		DirectedLabeledGraph<PathNode, Double> graph = new DirectedLabeledGraph<>();
		Map<PathNode, Map<PathNode, Double>> paths = new HashMap<>();
		Set<PathNode> nodes = new HashSet<>();
				
		try {
			PathParser.parseData(fileDir, paths, nodes);
		} catch (hw8.PathParser.MalformedDataException e) {
			e.printStackTrace();
		}
			
		for (PathNode node : nodes) {
			graph.addNode(node);
		}
		
		for (PathNode startPoint : paths.keySet()) {
			for (PathNode endPoint : paths.get(startPoint).keySet()) {
				graph.addEdge(startPoint, endPoint, paths.get(startPoint).get(endPoint));
			}
		}
		return graph;
	}
	
	/**
	 * @requires campusGraph == null || start == null || dest == null
	 * @param 	start The name of start location of the path searched
	 * @param 	dest The name of destination location of the path searched
	 * @return 	CampusPath that represent the closest path from start to dest if and only if:
	 * 				start and dest exist in the map, star != null and dest != null
	 * 			Otherwise, return null;
	 */
	public CampusPath findPath(String start, String dest) {
		if (campusGraph == null || start == null || dest == null) {
			return null;
		} else {
			if (exist(start) && exist(dest)) {
				PathNode startLocation = setPathNode(start);
				PathNode destLocation = setPathNode(dest);
				MinimumCostPath<PathNode, Double> pathObject = new MinimumCostPath<>();
				List<DirectedLabeledEdge<PathNode, Double>> path = pathObject.findPath(campusGraph, startLocation, destLocation, 0.0);
				CampusPath wrappedPath = new CampusPath(path);
				return wrappedPath;
			} else {
				return null;
			}
		}
	}
	
	/**
	 * @return A collection of buildings short name that is currently in the map
	 */
	public Set<String> listBuildingsShortName() {
		Set<String> buildings = new HashSet<>();
		for (PathNode building : campusBuildings) {
			buildings.add(building.getShortName());
		}
		return buildings;
	}
	
	/**
	 * @return A collection of buildings long name that is currently in the map
	 */
	public Set<String> listBuildingsLongName() {
		Set<String> buildings = new HashSet<>();
		for (PathNode building : campusBuildings) {
			buildings.add(building.getLongName());
		}
		return buildings;
	}
 
	/**
	 * @param 	longName the short name of the building user is searching for
	 * @requires longName != null
	 * @return	ShortName that represent the building user looking for if and only if:
	 * 				Building being searched exists in the graph
	 * 			Otherwise: return null
	 */
	public String getBuildingShortName(String longName) {
		if (longName != null) {
			for (PathNode node : campusBuildings) {
				if (node.getLongName().equals(longName)) {
					return node.getShortName();
				}
			}
		}
		return null;
	}
	
	/**
	 * @param 	short name the short name of the building user is searching for
	 * @requires shortName != null
	 * @return	long name represent the building user looking for if and only if:
	 * 				Building being searched exists in the graph
	 * 			Otherwise: return null
	 */
	public String getBuildingLongName(String shortName) {
		if (shortName != null) {
			for (PathNode node : campusBuildings) {
				if (node.getShortName().equals(shortName)) {
					return node.getLongName();
				}
			}
		}
		return null;

	}
		
	/**	
	 * @param location
	 * @requires location != null
	 * @return	true if passed-in location exist in the graph
	 *			false otherwise
	 */
	public boolean exist(String location) {
		if (location != null) {
			for (PathNode node : campusBuildings) {
				if (node.getShortName().equals(location) || node.getLongName().equals(location)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @requires 	the filename and the file has to be valid
	 * @param 		fileName
	 * @return 		A collection of building that is currently in the map
	 * @throws 		MalformedDataException e if filename or file format is invalid
	 */
	private Set<PathNode> buildingParser(String fileName) {
		Set<PathNode> campusBuildings = new HashSet<>();
		try {
			BuildingParser.parseData(fileName, campusBuildings);
		} catch (hw8.BuildingParser.MalformedDataException e) {
			e.printStackTrace();
		}
		return campusBuildings;
	}
	
	/**
	 * @requires	location must exist in campus_building dataset
	 * @param 		location, the location user is searching for 
	 * @return 		PathNode object of passed-in location
	 * 		   		Null if campus_building dataset does not contain passed-in location
	 */
	private PathNode setPathNode(String location) { 
		for (PathNode node : campusBuildings) {
			if (node.getShortName().equals(location) || node.getLongName().equals(location)) {
				return node;
			}
		}
		return null;
	}
}