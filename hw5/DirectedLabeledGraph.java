package hw5;
import java.util.*;
/**
 * <b>DirectedLabeledGraph</b> represents a <b>mutable</b> graph that
 * contains multiple Nodes and labels to represent a series of vector 
 * connection relations between nodes
 * <p>
 * In DirectedLabeledGraph, all connections from one node can be described as 
 * [parent, [child1, (e2, e3...)] 
 * 			[child2, (e4, e5...)]
 * 			[...]
 * 			[child_n,(e_i,e_j..)]] where parent is the parent node where connection 
 * pointing from. Child are nodes where the connection pointing to. (e2,e3...) are collections 
 * of Edge that represent the label of this connection.
 * 
 * One Single connection will be described as [parent, [child, label]], indicating that this connection 
 * is pointing from parent to child with label as its information. This connection is called a Edge.
 * <p>
 * 
 * Specification fields
 *  @specfield parents : N // start nodes where connections start
 *  @specfield connections : L // label of connections with same start and end node 
 *  @specfield children : N // destination nodes where the connections points to 
 *
 * Abstract Invariant:
 *  Graph can not have null nodes nor null connections
 */

public class DirectedLabeledGraph<N, L> {
	public static boolean CHECK_EXPENSIVE = false;
	// Abstraction Function:
	//  AF(r) = DirectedLabeledGraph g such that 
	//			g.parents = r.graph.keySet()
	//			for each g.parent: for (String parent : r.graph.keySet())
	//			g.children = r.graph.get(parent).keySet()
	//			for each g.children: for (String child : r.graph.get(parent).keySet())
	//			g.Edge = r.graph.get(parent).get(children)
				
	// Representation Invariant:
	//  graph != null &&  
	//	graph.keySet().contains(null) == false &&
	//	graph does not contain any null Edge &&
	//  graph does not contain any null childNode
	
	/**
	* Checks that the representation invariant holds (if any).
    */
	private void checkRep() {
		assert(this != null) : "DirectedLabeledGraph can not be null."; 
		if (CHECK_EXPENSIVE) {
			for (N node : graph.keySet()) {
				assert(node != null) : "DirectedLabeledGraph can not have a null parent parentNode.";
				
				for (N childNode : graph.get(node).keySet()) {
					assert(childNode != null) : "DirectedLabeledGraph can not have a null childNode key.";
					assert(!graph.get(node).get(childNode).contains(null)) : 
							"DirectedLabeledGraph can not have a null Edge.";
				}
			}
		}
	}
	
	private Map<N, HashMap<N, HashSet<DirectedLabeledEdge<N, L>>>> graph;
	
	/**
    * @effects Constructs a new empty DirectedLabeledGraph 
    */
	public DirectedLabeledGraph() {
		graph = new HashMap<N, HashMap<N, HashSet<DirectedLabeledEdge<N, L>>>>();
		checkRep();
	}
	
	/**
	 * Add a new node n to the graph if and only if it does not exist in graph previously 
	 * 
	 * @param node, the name of node being added to the Graph 
	 * @requires node != null && this.keySet().contains(node) == false
	 * @modifies this
	 * @effects graph.keySet().contains(node) == true
	 * @throws IllegalArgumentException when node == null or node is already in the graph
	 */
	public void addNode(N node) {
		if (node == null) {
			throw new IllegalArgumentException("Can not add a null GraphNode to Graph");
		} 
		if(graph.containsKey(node)) {
			throw new IllegalArgumentException("Can not add a GraphNode that is already in the Graph");
		} else {
			graph.put(node, new HashMap<N, HashSet<DirectedLabeledEdge<N, L>>>());
			assert(graph.containsKey(node)) : "Node is not added to the Graph correctly";
			checkRep();
		}
	}
	
	/**
	 * Get a set of all current parent parentnNode 
	 * @requires graph.keySet() != null
	 * @return a unmodifiable view of all parentNode in the Graph
	 */
	public Set<N> getAllParents() {
		assert(graph.keySet() != null) : "Graph collection of parent nodes is not build correctly";
		return Collections.unmodifiableSet(graph.keySet());
	}
	
	/**
	 * Add a new Edge n to the graph:
	 *    - if both parent node and child node exist in Graph, simply add a new label to parent node
	 *    	under specified childNode 
	 *    - if one or both of the nodes does not exist in Graph, create new Node for the it.
	 *    	and then add label to parent node with specified childNode	
	 * 
	 * @param parentNode parentNode of edge being added
	 * @param childNode childNode of edge being added
	 * @param label label of edge being added
	 * @requires parentNode != null && childNode != null && label != null
	 * @modifies this
	 * @effects graph.keySet().contains(parentNode) == true
	 * @effects graph.keySet().contains(childNode) == true
	 * @effects graph.get(parentNode).get(childNode).contains(edge being added) == true
	 * @throws IllegalArgumentException when parentNode == null && childNode == null && label != null
	 */
	public void addEdge(N parentNode, N childNode, L label) {
		if (parentNode == null) {
			throw new IllegalArgumentException("parentNode can not be null in Graph");
		} else if(childNode == null) {
			throw new IllegalArgumentException("childNode can not be null in Graph");
		} else if(label == null) {
			throw new IllegalArgumentException("label can not be null in Graph");
		} else {
			if (!graph.containsKey(parentNode)) {
				graph.put(parentNode, new HashMap<N, HashSet<DirectedLabeledEdge<N, L>>>());
			}
			if (!graph.containsKey(childNode)) {
				graph.put(childNode, new HashMap<N, HashSet<DirectedLabeledEdge<N, L>>>());
			}
			HashMap<N, HashSet<DirectedLabeledEdge<N, L>>> children = graph.get(parentNode);
			if (!children.containsKey(childNode)) {
				children.put(childNode, new HashSet<DirectedLabeledEdge<N, L>>());
			}
			HashSet<DirectedLabeledEdge<N, L>> labels = children.get(childNode);
			DirectedLabeledEdge<N, L> newEdge = new DirectedLabeledEdge<N, L>(parentNode, childNode, label);
			if (!labels.contains(newEdge)) {
				labels.add(newEdge);
				assert(graph.get(parentNode).get(childNode).contains(newEdge)) : "Fail to add edge to Graph correctly";
				checkRep();
			}
		}
	}
	
	/**
	 * Get a copy of all childrenNode specified by the String parentNode name
	 * 
	 * @param parentName, the name of parentNode being searched
	 * @requires parentNode exist in the Graph 
	 * @return a immutable Set of all childrenNode of the specified parentNode
	 * @throws IllegalArgumentException when parentNode == null or parentNode is not in the graph 
	 */
	public Set<N> getChildren(N parentNode) {
		if (parentNode == null) {
			throw new IllegalArgumentException("No null node allowed in Graph");
		} else {
			if (!graph.containsKey(parentNode)) {
				throw new IllegalArgumentException("parentNode wanted is not in Graph");
			} else {
				return Collections.unmodifiableSet(graph.keySet()); // is KeySet already a view???
			}
		}
	}
	
	/**
	 * Get a map of all Edges that pointing from start GraphNode
	 * @param start, name of specified start node
	 * @requires specified start node is in the graph 
	 * @return a unmodifiable map view of all Edges that point from parentNode
	 * 		   the key of map are childNode and value are set of labels 
	 * @throws IllegalArgumentException when start == null || start is not in the graph
	 * 										
	 */
	public Map<N, HashSet<DirectedLabeledEdge<N, L>>> getAllEdges(N parentNode) {
		if (parentNode == null) {
			throw new IllegalArgumentException("No null parentNode allowed in Graph");
		} else if (!graph.containsKey(parentNode)) {
			throw new IllegalArgumentException("parent Node is not in the Graph");
		} else {
			return Collections.unmodifiableMap(graph.get(parentNode));
		}
	}
	
	/**
	 * Check if the parentNode represent has a edge that point to childNode represent
	 * @requires parent != null && child != null && parentNode is in the Graph and childNode is in the Graph
	 * 			 
	 * @return true if and only if: parent is in the graph && children is in the graph &&
	 * 					There is edge which parentNode is parent and childNode is child
	 * 		   false otherwise.
	 * @throws IllegalArgumentException when parent == null || children == null ||
	 * 					GraphNode parent represent is not in the Graph			
	 */
	public boolean isChildren(N parentNode, N childNode) {
		if (parentNode == null || childNode == null) {
			throw new IllegalArgumentException("ParentNode or ChildNode can not be null in Graph");
		} else {
			if (!graph.containsKey(parentNode)) {
				throw new IllegalArgumentException("ParentNode must be in Graph");
			} else {
				return graph.get(parentNode).containsKey(childNode);
			}
		}
	}
	
	@Override
	public String toString() {
		return graph.toString();
	}
}